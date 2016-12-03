package com.example.jerry_san.tnp_app.Service;
import android.content.Context;
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
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String android_id = tm.getDeviceId();
//        String android_id = Secure.getString(getContentResolver(),
//                Secure.ANDROID_ID);

        Log.i("My_tag",android_id);

        JSONObject obj = new JSONObject();
        try {
            obj.put("dev_id", android_id);
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
