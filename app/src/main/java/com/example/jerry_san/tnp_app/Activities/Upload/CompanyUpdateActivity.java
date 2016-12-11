package com.example.jerry_san.tnp_app.Activities.Upload;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.NetworkConnection;
import com.example.jerry_san.tnp_app.R;
import com.example.jerry_san.tnp_app.RESTCalls.UpdateCompany;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CompanyUpdateActivity extends AppCompatActivity {


    int hour, min;
    LocalDatabase localDatabase;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //populate spinner with names of companies from database
        final Spinner spinner = (Spinner) findViewById(R.id.name_spinner);
        localDatabase = new LocalDatabase(this);
        Cursor cur = localDatabase.getCompanyReverseCursor();
        String[] columns = new String[]{"name"};

        int[] views = new int[]{R.id.name};

        if (cur.getCount() > 0) {
            adapter = new SimpleCursorAdapter(this, R.layout.item_layout, cur, columns, views);

            spinner.setAdapter(adapter);
        } else {
            Toast.makeText(CompanyUpdateActivity.this, "Sorry No Companies found to update", Toast.LENGTH_SHORT).show();
            finish();
        }

        NetworkConnection connection = new NetworkConnection(this.getApplicationContext());
        boolean con = connection.checkNetwork();
        //Log.i("My_tag", "connection " + con);
        if (!con) {
            Toast.makeText(CompanyUpdateActivity.this, "Check your Network", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public void onClickRegDate(View view) {
        final View vv = view;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker v, int selectedYear, int selectedMonth, int selectedDay) {
                String year1 = String.valueOf(selectedYear);
                String month1 = String.valueOf(selectedMonth + 1);
                String day1 = String.valueOf(selectedDay);

                TextView tv;

                if (vv.getId() == R.id.reg_start_date) {
                    tv = (TextView) findViewById(R.id.reg_start_date);
                    assert tv != null;
                    tv.setText(year1 + "-" + month1 + "-" + day1);

                } else if (vv.getId() == R.id.reg_end_date) {
                    tv = (TextView) findViewById(R.id.reg_end_date);
                    assert tv != null;
                    tv.setText(year1 + "-" + month1 + "-" + day1);

                }

            }
        }, 2016, 11, 15);

        datePickerDialog.show();
    }

    public void onClickRegTime(View view) {
        final View vv = view;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            TextView tv;

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                hour = hourOfDay;
                min = minute;

                if (vv.getId() == R.id.reg_start_time) {
                    tv = (TextView) findViewById(R.id.reg_start_time);
                    tv.setText(new StringBuilder()
                            .append(pad(hour)).append(":")
                            .append(pad(min)));
                } else if (vv.getId() == R.id.reg_end_time) {
                    tv = (TextView) findViewById(R.id.reg_end_time);
                    tv.setText(new StringBuilder()
                            .append(pad(hour)).append(":")
                            .append(pad(min)));
                }

            }
        }, 10, 00, false);

        timePickerDialog.show();
    }


    public void onClickUpdate(View v) throws ExecutionException, InterruptedException {

        NetworkConnection connection = new NetworkConnection(this.getApplicationContext());
        boolean con = connection.checkNetwork();
        //Log.i("My_tag", "connection " + con);
        if (!con) {
            Toast.makeText(CompanyUpdateActivity.this, "Check your Network", Toast.LENGTH_SHORT).show();
            return;
        }
        final Spinner e1 = (Spinner) findViewById(R.id.name_spinner);
        EditText e2 = (EditText) findViewById(R.id.reg_link);
        TextView e3 = (TextView) findViewById(R.id.reg_start_date);
        TextView e4 = (TextView) findViewById(R.id.reg_start_time);
        TextView e5 = (TextView) findViewById(R.id.reg_end_date);
        TextView e6 = (TextView) findViewById(R.id.reg_end_time);
        EditText e7 = (EditText) findViewById(R.id.other_details);

        Cursor cursor = (Cursor) e1.getSelectedItem();
        int count = cursor.getColumnCount();
        String name = cursor.getString(1);

        String reg_start_date = e3.getText().toString();
        String reg_start_time = e4.getText().toString();
        String reg_end_date = e5.getText().toString();
        String reg_end_time = e6.getText().toString();
        Object reg_start ;
        Object reg_end ;
        Object reg_link = e2.getText().toString();
        Object other_details = e7.getText().toString();



        if (name.trim().length() == 0) {
            Toast.makeText(CompanyUpdateActivity.this, "Enter name of company ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (reg_link.toString().trim().length() == 0) {
            Toast.makeText(CompanyUpdateActivity.this, "Enter Registration Link ", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!reg_start_date.equals("Select Date")) {
            if (!reg_start_time.equals("Select Time"))
                reg_start = reg_start_date + " " + reg_start_time;
            else
                reg_start = reg_start_date;
        }
        else
            reg_start = JSONObject.NULL;

        if(!reg_end_date.equals("Select Date")){
            if(!reg_end_time.equals("Select Time"))
                reg_end = reg_end_date +  " " + reg_end_time;
            else
                reg_end = reg_end_date;
        }
        else
            reg_end = JSONObject.NULL;


        if (other_details.toString().trim().length() == 0)
            other_details = JSONObject.NULL;

        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("other_details", other_details);
            obj.put("reg_link", reg_link);
            obj.put("reg_start", reg_start);
            obj.put("reg_end", reg_end);

            Log.i("My_tag", obj.toString(0));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Toast.makeText(CompanyUpdateActivity.this, "Please Wait ", Toast.LENGTH_SHORT).show();

        UpdateCompany data = new UpdateCompany();

        String res = data.execute(obj.toString()).get();
        Log.i("My_tag", "Update Company Response  " + res);

        if (res == null) {
            Toast.makeText(CompanyUpdateActivity.this, "Update Unsuccessful", Toast.LENGTH_SHORT).show();
            Log.i("My_tag", "Update Unsuccessful");
        } else
            Toast.makeText(CompanyUpdateActivity.this, res, Toast.LENGTH_SHORT).show();

        finish();
    }
}
