package com.examp.allwishes.ui.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.examp.allwishes.R
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.util.AdUtils
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
//        fromDaily.setOnClickListener {
//            val inten1 = Intent(context, SavedActivity::class.java)
////            AdUtils.INSTANCE.showFullAd(this@MainActivity, object : AdListener() {
////                fun onComplete() {
//            context.startActivity(inten1)
////                }
////            })
//            dialog.dismiss()
//        }
//        fromHoliday.setOnClickListener {
//            val inten1 = Intent(context, SavedHolidayGreetings::class.java)
////            AdsHandler.INSTANCE.showInterstitialAds(this@MainActivity) { isAdDisplay, which ->
//            context.startActivity(
//                inten1
//            )
////            }
//            dialog.dismiss()
//        }
        dialog.show()
    }

    fun setUpToolbar(
        activity: AppCompatActivity,
        toolbar: Toolbar,
        title: String?,
        isHomeUp: Boolean
    ) {
        activity.setSupportActionBar(toolbar)
        val titleTv = toolbar.findViewById<TextView>(R.id.tooText)
        titleTv.text = title
        titleTv.setTextColor(Color.WHITE)
        activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(isHomeUp)
        //        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
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

    fun captureScreen(view : View): Bitmap?{

    if (view == null) {
        return null
    }
    var bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
    var canvas = Canvas(bitmap)
    var drawable: Drawable = view.getBackground()
    if (drawable != null)drawable.draw(canvas) else canvas.drawColor(android.graphics.Color.WHITE)
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
                com.examp.allwishes.BuildConfig.APPLICATION_ID + ".provider",
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
                context.getExternalFilesDir(null).toString() + "/Collection/Frame"
            )
            if (!direct.exists()) {
                direct.mkdirs()
            }
            val f = File(direct.absolutePath, "" + System.currentTimeMillis() + ".png")
            file.copyTo(f)
        }

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
            .into(imageView)
    }


    fun changeFragment(activity: Activity, resId: Int, b: Bundle) {
//        AdUtils.showFullAd(activity, object : AdUtils.AdListener {
//            override fun onComplete() {
                var dTime = 10L
//                if (adNetwork.equals("Mopub", ignoreCase = true)) {
//                    dTime = 500L
//                }
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        try {
                            Navigation.findNavController(
                                activity,
                                R.id.nav_host_fragment_content_main
                            ).navigate(
                                resId,
                                b
                            )
                        } catch (e: Exception) {
//                            AppUtils.getInstance().showToast(activity, "Something went wrong")
                        }
                    }, dTime
                )
//            }
//        })
    }


}