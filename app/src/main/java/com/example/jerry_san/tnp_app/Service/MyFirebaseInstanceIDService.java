package com.example.jerry_san.tnp_app.Service;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.jerry_san.tnp_app.RESTCalls.SendTokenToServer;
import com.example.jerry_san.tnp_app.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import android.provider.Settings.Secure;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by jerry-san on 9/11/16.
 */

public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService {
    private static final String TAG = "My_tag";
    private SessionManager sessionManager;


//  String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

    @Override
    public  void  onCreate()
    {
//        Log.i("My_tag","hi");
//        String CurrentToken = FirebaseInstanceId.getInstance().getToken();
//        Log.i("My_tag", "Refreshed token: " + CurrentToken);
        super.onCreate();

    }

    public void onTokenRefresh() {

        //Getting registration token

        Log.i("My_tag","firebase");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.i("My_tag", "Refreshed token: " + refreshedToken);

        try {
            sendRegistrationToServer(refreshedToken);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(String token) throws ExecutionException, InterruptedException {
        //You can implement this method to store the token on your server
        //Not required for current project
        //
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();

//        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(getApplicationContext(),
//                    new String[]{Manifest.permission.READ_PHONE_STATE},
//                    0);
//
//        }
//        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        String android_id = tm.getDeviceId();

        Log.i("My_tag",address);

        JSONObject obj = new JSONObject();
        try {
            obj.put("dev_id", address);
            obj.put("reg_id", token);
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        SendTokenToServer token_obj = new SendTokenToServer();
        String res = null;
        try {
            res = token_obj.execute(obj.toString()).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("My_tag",res);

    }
}
