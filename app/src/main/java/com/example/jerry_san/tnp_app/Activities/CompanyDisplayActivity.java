package com.example.jerry_san.tnp_app.Activities;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;

import org.json.JSONObject;

public class CompanyDisplayActivity extends AppCompatActivity {

    LocalDatabase localDatabase;
    boolean flag = false;
    boolean length = false;

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

        Cursor cursor = localDatabase.getCompanyDetails(position);


        TextView textViews[] = {
                (TextView) findViewById(R.id.name),  //dummy
                (TextView) findViewById(R.id.name),
                (TextView) findViewById(R.id.criteria),
                (TextView) findViewById(R.id.salary),
                (TextView) findViewById(R.id.back),
                (TextView) findViewById(R.id.ppt),
                (TextView) findViewById(R.id.other_details),
                (TextView) findViewById(R.id.reg_link),
                (TextView) findViewById(R.id.reg_start),
                (TextView) findViewById(R.id.reg_end),
                (TextView) findViewById(R.id.hired)

        };

        int count = 6;

        String other_det_col = "other_details";
        int i = 1;

        int temp1 = cursor.getType(6);

        for (i = 1; i <= count; i++) {

            int type = cursor.getType(i);
            String cur_col = cursor.getColumnName(i);


            if (type == Cursor.FIELD_TYPE_INTEGER) {

                Integer integer = cursor.getInt(i);
                if (integer != null)
                    textViews[i].setText("" + integer);

            } else if (type == Cursor.FIELD_TYPE_STRING) {

                String temp = cursor.getString(i);
                textViews[i].setText(temp);

            } else if (type == Cursor.FIELD_TYPE_FLOAT) {

                Float var = cursor.getFloat(i);
                if (var != null) {
                    if (cur_col.equals("criteria") && var == 0)
                        textViews[i].setText("No criteria");
                    else
                        textViews[i].setText("" + var);
                }
            } else if (type == Cursor.FIELD_TYPE_NULL) {
                if (cur_col.equals(other_det_col))
                    findViewById(R.id.other_det_layout).setVisibility(View.GONE);

            }

        }

        int hired = cursor.getInt(cursor.getColumnIndex("hired_people"));

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.hired_layout);
        if (hired == 0) {
            layout3.setVisibility(View.GONE);

        } else {
            layout3.setVisibility(View.VISIBLE);
            textViews[10].setText("" + hired);
        }

        //if company isn't  updated  ( registration info isn't present) hide registration info
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.update_company_layout);
        String reg_link = cursor.getString(cursor.getColumnIndex("reg_link"));

        if (reg_link == null) {
            layout1.setVisibility(View.GONE);
            return;
        }

        layout1.setVisibility(View.VISIBLE);
        textViews[i].setText(reg_link);
        i++;
        String reg_start = cursor.getString(cursor.getColumnIndex("reg_start"));
        textViews[i].setText(reg_start);
        i++;
        String reg_end = cursor.getString(cursor.getColumnIndex("reg_end"));
        textViews[i].setText(reg_end);

    }
}
