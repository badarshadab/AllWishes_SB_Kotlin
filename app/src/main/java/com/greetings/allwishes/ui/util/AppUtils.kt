package com.greetings.allwishes.ui.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.core.view.drawToBitmap
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.greetings.allwishes.BuildConfig
import com.greetings.allwishes.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.util.AdUtils
import com.hindishyari.shyari.viewModels.CardViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object AppUtils {

    private const val FADE_DURATION = 700
    private val GALLERY: Int = 1
    private val CAMERA: Int = 2
    val RECORD_REQUEST_CODE = 101

    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    public fun openUrl(context: Context, url: String?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    public fun rateUs(context: Context) {
        try {
            val i =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.packageName))
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: java.lang.Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun shareApp(context: Context) {
        try {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            var sAux = """
            Download
            ${context.resources.getString(R.string.app_name)}
            
            """.trimIndent()
            sAux = """
            ${sAux}https://play.google.com/store/apps/details?id=${context.packageName}
            
            """.trimIndent()
            i.putExtra(Intent.EXTRA_TEXT, sAux)
            context.startActivity(Intent.createChooser(i, "choose one"))
        } catch (e: java.lang.Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun selectSavedTypeDialog(context: Activity) {
        val builder = AlertDialog.Builder(context)
        val customLayout: View = context.getLayoutInflater()
            .inflate(R.layout.select_saved_dialog, null)
        builder.setView(customLayout)
        val dialog = builder.create()
        val fromDaily = customLayout.findViewById<ImageView>(R.id.daily)
        val fromHoliday = customLayout.findViewById<ImageView>(R.id.holiday)

        fromDaily.setOnClickListener {

            val b = Bundle()
            b.putString("from", "daily")
            changeFragment(context, R.id.nav_saved_main, b)
            dialog.dismiss()
        }
        fromHoliday.setOnClickListener {

            val b = Bundle()
            b.putString("from", "holiday")
            changeFragment(context, R.id.nav_saved_main, b)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun setUpToolbar(
        activity: AppCompatActivity,
        toolbar: Toolbar,
        title: String?,
        isHomeUp: Boolean
    ) {
        activity.setSupportActionBar(toolbar)
//        val titleTv = toolbar.findViewById<TextView>(R.id.tooText)
//        titleTv.text = title
//        titleTv.setTextColor(activity.getColor(R.color.toolbar_text_colo))
        activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(isHomeUp)
        activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_new);
        toolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

    public fun setImageWithRoundCorner(str: String, iv: ImageView, corner: Int, size: Int) {
        Glide.with(iv)
            .load(str)
            .transform(RoundedCorners(corner))
            .thumbnail(Glide.with(iv).load(R.drawable.loading_img))
            .override(size)
            .into(iv)
    }

    fun setImageWithRoundCorner(
        storageReference: StorageReference?,
        iv: ImageView?,
        corner: Int,
        size: Int
    ) {
        Glide.with(iv!!)
            .load(storageReference)
            .transform(RoundedCorners(corner))
            .thumbnail(Glide.with(iv).load(R.drawable.loading_img))
            .override(size)
            .into(iv)
    }

    fun captureScreen(view: View): Bitmap? {

        if (view == null) {
            return null
        }
        view.drawToBitmap()
        var bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        var drawable: Drawable = view.getBackground()
        if (drawable != null) drawable.draw(canvas) else canvas.drawColor(android.graphics.Color.WHITE)
        view.draw(canvas)

        return bitmap
    }

    fun shareBitmap(context: Context, bitmap: Bitmap) {
        try {
            val file = File(context.externalCacheDir, "share.png")
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            shareImage(context, getProviderUri(context, file))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            showToast(context, e.message)
        }
    }

    fun shareImage(context: Context, uri: Uri?) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            var sAux = """
            Download ${context.getString(R.string.app_name)}
            
            """.trimIndent()
            sAux = """
            ${sAux}https://play.google.com/store/apps/details?id=${context.packageName}
            
            """.trimIndent()
            intent.putExtra(Intent.EXTRA_TEXT, sAux)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.type = "image/png"
            context.startActivity(Intent.createChooser(intent, "Share image via"))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getProviderUri(ctx: Context?, file: File): Uri? {
        return try {
            FileProvider.getUriForFile(
                ctx!!,
                BuildConfig.APPLICATION_ID + ".provider",
                file.absoluteFile
            )
        } catch (e: java.lang.Exception) {
            null
        }
    }

    fun showToast(context: Context?, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun getLocalBitmapUri(context: Context, bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(
                context.getExternalFilesDir(null),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = getProviderUri(context, file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    fun getFile(ctx: Context, bmp: Bitmap?): File? {
        return try {
            val file = File(ctx.externalCacheDir, "share.png")
            val fOut = FileOutputStream(file)
            bmp!!.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            file
        } catch (e: IOException) {
            null
        }
    }

    fun getBitMap(view: View?): Bitmap? {
        if (view == null) return null
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val drawable = view.background
        if (drawable != null) drawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return bitmap
    }

    fun shareString(context: Context, s: String?) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, s)
        sendIntent.type = "text/plain"
        context.startActivity(sendIntent)
    }

    fun copyTextToClipBoard(context: Context, text: String?) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(context.getString(R.string.app_name), text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Text copy to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun setImage(ctx: Context, storageReference: StorageReference, iv: ImageView) {
        Glide.with(ctx)
            .load(storageReference)
            .thumbnail(Glide.with(ctx).load(R.drawable.loading_img))
            .into(iv)
    }

    fun saveBitmap(ob: Bitmap, extension: String, context: Context) {
        val file = getFile(context, ob)
        file?.let {
            val direct = File(
                context.getExternalFilesDir(null).toString() + "/Collection/" + extension
            )
            if (!direct.exists()) {
                direct.mkdirs()
            }
            val f = File(direct.absolutePath, "" + System.currentTimeMillis() + ".png")
            file.copyTo(f)
        }
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
    }


    fun checkCameraPermission(context: Context, callback: (result: Boolean) -> Unit) {
        Dexter.withContext(context)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                    callback(true)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    callback(false)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                    callback(false)
                }
            }).check()
    }

    public fun setImage(imageView: ImageView, storageReference: StorageReference?) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.loading_img)
        requestOptions.error(R.drawable.error_img)
        Glide.with(imageView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(storageReference).into(imageView)
    }

    fun setImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .thumbnail(Glide.with(imageView).load(R.drawable.loading_img))
            .error(R.drawable.error_img)
            .into(imageView)
    }

    fun changeFragmentWithPosition(
        nav: NavController,
        fragmentId: Int,
        activity: Activity,
        bundle: Bundle
    ) {
//        SmAds.showFullAd(activity, object : FullAdListener {
//            override fun onComplete(isAdDisplay: Boolean, adNetwork: String) {
        nav.navigate(fragmentId, bundle)
//            }
//        })
    }

    fun changeFragment(activity: Activity, resId: Int, b: Bundle) {
        AdUtils.showFullAd(activity, object : AdUtils.AdListener {
            override fun onComplete() {
        var dTime = 10L
//                if (adNetwork.equals("Mopub", ignoreCase = true)) {
//                    dTime = 500L
//                }
        Handler(Looper.getMainLooper()).postDelayed(
            {
                try {
                    Navigation.findNavController(
                        activity,
                        R.id.nav_host_fragment
                    ).navigate(
                        resId,
                        b
                    )
                } catch (e: Exception) {
                    print("catch Exception   " + e)
//                            AppUtils.getInstance().showToast(activity, "Something went wrong")
                }
            }, dTime
        )
            }
        })
    }

    fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.duration = AppUtils.FADE_DURATION.toLong()
        view.startAnimation(anim)
    }


    fun fullExitScreen(activity: Activity) {
        val dialog = Dialog(activity, R.style.DialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.exit_dialog)
        AdUtils.showNative(activity, dialog.findViewById<View>(R.id.nativeAdContainer) as CardView)
//        showNativeAd(dialog.findViewById<View>(R.id.smNativeAdContainer) as ViewGroup)
        dialog.findViewById<View>(R.id.canBtn)
            .setOnClickListener { view: View? -> dialog.dismiss() }
        dialog.findViewById<View>(R.id.okBtn).setOnClickListener { activity.finish() }
        dialog.findViewById<View>(R.id.rate_us).setOnClickListener {
            rateUs(activity)
        }
        dialog.findViewById<View>(R.id.more).setOnClickListener {
            openUrl(
                activity,
                "https://play.google.com/store/apps/developer?id=Greetings+%26+Wishes"
            )
        }
        dialog.show()
    }

    fun getpicGallery(activity: Activity) {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY)
    }

    fun camshow(activity: Activity) {
        try {

            if (ContextCompat.checkSelfPermission(
                    activity, Manifest.permission.ACCESS_FINE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.CAMERA
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.CAMERA),
                        1
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.CAMERA),
                        1
                    )

                }
            }

        } catch (e: java.lang.Exception) {
            print("errror is " + e)
        }
    }

    fun captercamera(activity: Activity) {
        val camera_intent =
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Start the activity with camera_intent, and request pic id
        activity.startActivityForResult(camera_intent, CAMERA)
    }


    fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    fun setupToolbar(view: Toolbar, activity: Activity, drawerLayout: DrawerLayout) {
        view.setNavigationOnClickListener {
            val navController = activity.findNavController(R.id.nav_host_fragment)
            val destination = navController.currentDestination
            if (destination?.id == R.id.nav_main) {
                drawerLayout.openDrawer(GravityCompat.START)
            } else {
                navController.popBackStack()
            }
        }
    }

    fun createImageUri(context: Context): Uri? {
        val img = File(context.filesDir, "camera_photo.png")
        return getProviderUri(context, img)
    }

    fun checkPermissionFor33(context: Context, activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= 33) {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 33) {
            activity.requestPermissions(
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.CAMERA
                ), RECORD_REQUEST_CODE
            )
        } else {
            activity.requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ), RECORD_REQUEST_CODE
            )
        }
    }

    fun createTabIcons(context: Context, tabLayout: TabLayout) {
        var tabOne = LayoutInflater.from(context).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = "GIFs"
        tabOne.textSize = 12F

        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_gif_unselected, 0, 0)
        tabLayout!!.getTabAt(0)!!.customView = tabOne


        val tabTwo = LayoutInflater.from(context).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = "Cards"
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cards_unselected, 0, 0)
        tabTwo.textSize = 12F
        tabLayout!!.getTabAt(1)!!.customView = tabTwo


        val tabThree = LayoutInflater.from(context).inflate(R.layout.custom_tab, null) as TextView
        tabThree.text = "Quotes"
        tabThree.textSize = 12F

        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_quotes_unselected, 0, 0)
        tabLayout!!.getTabAt(2)!!.customView = tabThree

        val tabfour = LayoutInflater.from(context).inflate(R.layout.custom_tab, null) as TextView
        tabfour.text = "Frames"
        tabfour.textSize = 12F

        tabfour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_frame_unselected, 0, 0)
        tabLayout!!.getTabAt(3)!!.customView = tabfour

