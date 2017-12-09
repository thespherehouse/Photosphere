package com.suhel.photosphere.Singleton

import android.app.Application
import com.facebook.appevents.AppEventsLogger
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.suhel.photosphere.Singleton.context
import java.util.*

val APP_NAME = "Photosphere"

val calendar: Calendar = Calendar.getInstance()
val database: FirebaseDatabase = FirebaseDatabase.getInstance()
val auth: FirebaseAuth = FirebaseAuth.getInstance()
val storage: FirebaseStorage = FirebaseStorage.getInstance()


class PhotosphereApp : Application() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        AppEventsLogger.activateApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        Fresco.initialize(this)
    }

}
