package com.greetings.allwishes.ui.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.greetings.allwishes.R
import java.io.File

class AddImage {
    operator fun invoke(
        context: Context,
        registry: ActivityResultRegistry,
        callback: (result: Uri?) -> Unit
    ) {
        selectImageChooseDialog(context, registry, callback)
    }

    private fun selectImageChooseDialog(
        context: Context,
        registry: ActivityResultRegistry,
        callback: (result: Uri?) -> Unit
    ) {
        val dialog = Dialog(context, R.style.Theme_Dialog)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.select_image_dialog)
        val fromGallery: ImageView = dialog.findViewById<ImageView>(R.id.fromGallery)
        val fromCamera: ImageView = dialog.findViewById<ImageView>(R.id.fromCamera)
        fromGallery.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val contractGallery = registry.register("", ActivityResultContracts.GetContent()) {
                    callback(it)
                }
                contractGallery.launch("image/*")
                dialog.dismiss()
            }
        })
        fromCamera.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                try {
                    val cameraImgUri = createImageURI(context)
                    val contractCamera =
                        registry.register("", ActivityResultContracts.TakePicture()) {
                            callback(cameraImgUri)
                        }
                    contractCamera.launch(cameraImgUri)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                dialog.dismiss()
            }
        })
        dialog.show()
    }

    private fun createImageURI(context: Context): Uri {
        val imageName = "photo_" + System.currentTimeMillis() + ".jpg"
        val image = File(context.filesDir, imageName)
        return FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            image
        )
    }
}