package com.greetings.allwishes.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.greetings.allwishes.R
import com.greetings.allwishes.ui.activity.MainActivity
import com.greetings.allwishes.util.AdUtils
import com.sm.newadlib.app.LibSplashActivity


class SplashActivity : LibSplashActivity() {

    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        handler = Handler(mainLooper)
        AdUtils.enabledisable(this)
        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        val SPLASH_TIME_OUT = 7000
        handler!!.postDelayed({ goToMainActivity() }, SPLASH_TIME_OUT.toLong())
    }

    private fun goToMainActivity() {
        if (handler != null) {
            handler!!.removeCallbacksAndMessages(null)
            AdUtils.showEntryFullAd(this, object : AdUtils.AdListener {
                override fun onComplete() {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            })
        }
    }
}