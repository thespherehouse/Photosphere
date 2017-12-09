package com.suhel.photosphere.Helper

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.suhel.photosphere.Singleton.auth

class SimpleAnalytics {

    enum class EventType {
        NAVIGATE,
        ACTION,
        ERROR;

        var stringValue: String = ""
            get() = when (this) {

                SimpleAnalytics.EventType.NAVIGATE -> "navigate"
                SimpleAnalytics.EventType.ACTION -> "action"
                SimpleAnalytics.EventType.ERROR -> "error"
            }

        companion object {
            fun fromStringValue(stringValue: String): EventType =
                    when (stringValue) {
                        "navigate" -> NAVIGATE
                        "action" -> ACTION
                        "error" -> ERROR
                        else -> ACTION
                    }
        }
    }

    companion object {

        fun logEvent(type: EventType, description: String, callback: (() -> Unit)? = null) {
            val map = HashMap<String, Any>()
            map["userId"] = auth.currentUser!!.uid
            map["type"] = type.stringValue
            map["description"] = description
            map["time"] = ServerValue.TIMESTAMP

            FirebaseDatabase.getInstance().getReference("logs").push().setValue(map).addOnSuccessListener {
                callback?.invoke()
            }
        }

        fun logFeedback(feedback: String, callback: (() -> Unit)? = null) {
            val map = HashMap<String, Any>()
            map["userId"] = auth.currentUser!!.uid
            map["feedback"] = feedback
            map["time"] = ServerValue.TIMESTAMP

            FirebaseDatabase.getInstance().getReference("feedback").push().setValue(map).addOnSuccessListener {
                callback?.invoke()
            }
        }

    }

}