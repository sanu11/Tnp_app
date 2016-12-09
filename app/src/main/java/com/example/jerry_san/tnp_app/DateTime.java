package com.example.jerry_san.tnp_app;

import android.util.Log;

import java.text.DateFormatSymbols;

/**
 * Created by Sanika on 08-Dec-16.
 */

public class DateTime {

    public String convert(String olddate) {

        String newdate = "";
        String arr[] = olddate.split("\\s+");

        String date[] = arr[0].split("-");
        int month = Integer.parseInt(date[1]);
        String monthstr = new DateFormatSymbols().getMonths()[month - 1];
        if (arr.length == 2) {
            newdate = date[2] + " " + monthstr + " , " + date[0] + "\n        " + arr[1];
        }
        else if(arr.length==1){
            newdate = date[2] + " " + monthstr + " , " + date[0];
        }
        else
            newdate=olddate;

        Log.i("My_tag  ", monthstr);
        return newdate;
    }

    public String getTime(String time) {

        return "";
    }
}
