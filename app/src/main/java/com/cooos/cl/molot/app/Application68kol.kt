package com.cooos.cl.molot.app

import android.app.Application
import com.onesignal.OneSignal

class Application68kol : Application() {

    override fun onCreate() {
        OneSignal.initWithContext(this)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.setAppId(CODED_ONESIGNAL_KEY_68kol.decodebase6468kol())
        super.onCreate()
    }
}