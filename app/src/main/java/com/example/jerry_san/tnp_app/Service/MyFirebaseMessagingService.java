package com.example.jerry_san.tnp_app.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jerry_san.tnp_app.Activities.Display.CompanyDisplayActivity;
import com.example.jerry_san.tnp_app.Activities.Display.CompanyUpdateDisplayActivity;
import com.example.jerry_san.tnp_app.Activities.Display.MessageDisplayActivity;
import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jerry-san on 9/11/16.
 */

public class MyFirebaseMessagingService  extends FirebaseMessagingService {
    private static final String TAG = "My_tag";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.i(TAG, "Notification received From: " + remoteMessage.getFrom());

        //Calling method to generate notification
        String type = remoteMessage.getData().get("type");

        if (type.equals("gen_msg")) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");

            sendNotification(title, body);
        } else if (type.equals("company_reg")) {
            JSONObject object = new JSONObject();
            String name = remoteMessage.getData().get("name");
            String criteria = remoteMessage.getData().get("criteria");
            String salary = remoteMessage.getData().get("salary");
            String back = remoteMessage.getData().get("back");
            String other_details = remoteMessage.getData().get("other_details");
            String ppt_date = remoteMessage.getData().get("ppt_date");


            try {
                object.put("name", name);
                object.put("criteria", criteria);
                object.put("salary", salary);
                object.put("back", back);
                if(other_details!=null)
                    object.put("other_details", other_details);
                object.put("ppt_date", ppt_date);
                object.put("reg_link", JSONObject.NULL);
                object.put("reg_start", JSONObject.NULL);
                object.put("reg_end", JSONObject.NULL);
                object.put("hired", 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                sendNotification(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (type.equals("company_update")) {
            JSONObject object = new JSONObject();
            String name = remoteMessage.getData().get("name");
            String reg_link = remoteMessage.getData().get("reg_link");
            String reg_start = remoteMessage.getData().get("reg_start");
            String reg_end = remoteMessage.getData().get("reg_end");
            String other_details = remoteMessage.getData().get("other_details");

            try {
                object.put("name", name);
                object.put("reg_link", reg_link);
                object.put("reg_start", reg_start);
                object.put("reg_end", reg_end);
                object.put("other_details", other_details);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                sendNotification(object, 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void sendNotification(JSONObject object) throws JSONException {
        Intent intent = new Intent(this, CompanyDisplayActivity.class);

        //add to local database
        LocalDatabase localDatabase = new LocalDatabase(getApplicationContext());
        long res = localDatabase.companyInsertJson(object);
        if (res > 0)
            Log.i("My_tag", "Added Successfully Locally");
        else
            Log.i("My_tag", "Addition to Local database failed");

        Log.i("My_tag", " pos " + String.valueOf(res));

        int pos = (int) res;
        pos--;
        intent.putExtra("position", pos);
        int n = getNotificationId();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, n, intent, 0);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Upcoming Company")
                .setContentText(object.getString("name"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(n, notificationBuilder.build());
        Log.i("My_tag", "Company Notification sent");

    }

    private void sendNotification(String title, String body) {

        Intent intent = new Intent(this, MessageDisplayActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("body", body);

        LocalDatabase localDatabase = new LocalDatabase(getApplicationContext());
        boolean res = localDatabase.messageInsert(title, body);
        if (res)
            Log.i("My_tag", "Added Successfully Locally");
        else
            Log.i("My_tag", "Addition to Local database failed");

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int n = getNotificationId();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, n, intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(n, notificationBuilder.build());
        Log.i("My_tag", "Message Notification sent");
    }


    private void sendNotification(JSONObject object, int i) throws JSONException {
        Intent intent = new Intent(this, CompanyUpdateDisplayActivity.class);

        intent.putExtra("update", object.toString());
        //add to local database
        LocalDatabase localDatabase = new LocalDatabase(getApplicationContext());
        long res = localDatabase.companyUpdate(object);

        if (res > 0)
            Log.i("My_tag", "Updated Successfully Locally");
        else
            Log.i("My_tag", "Updation to Local database failed");

        int pos = (int) res;

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int n = getNotificationId();

        PendingIntent pendingIntent = PendingIntent.getActivity(this, n, intent, 0);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Company Details")
                .setContentText(object.getString("name"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(n, notificationBuilder.build());
        Log.i("My_tag", "Company  Update Notification sent");

    }

    public int getNotificationId() {

        String PREF_NAME = "Tnp_pref";
        int n;
        // Shared Preferences
        SharedPreferences pref;
        // Editor for Shared preferences
        SharedPreferences.Editor editor;
        pref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor= pref.edit();
        n= pref.getInt("notifyId",1);
        editor.putInt("notifyId",++n);
        editor.commit();
        return n;

    }
}
