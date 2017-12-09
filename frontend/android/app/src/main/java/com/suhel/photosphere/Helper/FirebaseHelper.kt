package com.suhel.photosphere.Helper

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

fun generateFirebaseQuery(forPath: String, where: String, equals: String): Query =
        FirebaseDatabase.getInstance().getReference(forPath).orderByChild(where).equalTo(equals)

fun generateFirebaseChild(forPath: String): DatabaseReference =
        FirebaseDatabase.getInstance().getReference(forPath)

fun queryForAllPosts(): Query =
        FirebaseDatabase.getInstance().getReference("posts")

fun refForPath(vararg args: String): DatabaseReference {
    var ref = FirebaseDatabase.getInstance().reference
    args.forEach {
        ref = ref.child(it)
    }
    return ref
}
