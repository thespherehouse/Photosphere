package com.suhel.photosphere.Helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.suhel.photosphere.Singleton.activity
import java.util.*

val PERMISSION_REQUEST_CODE = 5

var PERMISSION_SUCCESS_TASK: (() -> Unit)? = null
var PERMISSIONS_REQUESTED_CURRENTLY = ArrayList<String>()

fun checkPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}

fun askForPermissionsWithCallback(permissions: ArrayList<String>, callback: (() -> Unit)?) {

    PERMISSIONS_REQUESTED_CURRENTLY.clear()

    trimPermissions(activity!!, permissions)

    if (permissions.isEmpty()) {
        callback?.invoke()
    } else {
        PERMISSIONS_REQUESTED_CURRENTLY.addAll(permissions)
        PERMISSION_SUCCESS_TASK = callback
        ActivityCompat.requestPermissions(activity!!, PERMISSIONS_REQUESTED_CURRENTLY.toTypedArray(), PERMISSION_REQUEST_CODE)
    }
}

fun invokeTaskForPermissionSuccess(context: Context) {

    trimPermissions(context, PERMISSIONS_REQUESTED_CURRENTLY)

    if (PERMISSIONS_REQUESTED_CURRENTLY.isEmpty())
        PERMISSION_SUCCESS_TASK?.invoke()

}

fun trimPermissions(context: Context, permissions: ArrayList<String>): ArrayList<String> {

    permissions.filter { checkPermission(context, it) }.forEach { permissions.remove(it) }
    return permissions
}