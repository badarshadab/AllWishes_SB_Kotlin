package com.greetings.allwishes.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greetings.allwishes.R
import com.greetings.allwishes.databinding.FragmentFrameEditBinding
import com.greetings.allwishes.databinding.SelectImageDialogBinding
import com.greetings.allwishes.ui.util.AddImage
import com.greetings.allwishes.ui.util.AddText
import com.greetings.allwishes.ui.util.AppUtils
import com.greetings.allwishes.ui.util.BubbleTextView
import com.greetings.allwishes.ui.util.MultiTouchListener
import com.greetings.allwishes.ui.util.PicColor
import com.greetings.allwishes.ui.util.RecyclerViewClickListener
import com.greetings.allwishes.ui.viewmodel.SelectedImageViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.databinding.AddtextDialogLayBinding
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.greetings.allwishes.ui.adapter.StickersAdapter
import com.greetings.allwishes.ui.data.api.FirebaseHelper
import com.greetings.allwishes.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class FrameEditFragment : Fragment() {
    private lateinit var b: FragmentFrameEditBinding
    private lateinit var sticker: ImageView
    lateinit var imageUri: Uri
    private var addtextbinding: AddtextDialogLayBinding? = null
    private var addtextdialog: Dialog? = null
    lateinit var fontdata: Typeface
    var texteditcolor: Int = 0
    private lateinit var mainViewModel: HomeViewModel

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        b.galleryImageView.setImageURI(null)
        b.galleryImageView.setImageURI(imageUri)
    }

    private val loadImage =
        registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
            b.galleryImageView.setImageURI(it)
        })

    companion object {
        lateinit var any: Any
        val _sharedFlow = MutableSharedFlow<Int>()
        val sharedFlow = _sharedFlow.asSharedFlow()
    }

    private fun initializeView(b: FragmentFrameEditBinding) {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        b.rv.setLayoutManager(layoutManager)

        sticker = ImageView(requireContext())
        val lp = FrameLayout.LayoutParams(150, 150)
        sticker.layoutParams = lp
//        mTv_text.setLayoutParams(lp);
        //        mTv_text.setLayoutParams(lp);
        sticker.setOnTouchListener(MultiTouchListener())

        b.container.addView(sticker)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        b = FragmentFrameEditBinding.inflate(inflater, container, false)
        initializeView(b)
        imageUri = createImageUri()!!
        b.galleryImageView.setOnTouchListener(MultiTouchListener())
        setupViewModel()
        if (any is StorageReference) {
            AppUtils.setImage(requireContext(), any as StorageReference, b.cakeImageView)
        } else if (any is String) {
//            Util.setImage(requireContext(), any as String, b.cakeImageView)
        }

        b.chooseImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                selectImage()
                openAddPhotoDialog(requireContext(), true)
            }
        })
        b.btnShare.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                shareImage()
            }
        })
        b.addText.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                addTextFrame()
                addTextDilog()
            }
        })
        b.addStickers.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (b.addStickers.getText().toString()
                        .equals(getString(R.string.remove_stickers_btn_ttl), ignoreCase = true)
                ) {
                    b.addStickers.setText(getString(R.string.add_stickers_btn_ttl))
                    sticker.setImageDrawable(null)
                } else {
                    showStickersDialog(requireContext(), true)
                }
            }
        })
        b.download.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                saveImage()
                download()
            }
        })

        return b.root
    }

    private fun createImageUri(): Uri? {
        val img = File(requireContext().filesDir, "camera_photo.png")
        return AppUtils.getProviderUri(requireContext(), img)
    }

    fun download() {
        AppUtils.getBitMap(b.card)?.let { AppUtils.saveBitmap(it, "", requireContext()) }
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
    }

    fun shareImage() {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap: Bitmap? = AppUtils.getBitMap(b.card)
            val uri: Uri? =
                bitmap?.let { AppUtils.getLocalBitmapUri(requireContext(), it) }
            context?.let { AppUtils.shareImage(it, uri) }
        }
    }

    fun addTextFrame() {
//        val colorPicker = PicColor()
//        var addText = AddText(colorPicker)
//        addText(requireContext()) {
//            val string = it.first
////            val bubbleTextView = BubbleTextView(
////                context,
////                it.second,
////                it.third,
////                0
////            )
//            val bubbleTextView = BubbleTextView(
//                context
//            )
//            bubbleTextView.setOperationListener(object : BubbleTextView.OperationListener {
//                override fun onDeleteClick() {
//                    b.card.removeView(bubbleTextView)
//                }
//
//                override fun onEdit(bubbleTextView: BubbleTextView?) {
//                    bubbleTextView.let {
//                        val onEdit = !bubbleTextView?.isInEditMode!!
//                        bubbleTextView.setInEdit(onEdit)
//                    }
//                }
//
//                override fun onClick(bubbleTextView: BubbleTextView?) {
//                }
//
//                override fun onTop(bubbleTextView: BubbleTextView?) {
//                }
//            })
//
//            if (string.length <= 200) {
//                bubbleTextView.setImageResource(R.mipmap.bubble_7_rb_250)
//            } else if (string.length > 200 && string.length < 400) {
//                bubbleTextView.setImageResource(R.mipmap.bubble_7_rb_100)
//            } else if (string.length >= 400 && string.length < 800) {
//                bubbleTextView.setImageResource(R.mipmap.bubble_7_rb_500_200)
//            } else {
//                bubbleTextView.setImageResource(R.mipmap.bubble_7_rb)
//            }
//            bubbleTextView.setText(string)
//            GlobalScope.launch {
//                delay(500)
//                withContext(Dispatchers.Main) {
//                    b.card.addView(bubbleTextView)
//                }
//            }
//        }



    }

    fun addTextDilog() {
        addtextbinding = AddtextDialogLayBinding.inflate(layoutInflater)
        addtextdialog = Dialog(requireContext(), R.style.WideDialog)
        addtextdialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogTheme2

        addtextdialog?.setContentView(addtextbinding!!.root)
        addtextdialog?.setCancelable(true)
        addtextdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        fontdata = Typeface.createFromAsset(requireContext().assets, "1.ttf")
        texteditcolor = Color.parseColor("#FF000000")

        addtextbinding?.cancelBtn?.setOnClickListener {
            addtextdialog?.dismiss()
        }
        addtextbinding?.addokbtn?.setOnClickListener {

            var msgToAdd = addtextbinding!!.textid.text.toString()
            val triple = Triple(msgToAdd, texteditcolor, fontdata)
            AppUtils.addStringToView(b.card ,requireContext(), triple)
            addtextdialog?.dismiss()
        }


        addtextdialog?.show()
    }

    private fun addText(triple: Triple<String, Int, Typeface>) {
        val textView = TextView(requireContext())
        textView.setText(triple.first)
        b.card.addView(textView)
    }

    private fun addTextDialog(
        context: Context,
        isCancelable: Boolean,
        callback: (result: Triple<String, Int, Typeface>) -> Unit
    ) {
        val dialog = Dialog(context, R.style.Theme_Dialog)
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(isCancelable)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.add_text)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val etInput: TextInputEditText = dialog.findViewById(R.id.etInput)
        dialog.findViewById<View>(R.id.ivFont).setOnClickListener { v: View? ->

        }
        dialog.findViewById<View>(R.id.ivColor).setOnClickListener { v: View? ->

        }
        dialog.findViewById<View>(R.id.btnAdd).setOnClickListener { v: View? ->
            dialog.dismiss()
            val text = etInput.text.toString()
            val color = etInput.currentTextColor
            val fontface = etInput.typeface

            val triple = Triple(text, color, fontface)
            callback(triple)
        }
        dialog.findViewById<View>(R.id.btnCancel)
            .setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

    private fun openAddPhotoDialog(context: Context, isCancelable: Boolean) {
        AppUtils.checkCameraPermission(requireContext()) { it ->
            if (it) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                val addPhotoDialogBinding = SelectImageDialogBinding.inflate(layoutInflater)
                builder.setView(addPhotoDialogBinding.root)
                val dialog: AlertDialog = builder.create()
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
//        addPhotoDialogBinding.tvClose.setOnClickListener {
//            dialog.dismiss()
//        }
                addPhotoDialogBinding.fromCamera.setOnClickListener {
                    contract.launch(imageUri)
                    dialog.dismiss()
                }
                addPhotoDialogBinding.fromGallery.setOnClickListener {
                    loadImage.launch("image/*")
                    dialog.dismiss()
                }
            }
        }
    }

    fun selectImage() {
        AppUtils.checkCameraPermission(requireContext()) { it ->
            if (it) {
                val addImage = AddImage()
                val registry = requireActivity().activityResultRegistry
                addImage(requireContext(), registry = registry) {
                    try {
                        it.let {
                            println("value of it" + it)
//                            val selImgBitmap = MediaStore.Images.Media.getBitmap(
//                                activity?.getContentResolver(),
//                                it
//                            )
                            var selImgBitmap: Bitmap? = null
                            if (Build.VERSION.SDK_INT < 28) {
                                selImgBitmap = MediaStore.Images.Media.getBitmap(
                                    requireContext().contentResolver,
                                    it
                                )
                            } else {
                                val source = it?.let { it1 ->
                                    ImageDecoder.createSource(
                                        requireContext().contentResolver,
                                        it1
                                    )
                                }
                                selImgBitmap = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
                            }

                            addImageBetweenFrame(requireContext(), selImgBitmap)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun addImageBetweenFrame(context: Context, bitmap: Bitmap?) {
        bitmap.let {
            b.galleryImageView.setImageBitmap(bitmap)
            b.galleryImageView.setOnTouchListener(MultiTouchListener())
        }
    }

    fun selectImageChooseDialog() {

        // Create an alert builder
        val builder = AlertDialog.Builder(requireContext())
        // set the custom layout
        val customLayout: View = layoutInflater
            .inflate(R.layout.select_image_dialog, null)
        builder.setView(customLayout)
        val dialog = builder.create()
        val fromGallery = customLayout.findViewById<ImageView>(R.id.fromGallery)
        val fromCamera = customLayout.findViewById<ImageView>(R.id.fromCamera)
        fromGallery.setOnClickListener {
            //getPicFromGallery()
            dialog.dismiss()
        }
        fromCamera.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(this, myViewModelFactory)[HomeViewModel::class.java]
    }

    private fun showStickersDialog(context: Context, isCancelable: Boolean) {
        val dialog = Dialog(context, R.style.Theme_Dialog)
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(isCancelable)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.show_stickers)
        val stickersRv = dialog.findViewById<RecyclerView>(R.id.rv)
        val gridLayoutManager = GridLayoutManager(requireContext(), 5)
        stickersRv.layoutManager = gridLayoutManager
        mainViewModel.loadImagesStorage("Stickers")
        mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity()) {storageReferenceList ->
            val listener =
                RecyclerViewClickListener { view, position ->
//                    UtilFunctions.setImage(
//                        sticker,
//                        storageReferenceList!![position]
//                    )
                    AppUtils.setImage(
                        sticker,
                        storageReferenceList!![position]
                    )
                    dialog.dismiss()
                }
            stickersRv.adapter = StickersAdapter(
                storageReferenceList,
                listener
            )
        }
//        val viewModel = ViewModelProvider(this)[SelectedImageViewModel::class.java]
//        viewModel.getAllImage()!!.observe(this) { storageReferenceList ->
//            val listener =
//                RecyclerViewClickListener { view, position ->
////                    UtilFunctions.setImage(
////                        sticker,
////                        storageReferenceList!![position]
////                    )
//                    AppUtils.setImage(
//                        sticker,
//                        storageReferenceList!![position]
//                    )
//                    dialog.dismiss()
//                }
//            stickersRv.adapter = com.greetings.allwishes.ui.adapter.StickersAdapter(
//                storageReferenceList,
//                listener
//            )
//        }
        dialog.show()
    }

}