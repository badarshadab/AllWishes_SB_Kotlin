package com.greetings.allwishes.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.NetworkError
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.ServerError
import com.android.volley.TimeoutError
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.greetings.allwishes.R
import com.greetings.allwishes.ui.adapter.DownloadedAdapter
import com.greetings.allgreetingwishes.utils.ServerRequest
import com.sm.newadlib.handlers.AdsHandler
import com.sm.newadlib.listeners.FullAdListener
import org.json.JSONObject
import java.io.File

object AdUtils {

    var isAdsCheck = false

    fun showEntryFullAd(activity: Activity, listener: AdListener) {
        if (isAdsCheck) {
            AdsHandler.showEntryInterstitialAds(activity, object : FullAdListener {
                override fun onComplete(isAdDisplay: Boolean, adNetwork: String) {
                    listener.onComplete()
                }
            })
        } else {
            listener.onComplete()
        }

    }

    fun showFullAd(activity: Activity, listener: AdListener) {
        if (isAdsCheck) {
            AdsHandler.showInterstitialAds(activity, object : FullAdListener {
                override fun onComplete(isAdDisplay: Boolean, adNetwork: String) {
                    listener.onComplete()
                }
            })
        } else {
            listener.onComplete()
        }
    }


    fun showNativeBanner(activity: Activity, adContainer: ViewGroup) {
        if (isAdsCheck) {
            AdsHandler.showNativeBannerAd(activity, adContainer)
        }
    }

    fun showNative(activity: Activity, adContainer: ViewGroup) {
        if (isAdsCheck) {
            AdsHandler.showNativeAd(activity, adContainer)
        }
    }

    interface AdListener {
        fun onComplete()
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

    fun enabledisable(context: Context) {
        doStringRequest(
            context,
            context.resources.getString(R.string.enabledisable),
            object : ServerCallBack {
                override fun onSuccess(isSuccess: Boolean, string: String) {
                    if (isSuccess) {
                        try {
                            val ob = JSONObject(string)
                            val vcode = ob.getString("vCode")
                            isAdsCheck = ob.getBoolean("enableCheck")
                        } catch (e: Exception) {
                        }
                    }
                }
            })
    }

    fun doStringRequest(context: Context, url: String, serverCallBack: ServerCallBack) {
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                serverCallBack.onSuccess(true, response)
            },
            { error ->
                serverCallBack.onSuccess(false, getVolleyError(error))
            })
        ServerRequest.addToRequestQueue(context, stringRequest)
    }

    private fun getVolleyError(volleyError: VolleyError?): String {
        var msg: String
        msg = "Network Error"
        when (volleyError) {
            is NetworkError -> {
                msg = "Cannot connect to Internet...Please check your connection!"
            }

            is ServerError -> {
                msg = "The server could not be found. Please try again after some time!!"
            }

            is AuthFailureError -> {
                msg = "Cannot connect to Internet...Please check your connection!"
            }

            is ParseError -> {
                msg = "Parsing error! Please try again after some time!!"
            }

            is TimeoutError -> {
                msg = "Connection TimeOut! Please check your internet connection."
            }
        }
        return msg
    }


}