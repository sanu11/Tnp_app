package com.example.jerry_san.myfirstapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;

public class CompanyActivity extends AppCompatActivity {

    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;
    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;

    int hour,min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        setDateTimeField();

    }

    private void setDateTimeField() {
        try {

            //Reg_start_date

            Calendar start_cal = Calendar.getInstance(TimeZone.getDefault());
            startDatePickerDialog = new DatePickerDialog(this,
                    startDatePickerListener,
                    start_cal.get(Calendar.YEAR),
                    start_cal.get(Calendar.MONTH),
                    start_cal.get(Calendar.DAY_OF_MONTH));

            startDatePickerDialog.setCancelable(false);
            startDatePickerDialog.setTitle("Select the date");

            //Reg_end_date
            Calendar end_cal = Calendar.getInstance(TimeZone.getDefault());
            endDatePickerDialog = new DatePickerDialog(this,
                    endDatePickerListener,
                    end_cal.get(Calendar.YEAR),
                    end_cal.get(Calendar.MONTH),
                    end_cal.get(Calendar.DAY_OF_MONTH));

            endDatePickerDialog.setCancelable(false);
            endDatePickerDialog.setTitle("Select the date");

            Calendar st_time_cal = Calendar.getInstance(TimeZone.getDefault());

            startTimePickerDialog=new TimePickerDialog(this,startTimePickerListener,st_time_cal.get(Calendar.HOUR_OF_DAY),st_time_cal.get(Calendar.MINUTE),false);

            Calendar end_time_cal = Calendar.getInstance(TimeZone.getDefault());

            endTimePickerDialog=new TimePickerDialog(this,endTimePickerListener,end_time_cal.get(Calendar.HOUR_OF_DAY),end_time_cal.get(Calendar.MINUTE),false);

        } catch (Exception e) {
        }
    }

    //listener for start date set
    private DatePickerDialog.OnDateSetListener startDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            TextView tvDt = (TextView) findViewById(R.id.start_date);
            tvDt.setText(day1 + "/" + month1 + "/" + year1);
        }
    };

    //listener for reg end date set
    private DatePickerDialog.OnDateSetListener endDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            TextView tv = (TextView) findViewById(R.id.end_date);
            tv.setText(day1 + "/" + month1 + "/" + year1);
        }
    };

    //listener for reg start time set
    private  TimePickerDialog.OnTimeSetListener startTimePickerListener =new TimePickerDialog.OnTimeSetListener(){
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            TextView st_tm=(TextView)findViewById(R.id.start_time);
            st_tm.setText(new StringBuilder()
                    .append(pad(hour)).append(":")
                    .append(pad(min)));
        }
    };

    //listener for reg end time set
    private  TimePickerDialog.OnTimeSetListener endTimePickerListener =new TimePickerDialog.OnTimeSetListener(){
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            TextView end_tm=(TextView)findViewById(R.id.end_time);
            end_tm.setText(new StringBuilder()
                    .append(pad(hour)).append(":")
                    .append(pad(min)));
        }
    };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


    public void onClickStartDate(View view) {

            startDatePickerDialog.show();
        }
    public void onClickEndDate(View view) {

        endDatePickerDialog.show();
    }
    public void onClickStartTime(View v )
    {
        startTimePickerDialog.show();

    }
    public  void onClickEndTime(View v)
    {
        endTimePickerDialog.show();

    }




    public void Reg_company (View v)
    {

        EditText e1=(EditText)findViewById(R.id.name);
        EditText e2=(EditText)findViewById(R.id.criteria);
        EditText e3=(EditText)findViewById(R.id.salary);

        String name   = e1.getText().toString();
        float critera = Float.parseFloat(e2.getText().toString());
        float salary  = Float.parseFloat(e3.getText().toString());

    }

}
