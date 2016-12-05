package com.example.jerry_san.tnp_app;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by jerry-san on 9/23/16.
 */
public  class NetworkConnection{
    Context context;
    public NetworkConnection(Context applicationContext) {
    context = applicationContext;
    }

    public  boolean checkNetwork (){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Log.i("My_tag","connection "+ isConnected);
        return isConnected;
    }
}

