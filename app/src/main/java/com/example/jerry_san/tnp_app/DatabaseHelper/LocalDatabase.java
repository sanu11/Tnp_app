package com.example.jerry_san.tnp_app.DatabaseHelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.example.jerry_san.tnp_app.Receiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by jerry-san on 8/9/16.
 */

public class LocalDatabase extends SQLiteOpenHelper {

    Context context;
    public static final String col[] = {
            "_id",
            "name",
            "criteria",
            "salary",
            "back",
            "ppt_date",
            "other_details",
            "reg_link",
            "reg_start",
            "reg_end",
            "hired_people"
    };

    public static final String db_name = "Tnp.db";
    public static final String table_1 = "Company";
    public static final String table_2 = "Message";
    public static final String table_3 = "Result";


    public LocalDatabase(Context context) {
        super(context, db_name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table for companies
        db.execSQL("create table " + table_1 + "( " + col[0]
                + " integer primary key autoincrement, " + col[1]
                + " text not null, " + col[2] + " real, " + col[3] + " real ," + col[4] + " text , " + col[5]
                + " text ," + col[6] + " text , " + col[7] + " text ,"
                + col[8] + "  text ," + col[9] + " text ," + col[10] + " integer  );");

        //create Table for storing notifications(general messages)
        db.execSQL("create table " + table_2 + "( _id integer primary key autoincrement , Title text , Body text , url integer )");

        //create Table for storing Results
        db.execSQL("create table " + table_3 + "( _id integer primary key autoincrement , Title text , url text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table_1);
        onCreate(db);
        db.execSQL("drop table if exists " + table_2);
        onCreate(db);

    }


    public long companyInsertJson(JSONObject obj) throws JSONException {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (!obj.isNull("name")) {
            String name = obj.getString("name");
            contentValues.put(col[1], name);
        }


        if (!obj.isNull("criteria")) {
            Log.i("My_tag", "crit " + obj.getDouble("criteria"));
            Double criteria = obj.getDouble("criteria");
            contentValues.put(col[2], criteria);
        }

        if (!obj.isNull("salary")) {
            Double salary = obj.getDouble("salary");
            contentValues.put(col[3], salary);
        }

        if (!obj.isNull("back")) {
            String back = obj.getString("back");
            contentValues.put(col[4], back);
        }

        if (!obj.isNull("ppt_date")) {
            String ppt_date_time = obj.getString("ppt_date");
            contentValues.put(col[5], ppt_date_time);
        }

        if (!obj.isNull("other_details")) {
            String other_details = obj.getString("other_details");
            Log.i("My_tag",other_details+ " other ");
            contentValues.put(col[6], other_details);
        }

        if (!obj.isNull("reg_link")) {
            String reg_link = obj.getString("reg_link");
            contentValues.put(col[7], reg_link);
        }

        if (!obj.isNull("reg_start_date")) {

            String reg_start = obj.getString("reg_start_date");
            contentValues.put(col[8], reg_start);
        }

        if (!obj.isNull("reg_end_date")) {
            String reg_end = obj.getString("reg_end_date");
            contentValues.put(col[9], reg_end);
        }

        long result = db.insert(table_1, null, contentValues);
        db.close();

        return result;

    }

    public boolean messageInsert(String title, String body) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Title", title);
        contentValues.put("Body", body);

        long result = db.insert(table_2, null, contentValues);
        db.close();
        if (result == -1)
            return false;
        return true;

    }
    public boolean resultInsert(String title, String url) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Title", title);
        contentValues.put("url", url);

        long result = db.insert(table_3, null, contentValues);
        db.close();
        if (result == -1)
            return false;
        return true;

    }


    public Cursor getCompanyReverseCursor() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + table_1 + " order by _id desc ", null);
        res.moveToFirst();
        return res;

    }

    public Cursor getMessageReverseCursor() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + table_2 + " order by _id desc ", null);
        res.moveToFirst();
        return res;

    }
    public Cursor getResultReverseCursor() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + table_3 + " order by _id desc ", null);
        res.moveToFirst();
        return res;
    }

    public Cursor getCompanyDetails(int position) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + table_1;
        Cursor cur = db.rawQuery(query, null);
        cur.moveToPosition(position);
        return cur;
    }

    public Cursor getMessageDetails(int position) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + table_2;
        Cursor cur = db.rawQuery(query, null);
        cur.moveToPosition(position);
        return cur;
    }


    public boolean checkCompanyifExists(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + table_1 + " where name = '" + name + "'";
        Cursor cur = db.rawQuery(query, null);
        int count = cur.getCount();

        if (cur.getCount() == 0)
            return false;
        return true;

    }
    public  int getCompanyPosition(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select _id from " + table_1 + " where name = '" + name + "'";
        Cursor cur = db.rawQuery(query,null);
        cur.moveToFirst();
        return cur.getInt(0);
    }

    public int getCompanyCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + table_1;
        Cursor cur = db.rawQuery(query,null);
        cur.moveToFirst();
        return cur.getCount();
    }
    public int getMessageCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + table_2;
        Cursor cur = db.rawQuery(query,null);
        cur.moveToFirst();
        return cur.getCount();

    }

    public long companyUpdate(JSONObject object) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        String name = object.getString("name");

        String query = "select * from " + table_1 + " where name = '" + name + "'";
        Cursor cur = db.rawQuery(query, null);
        cur.moveToFirst();
        String reg_start = null;
        String reg_end = null;
        String reg_link = null;
        if (!object.isNull("reg_link")) {
            reg_link = object.getString("reg_link");
            cv.put("reg_link", reg_link);
        }
        if (!object.isNull("reg_start")) {
            reg_start = object.getString("reg_start");
            cv.put("reg_start", reg_start);
        }
        if (!object.isNull("reg_end")) {
            reg_end = object.getString("reg_end");
            cv.put("reg_end", reg_end);
        }
        if (!object.isNull("other_details")) {
            String other_details = object.getString("other_details");
            String other = cur.getString(cur.getColumnIndex("other_details"));
            if (other != null)
                other = other + " " + other_details;
            else
                other = other_details;
            cv.put("other_details", other);
        }


        long res = db.update(table_1, cv, "name= '" + name + "'", null);

        db.close();
        cur.close();
        if(reg_end!=null)
            setAlarm(name,reg_link,reg_end);
        return res;

    }

    public void setAlarm(String name ,String reg_link,String reg_end) {

        //reg_end yy-mm-dd
        String datetime[] = reg_end.split("\\s+");
        String date = datetime[0];
        String time =null ;
        if(datetime.length==1){
            time = "23:30:00";
        }
        else if(datetime.length==2){
            time = datetime[1];
        }
        else
            return;

        String arr[] = datetime[0].split("-");
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);

        String arr2[] = time.split(":");
        int hour = Integer.valueOf(arr2[0]);
        int min = Integer.valueOf(arr2[1]);
        int sec;
        if(arr2.length==3)
            sec=Integer.parseInt(arr2[2]);
        else
            sec=0;

        PendingIntent pendingIntent;

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, day);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);

        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.set(Calendar.HOUR_OF_DAY, hr-1);

        Intent myIntent = new Intent(context, Receiver.class);
        myIntent.putExtra("name",name);
        myIntent.putExtra("reg_link",reg_link);
        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

        String temp = calendar.getTime().toString();
        Log.i("My_tag", "Alarm set at " + temp);
    } //end onCreate


}