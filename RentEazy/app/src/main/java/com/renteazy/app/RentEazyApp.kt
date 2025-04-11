package com.renteazy.app

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RentEazyApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseAuth.getInstance().firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
        FirebaseFirestore.setLoggingEnabled(true) // Enable Firestore debug logging
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        androidx.multidex.MultiDex.install(this)
    }
}
