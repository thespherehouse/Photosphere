package com.suhel.photosphere.Helper

import android.app.NotificationManager
import android.content.Context
import android.support.v7.app.NotificationCompat
import com.suhel.photosphere.R

private val APP_NOTIFICATION = 1
private var notifManager: NotificationManager? = null
private var notifBuilder: NotificationCompat.Builder? = null

private fun initNotifManager(context: Context) {
    if(notifManager == null) {
        notifManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}

private fun initNotifBuilder(context: Context) {
    if(notifBuilder == null) {
        notifBuilder = NotificationCompat.Builder(context)
    }
}

fun notifProgress(context: Context, percent: Int, title: String, content: String, ongoing: Boolean) {
    initNotifManager(context)
    initNotifBuilder(context)

    notifBuilder?.setContentTitle(title)
    notifBuilder?.setProgress(100, percent, false)
    notifBuilder?.setOngoing(ongoing)
    notifBuilder?.setSmallIcon(R.mipmap.ic_launcher)
    notifBuilder?.setContentText(content)

    notifManager?.notify(APP_NOTIFICATION, notifBuilder?.build())
}

fun notifInfo(context: Context, title: String, content: String, ongoing: Boolean) {
    initNotifManager(context)
    initNotifBuilder(context)

    notifBuilder?.setContentTitle(title)
    notifBuilder?.setOngoing(ongoing)
    notifBuilder?.setSmallIcon(R.mipmap.ic_launcher)
    notifBuilder?.setContentText(content)

    notifManager?.notify(APP_NOTIFICATION, notifBuilder?.build())
}

fun notifRemove(context: Context) {
    initNotifManager(context)
    initNotifBuilder(context)

    notifManager?.cancel(APP_NOTIFICATION)
}