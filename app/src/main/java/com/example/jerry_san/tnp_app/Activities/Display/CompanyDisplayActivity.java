package com.example.jerry_san.tnp_app.Activities.Display;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.URLUtil.*;

import com.example.jerry_san.tnp_app.Activities.Upload.CompanyRegActivity;
import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.DateTime;
import com.example.jerry_san.tnp_app.R;

public class CompanyDisplayActivity extends AppCompatActivity {

    LocalDatabase localDatabase;
    boolean flag = false;
    boolean length = false;
    DateTime dateTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_company_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        localDatabase = new LocalDatabase(this);
        int position = getIntent().getExtras().getInt("position");
        Log.i("My_tag", String.valueOf(position));

        if (position == -1) {
            Toast.makeText(CompanyDisplayActivity.this, "Error ", Toast.LENGTH_SHORT).show();
            return;
        }
        dateTime = new DateTime();


        TextView textViews[] = {
                (TextView) findViewById(R.id.name),  //dummy
                (TextView) findViewById(R.id.name),
                (TextView) findViewById(R.id.criteria),
                (TextView) findViewById(R.id.salary),
                (TextView) findViewById(R.id.back),
                (TextView) findViewById(R.id.ppt_date),
                (TextView) findViewById(R.id.other_details),
                (TextView) findViewById(R.id.reg_link),
                (TextView) findViewById(R.id.reg_start),
                (TextView) findViewById(R.id.reg_end),
                (TextView) findViewById(R.id.hired)

        };

        Cursor cursor = localDatabase.getCompanyDetails(position);

        String name = cursor.getString(1);
        textViews[1].setText(name);

        Log.i("My_tag", String.valueOf(cursor.getFloat(2)));
        Float criteria = cursor.getFloat(2);
        if (criteria == null || criteria == 0)
            textViews[2].setText("No Criteria");
        else
            textViews[2].setText(String.valueOf(criteria));

        Float salary = cursor.getFloat(3);
        if (salary != null && salary != 0)
            textViews[3].setText(String.valueOf(salary));

        String back = cursor.getString(4);
        if (back != null)
            textViews[4].setText(back);

        String ppt = cursor.getString(5);
        if (ppt != null) {
            String ppt_new = dateTime.convert(ppt);
            ((TextView) findViewById(R.id.ppt_date)).setText(ppt_new);
        }

        String other = cursor.getString(6);
        if (other != null)
            textViews[6].setText(other);
        else
            findViewById(R.id.other_det_layout).setVisibility(View.GONE);

        int hired = cursor.getInt(10);

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.hired_layout);
        if (hired == 0) {
            layout3.setVisibility(View.GONE);
        } else {
            layout3.setVisibility(View.VISIBLE);
            textViews[10].setText("" + hired);
        }

        //if company isn't  updated  ( registration info isn't present) hide registration info
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.update_company_layout);
        String reg_link = cursor.getString(7);

        if (reg_link == null) {
            layout1.setVisibility(View.GONE);
            return;
        }

        layout1.setVisibility(View.VISIBLE);
        textViews[7].setText(reg_link);

        String reg_start = cursor.getString(8);
        if (reg_start != null) {
            String reg_start_new = dateTime.convert(reg_start);
            textViews[8].setText(reg_start_new);
        }
        String reg_end = cursor.getString(9);
        if (reg_end != null) {
            String reg_end_new = dateTime.convert(reg_end);
            textViews[9].setText(reg_end_new);
        }
    }

    public void onClickURL(View view) {

        TextView textView= (TextView) findViewById(R.id.reg_link);
        String url = textView.getText().toString();
        Log.i("My_tag",url);
        if(URLUtil.isValidUrl(url))
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        else
            Toast.makeText(CompanyDisplayActivity.this, "Invalid URL", Toast.LENGTH_SHORT).show();

    }

}