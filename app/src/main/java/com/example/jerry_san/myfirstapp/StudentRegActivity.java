package com.example.jerry_san.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class StudentRegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);
        Switch sw = (Switch) findViewById(R.id.placed);
        sw.setTextOff("NO");
        sw.setTextOn("YES");
    }


    public void onClick(View v) throws JSONException, ExecutionException, InterruptedException {


        EditText e1 = (EditText) findViewById(R.id.name);
        EditText e2 = (EditText) findViewById(R.id.email);
        EditText e3 = (EditText) findViewById(R.id.name);
        EditText e4 = (EditText) findViewById(R.id.phone);
        Spinner e5 = (Spinner) findViewById(R.id.branch);
        EditText e6 = (EditText) findViewById(R.id.average);
        Switch e7=(Switch) findViewById(R.id.placed);
        Switch e8=(Switch) findViewById(R.id.active_back);

        String name = e1.getText().toString();
        String email = e2.getText().toString();
        String password = e3.getText().toString();
        String phone = e4.getText().toString();
        String branch = String.valueOf(e5.getSelectedItem());
        float average = Float.parseFloat(e6.getText().toString());
        String placed=null;
        String active_back=null;
        if(e7.isChecked())
            placed="YES";
        else
            placed="NO";
        if(e8.isChecked())
            active_back="YES";
        else
            active_back="NO";

        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("email", email);
            obj.put("password", password);
            obj.put("phone", phone);
            obj.put("branch", branch);
            obj.put("average", average);
            obj.put("placed", placed);
            obj.put("activeBack", active_back);
            Log.i("My_tag", obj.toString(0));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Toast.makeText(StudentRegActivity.this, "Please Wait ", Toast.LENGTH_SHORT).show();

        RegisterStudent data = new RegisterStudent();
        String res = data.execute(obj.toString()).get();
        Log.i("My_tag", "response  " + res);

        if (res == null)
            Toast.makeText(StudentRegActivity.this, "Registered Unsuccessful", Toast.LENGTH_SHORT).show();
        else
        {
            Toast.makeText(StudentRegActivity.this, "Registered Successfullly", Toast.LENGTH_SHORT).show();
            SessionManager sessionManager  = new SessionManager(this);
            sessionManager.createLoginSession(name,email);
            startService(new Intent(getBaseContext(), MyFirebaseInstanceIDService.class));
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        }

    }

}
