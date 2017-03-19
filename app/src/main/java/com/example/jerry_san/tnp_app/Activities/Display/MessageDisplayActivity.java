package com.example.jerry_san.tnp_app.Activities.Display;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.jerry_san.tnp_app.R;

public class MessageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String t = getIntent().getExtras().getString("title");
        String b = getIntent().getExtras().getString("body");

        TextView textview =(TextView) findViewById (R.id.title);
        TextView textview2 =(TextView) findViewById (R.id.url);

        textview2.setAutoLinkMask(1);
        CharSequence title = t.subSequence(0, t.length());
        CharSequence  body = b.subSequence(0, b.length());

        textview.setText(title);
        textview2.setText(body);

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
