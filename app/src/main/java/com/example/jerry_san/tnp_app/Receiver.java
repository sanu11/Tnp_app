package com.example.jerry_san.tnp_app;

/**
 * Created by Sanika on 08-Dec-16.
 */
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.jerry_san.tnp_app.Service.AlarmService;

import static android.content.Intent.getIntent;

public class Receiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i("My_tag","Receiver ");
        String name = intent.getExtras().getString("name");
        String reg_link  = intent.getExtras().getString("reg_link");
        Intent service1 = new Intent(context, AlarmService.class);
        service1.putExtra("name",name);
        service1.putExtra("reg_link",reg_link);
        context.startService(service1);
    }
}
