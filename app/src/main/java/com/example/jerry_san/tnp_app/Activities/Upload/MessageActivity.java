package com.example.jerry_san.tnp_app.Activities.Upload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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





    }

    public void  onClickPost(View v) {
        EditText editText = (EditText) findViewById(R.id.title);
        EditText editText2 = (EditText) findViewById(R.id.body);
        String title = editText.getText().toString();
        String body = editText2.getText().toString();
        JSONObject obj = new JSONObject();
        try {
            obj.put("title",title);
            obj.put("body", body);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        sendMessage = new SendMessagetoServer();
        String res = null;
        try {
            res = sendMessage.execute(obj.toString()).get();
            Log.i("My_tag",res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
