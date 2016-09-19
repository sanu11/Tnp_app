package com.example.jerry_san.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message_display);
        String t = getIntent().getExtras().getString("title");
        String b = getIntent().getExtras().getString("body");

        TextView textview =(TextView) findViewById (R.id.title);
        TextView textview2 =(TextView) findViewById (R.id.body);

        CharSequence title = t.subSequence(0, t.length());
        CharSequence  body = b.subSequence(0, b.length());

        textview.setText(title);
        textview2.setText(body);



    }
}
