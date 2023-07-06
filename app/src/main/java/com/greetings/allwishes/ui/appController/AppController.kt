package com.greetings.allwishes.ui.appController

import com.onesignal.OneSignal
import com.sm.newadlib.app.LibApplication

class AppController : LibApplication() {


    private val ONESIGNAL_APP_ID = "86ba8c87-8b44-4dd6-a5d5-6b8ea125635b"

    override fun onCreate() {
        super.onCreate()


        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}