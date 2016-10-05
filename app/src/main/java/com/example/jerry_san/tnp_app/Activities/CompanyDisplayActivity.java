package com.example.jerry_san.tnp_app.Activities;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;

public class CompanyDisplayActivity extends AppCompatActivity {

    LocalDatabase localDatabase;
    boolean flag=false;
    boolean length=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_company_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        localDatabase= new LocalDatabase(this);
        int position = getIntent().getExtras().getInt("position");
        Log.i("My_tag",String.valueOf(position));

        if(position==-1)
            Toast.makeText(CompanyDisplayActivity.this, "Error ", Toast.LENGTH_SHORT).show();

        else {
            Cursor cursor = localDatabase.getCompanyDetails(position);

            String name_str = String.valueOf(cursor.getString(cursor.getColumnIndex("name")));
            String criteria_str = String.valueOf(cursor.getFloat(cursor.getColumnIndex("criteria")));
            String salary_str = String.valueOf(cursor.getFloat(cursor.getColumnIndex("salary")));
            String back_str = cursor.getString(cursor.getColumnIndex("back"));
            String date_time_str = cursor.getString(cursor.getColumnIndex("ppt_date"));
            String other_det_str = cursor.getString(cursor.getColumnIndex("other_details"));
            String reg_link_str = cursor.getString(cursor.getColumnIndex("reg_link"));
            String reg_start_str = cursor.getString(cursor.getColumnIndex("reg_start"));
            String reg_end_str = cursor.getString(cursor.getColumnIndex("reg_end"));
            String hired_str = String.valueOf(cursor.getInt(cursor.getColumnIndex("hired_people")));

            Log.i("My_tag","compnay " + name_str);
            Log.i("My_tag","other " + other_det_str);

            assert date_time_str != null;
            Log.i("My_tag", date_time_str);
            String array[] = date_time_str.split("\\s+");
            String ppt_date_str = array[0];
            String ppt_time_str = array[1];





            TextView textview = (TextView) findViewById(R.id.name);
            TextView textview2 = (TextView) findViewById(R.id.criteria);
            TextView textview3 = (TextView) findViewById(R.id.salary);
            TextView textview4 = (TextView) findViewById(R.id.back);
            TextView textview5 = (TextView) findViewById(R.id.ppt_date);
            TextView textview6 = (TextView) findViewById(R.id.ppt_time);
            TextView textview7 = (TextView) findViewById(R.id.other_details);
            TextView textview8 = (TextView) findViewById(R.id.reg_link);
            TextView textview9 = (TextView) findViewById(R.id.reg_start_date);
            TextView textview10 = (TextView) findViewById(R.id.reg_start_time);
            TextView textview11 = (TextView) findViewById(R.id.reg_end_date);
            TextView textview12 = (TextView) findViewById(R.id.reg_end_time);
            TextView textview13 = (TextView) findViewById(R.id.hired);


            CharSequence name = name_str.subSequence(0, name_str.length());
            CharSequence criteria = null;
            if (flag == false)
                criteria = criteria_str.subSequence(0, criteria_str.length());
            CharSequence salary = salary_str.subSequence(0, salary_str.length());
            CharSequence back = back_str.subSequence(0, back_str.length());
            CharSequence date = ppt_date_str.subSequence(0, ppt_date_str.length());
            CharSequence time = ppt_time_str.subSequence(0, ppt_time_str.length());



        }

    }
}
