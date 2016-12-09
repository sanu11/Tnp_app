package com.example.jerry_san.tnp_app.Service;

/**
 * Created by Sanika on 08-Dec-16.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jerry_san.tnp_app.Activities.Display.CompanyDisplayActivity;
import com.example.jerry_san.tnp_app.Activities.Display.MessageDisplayActivity;
import com.example.jerry_san.tnp_app.Activities.HomeActivity;
import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;


public class AlarmService extends Service
{

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags,startId);

        LocalDatabase localDatabase =new LocalDatabase(this);
        String name = intent.getExtras().getString("name");
        String reg_link = intent.getExtras().getString("reg_link");
        int pos=localDatabase.getCompanyPosition(name);
        pos--;
        Intent intent1 = new Intent(this, CompanyDisplayActivity.class);
        intent1.putExtra("position",pos);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Reminder for Registration")
                .setContentText(name)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}