//        val tabfive = LayoutInflater.from(context).inflate(R.layout.custom_tab, null) as TextView
//        tabfive.text = "Create"
//         tabfive.textSize = 12F
//
//        tabfive.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.create_btn,0,0)
//        tabLayout!!.getTabAt(4)!!.customView = tabfive


    }



    fun addStringToView(mainView: MaterialCardView ,context: Context, triple: Triple<String, Int, Typeface>) {
        val string = triple.first
        val tv_sticker = BubbleTextView(context, triple.second, triple.third, 0, string , mainView)
        removeAddedView(mainView ,tv_sticker)
        tv_sticker.setOperationListener(object : BubbleTextView.OperationListener {
            override fun onDeleteClick() {
                removeAddedView(mainView ,tv_sticker)
            }

            override fun onEdit(bubbleTextView: BubbleTextView?) {
                bubbleTextView.let {
                    val onEdit = !bubbleTextView?.isInEditMode!!
                    tv_sticker.setInEdit(onEdit)
                }
            }

            override fun onClick(bubbleTextView: BubbleTextView?) {
            }

            override fun onTop(bubbleTextView: BubbleTextView?) {
            }
        })

        if (string.length <= 200) {
            tv_sticker.setImageResource(R.mipmap.bubble_7_rb_250)
        } else if (string.length > 200 && string.length < 400) {
            tv_sticker.setImageResource(R.mipmap.bubble_7_rb_100)
        } else if (string.length >= 400 && string.length < 800) {
            tv_sticker.setImageResource(R.mipmap.bubble_7_rb_500_200)
        } else {
            tv_sticker.setImageResource(R.mipmap.bubble_7_rb)
        }

        tv_sticker.setText(string)
        addMovableItemOnView(mainView ,tv_sticker)

    }

    private fun addMovableItemOnView(mainView: MaterialCardView ,any: View) {
        mainView.addView(any)
    }

    private fun removeAddedView(mainView: MaterialCardView ,view: View) {
        mainView.removeView(view)
    }

//    fun setUpCardData(activity: ViewModelStoreOwner , categoryName : String , viewLifecycleOwner : LifecycleOwner){
//        val cardViewModel : CardViewModel by lazy { ViewModelProvider(activity)[CardViewModel::class.java] }
//        cardViewModel.getCategoryWiseCards(categoryName).observe(viewLifecycleOwner, Observer {
//            setImageAdapter(it)
//            println("bgList$it")
//        })
//    }

}