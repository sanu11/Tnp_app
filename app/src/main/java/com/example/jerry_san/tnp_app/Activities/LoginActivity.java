package com.example.jerry_san.tnp_app.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jerry_san.tnp_app.RESTCalls.LoginCheckServer;
import com.example.jerry_san.tnp_app.R;
import com.example.jerry_san.tnp_app.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    static final String PREF_USER_NAME = "username";

    LoginCheckServer logincheckServer ;
    SessionManager sessionManager;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sessionManager = new SessionManager(getApplicationContext());

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Login Failed");
        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

    }

    public void onClickLogin(View v) {

        EditText user = (EditText) findViewById(R.id.username);
        EditText pass = (EditText) findViewById(R.id.password);
        String email = user.getText().toString();
        String password = pass.getText().toString();

        if (email.length() > 0 && password.length() > 0) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("email", email);
                obj.put("password", password);


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.i("My_tag",email);
            logincheckServer = new LoginCheckServer();
            String res = null;

            try {
                res = logincheckServer.execute(obj.toString()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            String user_found="User not found";
            String incorrect_pw="Incorrect Password";

            if (res.equals(incorrect_pw)) {
                alertDialogBuilder.setMessage("Incorrect Password");
                alertDialogBuilder.show();
            }
            else if (res.equals(user_found)) {
                alertDialogBuilder.setMessage("User not Found");
                alertDialogBuilder.show();
            }
            else
            {
                //create session
                sessionManager.createLoginSession(res, email);

                // Staring MainActivity
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                finish();
                Log.i("My_tag","Login Successful");
            }
        }

        else
        {
            alertDialogBuilder.setMessage("Enter all Details");
            alertDialogBuilder.show();

        }
    }


@Override
    public void onBackPressed() {
    finish();
        //do nothing
    }

    public  void  onClickRegister(View v)
    {
        Intent i = new Intent(getApplicationContext(), StudentRegActivity.class);
        startActivity(i);
        finish();
        return;
    }
}

