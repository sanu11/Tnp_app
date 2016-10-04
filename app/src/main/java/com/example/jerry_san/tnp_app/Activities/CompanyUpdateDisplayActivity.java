package com.example.jerry_san.tnp_app.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.jerry_san.tnp_app.R;

public class CompanyUpdateDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_update_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        String name_str = getIntent().getExtras().getString("name");
        String reg_link_str = getIntent().getExtras().getString("reg_link");
        String reg_start_str = getIntent().getExtras().getString("reg_start");
        String reg_end_str = getIntent().getExtras().getString("reg_end");
//        String other_details_str = getIntent().getExtras().getString("other_details");

        TextView textview =(TextView) findViewById (R.id.name);
        TextView textview2 =(TextView) findViewById (R.id.reg_link);
        TextView textview3 =(TextView) findViewById (R.id.reg_start);
        TextView textview4 =(TextView) findViewById (R.id.reg_end);
//        TextView textview5 =(TextView) findViewById (R.id.name);

        CharSequence name = name_str.subSequence(0, name_str.length());
        CharSequence reg_link = reg_link_str.subSequence(0, reg_link_str.length());
        CharSequence reg_start = reg_start_str.subSequence(0, reg_start_str.length());
        CharSequence reg_end= reg_end_str.subSequence(0, reg_end_str.length());
//        CharSequence other_details= name_str.subSequence(0, other_details_str.length());


        textview.setText(name);
        textview2.setText(reg_link);
        textview3.setText(reg_start);
        textview4.setText(reg_end);
//        textview5.setText(other_details);


    }
}
