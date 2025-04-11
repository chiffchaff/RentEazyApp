package com.renteazy.app

import android.app.Application
import com.google.firebase.FirebaseApp

class RentEazyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
