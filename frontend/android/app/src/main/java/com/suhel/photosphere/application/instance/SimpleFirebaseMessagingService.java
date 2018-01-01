package com.suhel.photosphere.application.instance;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.suhel.photosphere.R;
import com.suhel.photosphere.screens.home.view.HomeActivity;

import java.util.Map;

public class SimpleFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() != null) {
            Map<String, String> map = remoteMessage.getData();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager == null)
                return;
            Notification notification = new NotificationCompat.Builder(this, "weird")
                    .setContentTitle("Photosphere")
                    .setContentText(map.get("userName") + " liked your post")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(PendingIntent.getActivity(this, 100, new Intent(this, HomeActivity.class), 0))
                    .build();
            notificationManager.notify(1000, notification);
        }
    }

}
