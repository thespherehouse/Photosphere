package com.suhel.photosphere.Singleton

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.facebook.CallbackManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.suhel.photosphere.Singleton.FIELD_INFO_ROOT
import com.suhel.photosphere.DataModels.Types.Derived.Support
import java.util.*

val REQ_FROM_GALLERY = 2
val REQ_FROM_EXTERNAL_CAMERA = 3
val REQ_GOOGLE_SIGN_IN = 6

var activity: AppCompatActivity? = null
var context: Context? = null

var callbackManager: CallbackManager? = null
var currentFilePath: String? = null

var deleteFileAfterUploading = Stack<String>()

var support = Support()
var supportListener = object : ChildEventListener {

    override fun onCancelled(p0: DatabaseError?) {
    }

    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
    }

    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
        p0?.let {
            support = Support.fromDataSnapshot(it)
            supportCallback?.invoke(support)
        }
    }

    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
        p0?.let {
            support = Support.fromDataSnapshot(it)
            supportCallback?.invoke(support)
        }
    }

    override fun onChildRemoved(p0: DataSnapshot?) {
        support = Support()
        supportCallback?.invoke(support)
    }

}

var supportCallback: ((Support) -> Unit)? = null

fun addSupportListener() {
    FirebaseDatabase.getInstance().getReference(FIELD_INFO_ROOT).addChildEventListener(supportListener)
}

fun removeSupportListener() {
    FirebaseDatabase.getInstance().getReference(FIELD_INFO_ROOT).removeEventListener(supportListener)
}