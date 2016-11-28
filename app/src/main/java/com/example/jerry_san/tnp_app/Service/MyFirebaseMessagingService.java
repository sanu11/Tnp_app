package com.example.jerry_san.tnp_app.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jerry_san.tnp_app.Activities.CompanyDisplayActivity;
import com.example.jerry_san.tnp_app.Activities.CompanyUpdateDisplayActivity;
import com.example.jerry_san.tnp_app.Activities.MessageDisplayActivity;
import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;
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
        Log.i(TAG, "Notification received From: " + remoteMessage.getFrom());

        //Calling method to generate notification
        String type=remoteMessage.getData().get("type") ;

        if(type.equals("gen_msg")) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            Log.i("My_tag", title + " " + body);
            sendNotification(title, body);
        }

        else if(type.equals("company_reg"))
        {
            String name = remoteMessage.getData().get("name");
            String criteria = remoteMessage.getData().get("criteria");
            String salary = remoteMessage.getData().get("salary");
            String back = remoteMessage.getData().get("back");
            String other_details = remoteMessage.getData().get("other_details");
            String date_time  = remoteMessage.getData().get("ppt_date");
            sendNotification(name,criteria,salary,back,other_details,date_time);


        }
        else if(type.equals("company_update")){
            String name = remoteMessage.getData().get("name");
            String reg_link=remoteMessage.getData().get("reg_link");
            String reg_start=remoteMessage.getData().get("reg_start");
            String reg_end=remoteMessage.getData().get("reg_end");
            String other_details=remoteMessage.getData().get("other_details");

            sendNotification(name,reg_link,reg_start,reg_end,other_details);

        }


    }

    private void sendNotification(String name, String criteria, String salary, String back, String other_details, String ppt_date) {
        Intent intent = new Intent(this, CompanyDisplayActivity.class);

        //add to local database
        LocalDatabase localDatabase= new LocalDatabase(getApplicationContext());
        long res= localDatabase.company_insert(name,criteria,salary,back,ppt_date,other_details,0);

        if(res>0)
            Log.i("My_tag","Added Successfully Locally");
        else
            Log.i("My_tag","Addition to Local database failed");

        Log.i("My_tag"," pos " + String.valueOf(res));

        int pos=(int)res;
        pos--;
        intent.putExtra("position",pos);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Upcoming Company")
                .setContentText(name)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
        Log.i("My_tag","Company Notification sent");

    }

    private void sendNotification(String title, String body ) {

        Intent intent = new Intent(this, MessageDisplayActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("body",body);

        LocalDatabase localDatabase= new LocalDatabase(getApplicationContext());
        boolean res= localDatabase.message_insert(title,body);
        if(res)
            Log.i("My_tag","Added Successfully Locally");
        else
            Log.i("My_tag","Addition to Local database failed");

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

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
        Log.i("My_tag","Message Notification sent");
    }

    private void sendNotification(String name, String reg_link, String reg_start, String reg_end, String other_details) {
        Intent intent = new Intent(this, CompanyUpdateDisplayActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("reg_link",reg_link);
        intent.putExtra("reg_start",reg_start);
        intent.putExtra("reg_end",reg_end);
        intent.putExtra("other_details",other_details);

        //add to local database
        LocalDatabase localDatabase= new LocalDatabase(getApplicationContext());
        long res= localDatabase.company_update(name,reg_link,reg_start,reg_end,other_details);

        if(res>0)
            Log.i("My_tag","Updated Successfully Locally");
        else
            Log.i("My_tag","Updation to Local database failed");

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Company Details")
                .setContentText(name)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
        Log.i("My_tag","Company  Update Notification sent");


    }


}
