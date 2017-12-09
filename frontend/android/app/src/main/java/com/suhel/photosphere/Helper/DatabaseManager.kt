package com.suhel.photosphere.Helper

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import com.suhel.photosphere.DataModels.Types.Derived.Post
import java.io.File

var uploadTask: UploadTask? = null

fun uploadImage(filePath: String?, firebaseStorageFolder: String,
                progress: ((Float) -> Unit),
                completion: ((Boolean, String) -> Unit)?) {

    uploadTask?.let {
        if (it.isInProgress)
            return
    }

    filePath?.let {
        val file = File(it)
        val storageRef = FirebaseStorage.getInstance().reference
        val currentUser = FirebaseAuth.getInstance().currentUser
        val firebaseStoragePath = "$firebaseStorageFolder/${currentUser?.uid}/${file.name}"
        val fileStorageRef = storageRef.child(firebaseStoragePath)

        val metadata = StorageMetadata.Builder().setContentType("image/jpg").build()

        uploadTask = fileStorageRef.putFile(Uri.fromFile(file), metadata)

        uploadTask?.addOnFailureListener {
            completion?.invoke(false, firebaseStoragePath)
        }

        uploadTask?.addOnProgressListener {
            prog ->

            val percent = (prog.bytesTransferred.toFloat() * 100.0f / prog.totalByteCount.toFloat())
            progress.invoke(percent)
        }

        uploadTask?.addOnSuccessListener {
            completion?.invoke(true, firebaseStoragePath)
        }

    }
}

fun cancelUpload() {
    uploadTask?.cancel()
}

fun getNameById(id: String, callback: ((String?) -> Unit)?) {
    FirebaseDatabase.getInstance().reference
            .child("users")
            .child(id)
            .child("name")
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(data: DataSnapshot?) {
                    callback?.invoke(data?.value?.toString())
                }

            })
}

fun getEmailById(id: String, callback: ((String?) -> Unit)?) {
    FirebaseDatabase.getInstance().reference
            .child("users")
            .child(id)
            .child("email")
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(data: DataSnapshot?) {
                    callback?.invoke(data?.value?.toString())
                }

            })
}

fun getFacebookIdById(id: String, callback: ((String?) -> Unit)?) {
    FirebaseDatabase.getInstance().reference
            .child("users")
            .child(id)
            .child("facebookId")
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(data: DataSnapshot?) {
                    callback?.invoke(data?.value?.toString())
                }

            })
}

fun getOwnerIdByPost(post: Post, callback: ((String?) -> Unit)?) {
    FirebaseDatabase.getInstance().reference
            .child("posts")
            .child(post.id)
            .child("ownerId")
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(data: DataSnapshot?) {
                    callback?.invoke(data?.value?.toString())
                }

            })
}