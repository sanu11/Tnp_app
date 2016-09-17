package com.example.jerry_san.myfirstapp;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by jerry-san on 9/11/16.
 */
public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService {
    private static final String TAG = "My_tag";
    private  SessionManager sessionManager=new SessionManager(getApplicationContext());
    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.i("My_tag", "Refreshed token: " + refreshedToken);
        JSONObject obj = new JSONObject();
//        String android_id = Settings.Secure.getString(getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//        Log.i("My_tag",android_id);

        //get session details
        String array[]= sessionManager.getUserDetails();

        String user=array[0];

        try {
            obj.put("dev_id", 1);
            obj.put("reg_id", refreshedToken);
            obj.put("name",user);
        } catch (JSONException e) {
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

    private void sendRegistrationToServer(String token) throws ExecutionException, InterruptedException {
        //You can implement this method to store the token on your server
        //Not required for current project

    }
}
