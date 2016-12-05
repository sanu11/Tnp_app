package com.example.jerry_san.tnp_app.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Float2;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.example.jerry_san.tnp_app.R.id.name;

/**
 * Created by jerry-san on 8/9/16.
 */
public class LocalDatabase extends SQLiteOpenHelper {

    public static final String col[] = {
            "id",
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
    public static final String col_0 = "_id";

    public static final String col_1 = "name";
    public static final String col_2 = "criteria";
    public static final String col_3 = "salary";
    public static final String col_4 = "back";

    public static final String col_5 = "ppt_date";
    public static final String col_6 = "other_details";
    public static final String col_7 = "reg_link";
    public static final String col_8 = "reg_start";
    public static final String col_9 = "reg_end";
    public static final String col_10 = "hired_people";


    public LocalDatabase(Context context) {
        super(context, db_name, null, 1);

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
        db.execSQL("create table " + table_2 + "( _id integer primary key autoincrement , Title text , Body text )");

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
            contentValues.put(col_1, name);
        }

        if (!obj.isNull("criteria")) {
            String criteria = obj.getString("criteria");
            contentValues.put(col_2, criteria);
        }

        if (!obj.isNull("salary")) {
            String salary = obj.getString("salary");
            contentValues.put(col_3, salary);
        }

        if (!obj.isNull("back")) {
            String back = obj.getString("back");
            contentValues.put(col_4, back);
        }

        if (!obj.isNull("ppt_date")) {
            String ppt_date_time = obj.getString("ppt_date");
            contentValues.put(col_5, ppt_date_time);
        }

        if (!obj.isNull("other_details")) {
            String other_details = obj.getString("other_details");
            contentValues.put(col_6, other_details);
        }

        if (!obj.isNull("reg_link")) {
            String reg_link = obj.getString("reg_link");
            contentValues.put(col_7, reg_link);
        }

        if (!obj.isNull("reg_start_date")) {

            String reg_start = obj.getString("reg_start_date");
            contentValues.put(col_8, reg_start);
        }

        if (!obj.isNull("reg_end_date")) {
            String reg_end = obj.getString("reg_end_date");
            contentValues.put(col_9, reg_end);
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

    public Cursor getCompanyCursor() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + table_1, null);
        res.moveToFirst();
        return res;

    }

    public Cursor getMessageCursor() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + table_2, null);
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
        Log.i("My_tag", String.valueOf(count));
        Log.i("My_tag", query);

        if (cur.getCount() == 0)
            return false;
        return true;

    }

    public long companyUpdate(JSONObject object) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();


        String query = "select * from " + table_1 + " where name = '" + name + "'";
        Cursor cur = db.rawQuery(query, null);
        cur.moveToFirst();
        String other = cur.getString(cur.getColumnIndex("other_details"));
        String other_details = object.getString("other_details");
        if (other != null) {
            if (other_details != null)
                other = other + " " + other_details;
        } else
            other = other_details;
        object.put("other_details", other);

        int i = 6;
        String key;
        Iterator<String> stringIterator = object.keys();
        while (i < 10) {
            key = stringIterator.next();
            cv.put(col[i], object.getString(key));
        }

        long res = db.update(table_1, cv, "name= '" + name + "'", null);
        Log.i("My_tag", String.valueOf(res));
        db.close();
        return res;

    }
}

