package com.example.jerry_san.tnp_app.Activities.Upload;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jerry_san.tnp_app.R;
import com.example.jerry_san.tnp_app.RESTCalls.UpdateCompany;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class UpdateCompanyActivity extends AppCompatActivity {


    int hour,min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public void onClickRegDate(View view)
    {
        final View vv = view;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker v, int selectedYear, int selectedMonth, int selectedDay) {
                String year1 = String.valueOf(selectedYear);
                String month1 = String.valueOf(selectedMonth + 1);
                String day1 = String.valueOf(selectedDay);

                TextView tv;

                if(vv.getId()==R.id.reg_start_date) {
                    tv = (TextView) findViewById(R.id.reg_start_date);
                    assert tv != null;
                    tv.setText(year1 +"-" + month1 + "-" + day1);

                }else if(vv.getId()==R.id.reg_end_date) {
                    tv = (TextView) findViewById(R.id.reg_end_date);
                    assert tv != null;
                    tv.setText(year1 +"-" + month1 + "-" + day1);

                }

            }
        },2016,9,10);

        datePickerDialog.show();
    }

    public void onClickRegTime(View view)
    {
        final View vv = view;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            TextView tv;

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                hour = hourOfDay;
                min = minute;

                if (vv.getId() == R.id.reg_start_time) {
                    tv = (TextView) findViewById(R.id.reg_start_time);
                    tv.setText(new StringBuilder()
                            .append(pad(hour)).append(":")
                            .append(pad(min)));
                }else if (vv.getId() == R.id.reg_end_time) {
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
        EditText e1 = (EditText) findViewById(R.id.name);
        EditText e2 = (EditText) findViewById(R.id.reg_link);
        EditText e3 = (EditText) findViewById(R.id.other_details);
        TextView e4 = (TextView) findViewById(R.id.reg_start_date);
        TextView e5 = (TextView) findViewById(R.id.reg_start_time);
        TextView e6 = (TextView) findViewById(R.id.reg_end_date);
        TextView e7 = (TextView) findViewById(R.id.reg_end_time);

        String name = e1.getText().toString();
        String reg_link = e2.getText().toString();
        String other_details = e3.getText().toString();
        String reg_start_date = e4.getText().toString();
        String reg_start_time = e5.getText().toString();
        String reg_end_date = e6.getText().toString();
        String reg_end_time = e7.getText().toString();

        String reg_start = reg_start_date + " " + reg_start_time;
        String reg_end = reg_end_date + " " + reg_end_time;

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

        Toast.makeText(UpdateCompanyActivity.this, "Please Wait ", Toast.LENGTH_SHORT).show();

        UpdateCompany data = new UpdateCompany();

        String res = data.execute(obj.toString()).get();
        Log.i("My_tag", "Response  " + res);

        if (res == null) {
            Toast.makeText(UpdateCompanyActivity.this, "Update Unsuccessful", Toast.LENGTH_SHORT).show();
            Log.i("My_tag", "Update Unsuccessful");
        } else {
            Toast.makeText(UpdateCompanyActivity.this, "Updated Successfullly", Toast.LENGTH_SHORT).show();
            Log.i("My_tag", "Updated Successfully");

        }
        finish();


    }
}
