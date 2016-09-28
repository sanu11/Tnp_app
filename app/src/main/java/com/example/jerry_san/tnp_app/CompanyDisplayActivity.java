package com.example.jerry_san.tnp_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static java.security.AccessController.getContext;

public class CompanyDisplayActivity extends AppCompatActivity {

    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_company_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String name_str = getIntent().getExtras().getString("name");

        float criteria_float = Float.parseFloat(getIntent().getExtras().getString("criteria"));
        String criteria_str=String.valueOf(criteria_float);

        if(criteria_str.equals("0.0"))
            flag=true;
        float salary_float = Float.parseFloat(getIntent().getExtras().getString("salary"));
        String salary_str=String.valueOf(salary_float);

        String back_str = getIntent().getExtras().getString("back");
        String other_det_str = getIntent().getExtras().getString("other_details");
        String date_time_str = getIntent().getExtras().getString("ppt_date");

        assert date_time_str != null;

        Log.i("My_tag",date_time_str);
        String array[] = date_time_str.split("\\s+");
        String date_str=array[0];
        String time_str=array[1];


        Log.i("My_tag",criteria_str);

        TextView textview =(TextView) findViewById (R.id.name);
        TextView textview2 =(TextView) findViewById (R.id.criteria);
        TextView textview3 =(TextView) findViewById (R.id.salary);
        TextView textview4 =(TextView) findViewById (R.id.back);
        TextView textview5 =(TextView) findViewById (R.id.date);
        TextView textview6 =(TextView) findViewById (R.id.time);

        CharSequence name = name_str.subSequence(0, name_str.length());
        CharSequence criteria = null;
        if(flag==false)
             criteria = criteria_str.subSequence(0, criteria_str.length());
        CharSequence salary = salary_str.subSequence(0, salary_str.length());
        CharSequence back = back_str.subSequence(0,back_str.length());
        CharSequence date = date_str.subSequence(0, date_str.length());
        CharSequence time= time_str.subSequence(0, time_str.length());

        assert textview != null;
        textview.setText(name);


        assert textview2 != null;
        if(flag)
            textview2.setText("No Criteria");
        else
            textview2.setText(criteria);

        assert textview3 != null;
        textview3.setText(salary);

        assert textview4 != null;
        textview4.setText(back);

        assert textview5 != null;
        textview5.setText(date);

        assert textview6 != null;
        textview6.setText(time);

    }
}
