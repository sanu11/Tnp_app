package com.example.jerry_san.tnp_app.Activities.Upload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jerry_san.tnp_app.NetworkConnection;
import com.example.jerry_san.tnp_app.R;
import com.example.jerry_san.tnp_app.RESTCalls.SendMessagetoServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MessageActivity extends AppCompatActivity {

    SendMessagetoServer sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NetworkConnection connection = new NetworkConnection(this.getApplicationContext());
        boolean con = connection.checkNetwork();

        if(!con) {
            Toast.makeText(MessageActivity.this, "Check your Network", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onClickPost(View v) throws ExecutionException, InterruptedException {

        NetworkConnection connection = new NetworkConnection(this.getApplicationContext());
        boolean con = connection.checkNetwork();
        //Log.i("My_tag","connection "+con);
        if(!con) {
            Toast.makeText(MessageActivity.this, "Check your Network", Toast.LENGTH_SHORT).show();
            return;
        }
        EditText editText = (EditText) findViewById(R.id.title);
        EditText editText2 = (EditText) findViewById(R.id.body);
        String title = editText.getText().toString();
        String body = editText2.getText().toString();
        JSONObject obj = new JSONObject();
        try {
            obj.put("title", title);
            obj.put("body", body);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        sendMessage = new SendMessagetoServer();
        String res = null;


        res = sendMessage.execute(obj.toString()).get();
        Log.i("My_tag", "response " + res);
        if (res == null) {
            Toast.makeText(MessageActivity.this, "Message sending Failed", Toast.LENGTH_SHORT).show();
            Log.i("My_tag", "Message sending Failed");
        } else {
            res.replace("\n", " ");
            res.trim();
            Toast.makeText(MessageActivity.this, res, Toast.LENGTH_SHORT).show();
            Log.i("My_tag", res);
        }


        finish();
    }

}
