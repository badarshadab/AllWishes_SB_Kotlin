package com.greetings.allgreetingwishes.utils

import android.content.Context
import android.text.TextUtils
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object ServerRequest {

    private val TAG = ServerRequest::class.java.simpleName

    private var mRequestQueue: RequestQueue? = null
    private fun getRequestQueue(ctx: Context): RequestQueue {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ctx)
        }
        return mRequestQueue!!
    }

    fun <T> addToRequestQueue(ctx: Context, req: Request<T>, tag: String?) {
        // set the default tag if tag is empty
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions.
        //Volley does retry for you if you have specified the policy.
        req.retryPolicy = DefaultRetryPolicy(
            20000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        getRequestQueue(ctx).add(req)
    }

    fun <T> addToRequestQueue(ctx: Context, req: Request<T>) {
        req.tag = TAG
        getRequestQueue(ctx).add(req)
    }

    fun cancelPendingRequests(tag: Any?) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }

//    companion object {
//        private val TAG = ServerRequest::class.java.simpleName
//        private var mInstance: ServerRequest? = null
//
//        @get:Synchronized
//        val instance: ServerRequest?
//            get() {
//                if (mInstance == null) {
//                    mInstance = ServerRequest()
//                }
//                return mInstance
//            }
//    }
}