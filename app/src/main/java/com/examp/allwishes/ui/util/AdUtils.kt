package com.greetings.allwishes.util

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation.findNavController
import com.examp.allwishes.R
import com.examp.allwishes.ui.adapter.DownloadedAdapter
import java.io.File

object AdUtils {

//    fun showEntryFullAd(activity: Activity, listener: AdListener) {
//
//
//        AdsHandler.showEntryFullAd(activity, object : FullAdListener {
//            override fun onComplete(isAdDisplay: Boolean, adNetwork: String) {
//                listener.onComplete()
//            }
//        })
//    }

    fun showFullAd(activity: Activity, listener: AdListener) {
//        if (SplashActivityRelease.isAdsCheck) {
//            showInterstitialAds(activity, object : FullAdListener {
//                override fun onComplete(isAdDisplay: Boolean, adNetwork: String) {
//                    listener.onComplete()
//                }
//            })
//        } else {
//            listener.onComplete()
//        }
    }


    fun showNativeBanner(activity: Activity, adContainer: ViewGroup) {
//        if (SplashActivityRelease.isAdsCheck) {
//            AdsHandler.showNativeBannerAd(activity, adContainer)
//        }
    }

    fun showNative(activity: Activity, adContainer: CardView) {
//        if (SplashActivityRelease.isAdsCheck) {
//            AdsHandler.showNativeAd(activity, adContainer)
//        }
    }

    interface AdListener {
        fun onComplete()
    }

    fun changeFragment(activity: Activity, resId: Int, b: Bundle) {
        showFullAd(activity, object : AdListener {
            override fun onComplete() {
                var dTime = 10L
//                if (adNetwork.equals("Mopub", ignoreCase = true)) {
//                    dTime = 500L
//                }
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        try {
                            findNavController(
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
            }
        })
    }

    fun removeFromFavDialog(
        activity: Activity, list: ArrayList<String>,
        pos: Int,
        btnclck: String,
        adapter: DownloadedAdapter
    ) {
//        println("removeFromFavDialog is called")
        val dialog = Dialog(activity, R.style.Theme_Dialog)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.ask_remove_dialog)
        var query = dialog.findViewById<TextView>(R.id.query)
        if (btnclck.equals("Downloaded")) {
//            query.text = activity.resources.getString(R.string.down_query)
        }
//        showNativeAd(dialog.findViewById<View>(R.id.smNativeAdContainer) as ViewGroup)
        dialog.findViewById<View>(R.id.cancel)
            .setOnClickListener { view: View? -> dialog.dismiss() }
        dialog.findViewById<View>(R.id.delete).setOnClickListener { view: View? ->

            removeFavItem(
                activity,
                pos, adapter, list
            )

            dialog.dismiss()
//                adapter.notifyDataSetChanged()
//            activity.finish()
        }
        dialog.show()
    }

    fun removeFavItem(
        activity: Activity?,
        pos: Int,
        adapter: DownloadedAdapter,
        list: ArrayList<String>
    ) {
        val f = File(list.get(pos))
        if (f.exists()) {
            if (f.delete()) {
                list.remove(list.get(pos))
                adapter.notifyDataSetChanged()
//                update(gifUrl,list, adapter)
                Toast.makeText(activity, "Item Deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Failed to delete", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Item does not exist", Toast.LENGTH_SHORT).show()
        }
    }


    interface ServerCallBack {
        fun onSuccess(isSuccess: Boolean, string: String)
    }


}