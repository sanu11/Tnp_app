package com.example.jerry_san.tnp_app;
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
    private  SessionManager sessionManager;
    @Override

//  String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

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

        sessionManager=new SessionManager(getApplicationContext());
        String array[]= sessionManager.getUserDetails();

        String user=array[0];
        JSONObject obj = new JSONObject();
        Log.i("My_tag","in firebase " + user);
        try {
            obj.put("dev_id", 2);
            obj.put("reg_id", token);
            obj.put("name","2");
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        Log.i("My_tag","firebase");
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