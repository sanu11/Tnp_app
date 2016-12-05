package com.example.jerry_san.tnp_app.Activities.Upload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jerry_san.tnp_app.Activities.HomeActivity;
import com.example.jerry_san.tnp_app.Activities.LoginActivity;
import com.example.jerry_san.tnp_app.NetworkConnection;
import com.example.jerry_san.tnp_app.R;
import com.example.jerry_san.tnp_app.RESTCalls.RegisterStudent;
import com.example.jerry_san.tnp_app.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class StudentRegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Switch sw = (Switch) findViewById(R.id.placed);
        sw.setTextOff("NO");
        sw.setTextOn("YES");

        NetworkConnection connection = new NetworkConnection(this.getApplicationContext());
        boolean con = connection.checkNetwork();
        Log.i("My_tag","connection "+con);
        if(!con) {
            Toast.makeText(StudentRegActivity.this, "Check your Network", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public void onClick(View v) throws JSONException, ExecutionException, InterruptedException {


        NetworkConnection connection = new NetworkConnection(this.getApplicationContext());
        boolean con = connection.checkNetwork();
        Log.i("My_tag","connection "+con);
        if(!con) {
            Toast.makeText(StudentRegActivity.this, "Check your Network", Toast.LENGTH_SHORT).show();
            return;
        }
        EditText e1 = (EditText) findViewById(R.id.name);
        EditText e2 = (EditText) findViewById(R.id.email);
        EditText e3 = (EditText) findViewById(R.id.password);
        EditText e4 = (EditText) findViewById(R.id.phone);
        Spinner e5 = (Spinner) findViewById(R.id.branch);
        EditText e6 = (EditText) findViewById(R.id.average);
        Switch e7 = (Switch) findViewById(R.id.placed);
        Switch e8 = (Switch) findViewById(R.id.active_back);

        String name = e1.getText().toString();
        String email = e2.getText().toString();
        String password = e3.getText().toString();
        String phone = e4.getText().toString();
        String branch = String.valueOf(e5.getSelectedItem());
        String avg = e6.getText().toString();
        String placed = null;
        String active_back = null;

        String temp = name;
        if (temp.trim().equals("") || email.trim().equals("") || password.trim().equals("") || phone.trim().equals("") || avg.trim().equals("")) {
            Toast.makeText(StudentRegActivity.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
            return;
        }

        email.trim();
        CharSequence mail = email.subSequence(0, email.length());
        boolean matches = android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        if (!matches) {
            Toast.makeText(StudentRegActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.length() != 10) {
            Toast.makeText(StudentRegActivity.this, "Invalid Mobile", Toast.LENGTH_SHORT).show();
            return;
        }

        Float average = Float.parseFloat(avg);

        if (e7.isChecked())
            placed = "YES";
        else
            placed = "NO";

        if (e8.isChecked())
            active_back = "YES";
        else
            active_back = "NO";

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
        else if (res.equals("Already Registered\n")) {
            Toast.makeText(StudentRegActivity.this, "Already Registered , Please Login", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            res.replace("\n"," ");
            res.trim();
            Toast.makeText(StudentRegActivity.this, res, Toast.LENGTH_SHORT).show();
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.createLoginSession(name, email);
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
        //do nothing

    }
}