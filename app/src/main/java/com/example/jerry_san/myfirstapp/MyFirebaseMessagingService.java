package com.example.jerry_san.myfirstapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
/**
 * Created by jerry-san on 9/11/16.
 */
public class MyFirebaseMessagingService  extends FirebaseMessagingService{
    private static final String TAG ="My_tag";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.i(TAG, "From: " + remoteMessage.getFrom());

        //Calling method to generate notification

        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("body");
        Log.i("My_tag",title + " " + body) ;
        sendNotification(title,body);

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String title, String body ) {
        Log.i("My_tag","notify");

        Intent intent = new Intent(this, MessageDisplayActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("body",body);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Log.i("My_tag","notify");

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
