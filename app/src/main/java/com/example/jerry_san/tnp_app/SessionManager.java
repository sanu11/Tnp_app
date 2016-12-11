
package com.example.jerry_san.tnp_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jerry_san.tnp_app.Activities.LoginActivity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Tnp_pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static  String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static  String KEY_EMAIL = "email";

    //Notifiction id
    public String NOTIFY_ID ="notifyId";
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */

    public void createLoginSession(String name, String email){
        // Storing login value as TRUE

        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putInt(NOTIFY_ID,0);

        // commit changes
        editor.commit();

    }

    /**
     * Get stored session data
     * */

    public String[] getUserDetails()
    {

        String array[]=new String[2];

        String name=pref.getString(KEY_NAME,"def");
        String email=pref.getString(KEY_EMAIL,"def");

        array[0]=name;
        array[1]=email;

        Log.i("My_tag","Logged in to "+ email);
        return array;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Log.i("My_tag","Logged out");
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}