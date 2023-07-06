package com.sm.allwishes.greetings.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.greetings.allwishes.BuildConfig
import com.greetings.allwishes.R
import com.greetings.allwishes.ui.util.AppUtils
import java.io.File

object ShareUtils {

    private fun shareItem(ctx: Context, ob: Any, nameWithExtension: String, shareOnPkg: String?) {
        getFile(ctx, ob, object : DownloadFileListener {
            override fun onDownloadComplete(file: File?) {
                file?.let { _file ->
                    val tempFile = File(ctx.externalCacheDir, nameWithExtension)
                    val f: File = _file.copyTo(tempFile, true, DEFAULT_BUFFER_SIZE)
                    val uri = getProviderUri(ctx, f)
                    shareImage(ctx, uri, shareOnPkg)
                }
            }
        })
    }

    fun shareGIF(ctx: Context, ob: Any) {
        shareItem(ctx, ob, "share.gif", null)
    }

    fun shareImage(ctx: Context, ob: Any) {
        shareItem(ctx, ob, "share.png", null)
    }

    fun shareGIFOnWhatsApp(ctx: Context, ob: Any) {
        shareItem(ctx, ob, "share.gif", "com.whatsapp")
    }

    fun shareImageOnWhatsApp(ctx: Context, ob: Any) {
        shareItem(ctx, ob, "share.png", "com.whatsapp")
    }

    fun shareQuotes(context: Context, v: View) {
        val bm: Bitmap? = AppUtils.captureScreen(v)
        bm?.let {
            val uri: Uri? = AppUtils.getLocalBitmapUri(context, bm)
            AppUtils.shareBitmap(context, bm)
        }
    }

    fun shareImage(context: Context, uri: Uri, shareOnPkg: String?) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            var sAux = "Download ${context.getString(R.string.app_name)}"
            sAux += " https://play.google.com/store/apps/details?id=${context.packageName}"
            intent.setPackage(shareOnPkg)
            intent.putExtra(Intent.EXTRA_TEXT, sAux)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.type = "image/png"
            context.startActivity(Intent.createChooser(intent, "Share image via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFile(ctx: Context, ob: Any, listener: DownloadFileListener) {
        Glide.with(ctx)
            .downloadOnly()
            .load(ob)
            .listener(object : RequestListener<File?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<File?>,
                    isFirstResource: Boolean
                ): Boolean {
                    listener.onDownloadComplete(null)
                    return false
                }

                override fun onResourceReady(
                    resource: File?,
                    model: Any,
                    target: Target<File?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    listener.onDownloadComplete(resource)
                    return false
                }
            })
            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
    }

    fun getProviderUri(ctx: Context, file: File): Uri {
        val filePath = file.absoluteFile
        return FileProvider.getUriForFile(ctx, BuildConfig.APPLICATION_ID + ".provider", filePath)
    }

    interface DownloadFileListener {
        fun onDownloadComplete(file: File?)
    }

    fun saveQuotes(activity: Activity, view: View, extension: String) {
        val bm: Bitmap? = AppUtils.captureScreen(view)
        val file = AppUtils.getFile(activity, bm)
        val direct = File(
            activity.getExternalFilesDir(null).toString() + "/Collection/" + extension
        )
        if (!direct.exists()) {
            direct.mkdirs()
        }
        val f = File(direct.absolutePath, "" + System.currentTimeMillis() + extension)
        file?.copyTo(f)

        Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
    }

    fun saveItem(activity: Activity, ob: Any, extension: String) {
        getFile(activity, ob, object : DownloadFileListener {
            override fun onDownloadComplete(file: File?) {
                val direct = File(
                    activity.getExternalFilesDir(null).toString() + "/Collection/" + extension
                )
                if (!direct.exists()) {
                    direct.mkdirs()
                }
                val f = File(direct.absolutePath, "" + System.currentTimeMillis() + extension)
                file?.copyTo(f)
            }
        })
        Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
    }


    fun getCollection(activity: Activity, extension: String): ArrayList<String> {
        val list = ArrayList<String>()
        val file = File(activity.getExternalFilesDir(null).toString() + "/Collection/" + extension)
        if (file.exists()) {
            file.listFiles().forEach {
                list.add(it.path)
            }
        }
        return list
    }
}