package com.sm.allwishes.greetings.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.examp.allwishes.ui.activity.MainActivity
import com.examp.allwishes.R


class SplashActivity : AppCompatActivity() {


    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        handler = Handler(mainLooper)
        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        val SPLASH_TIME_OUT = 7000
        handler!!.postDelayed({ goToMainActivity() }, SPLASH_TIME_OUT.toLong())
    }

    private fun goToMainActivity() {
        if (handler != null) {
            handler!!.removeCallbacksAndMessages(null)

            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()

        }
    }
}