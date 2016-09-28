package com.example.jerry_san.tnp_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Float2;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jerry-san on 8/9/16.
 */
public class LocalDatabase extends SQLiteOpenHelper
{

    public static final String db_name="Tnp.db";
    public static final  String table_1 = "Company";
    public static final  String table_2 = "Message";
    public static final  String col_0 = "_id";

    public static final  String col_1 = "name";
    public static final  String col_2 = "criteria";
    public static final  String col_3 = "salary";
    public static final  String col_4 = "back";

    public static final  String col_5 = "ppt_date";
    public static final  String col_6 = "other_details";
    public static final  String col_7 = "reg_start_date";
    public static final  String col_8 = "reg_end_date";
    public static final  String col_9 = "hired_people";


    public LocalDatabase(Context context)
    {
        super(context,db_name, null , 1);

    }




    @Override
    public void onCreate(SQLiteDatabase db)
    {

        //create table for companies
        db.execSQL("create table " + table_1 + "( " + col_0
                + " integer primary key autoincrement, " + col_1
                + " text not null, " + col_2 + " real, " +col_3 + " real ," + col_4 + " text not null , " + col_5 +" text ,"  +col_6 +" text , " +col_7 + " text ,"+col_8 +"  text ,"+ col_9+" integer  );");

        //create Table for storing notifications(general messages)
        db.execSQL("create table " + table_2 + "( _id integer primary key autoincrement , Title text , Body text )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + table_1);
        onCreate(db);
        db.execSQL("drop table if exists " + table_2);
        onCreate(db);

    }


    public boolean company_insert(String name, String criteria, String salary, String back,String ppt_date_time,String other_details ,int hired)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        Float crit=Float.parseFloat(criteria);
        Float sal= Float.parseFloat(salary);

        contentValues.put(col_1, name);
        contentValues.put(col_2, crit);
        contentValues.put(col_3, sal);
        contentValues.put(col_4, back);
        contentValues.put(col_5, ppt_date_time);
        contentValues.put(col_6, other_details);
        contentValues.put(col_9, hired);

        long result=db.insert(table_1,null,contentValues);
        db.close();
        if(result==-1)
            return false;
        return true;

    }
    public boolean company_insert_json(JSONObject obj) throws JSONException {


        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();

        String name=obj.getString("name");
        Double criteria=obj.getDouble("criteria");
        Double sal= obj.getDouble("salary");
        String back=obj.getString("back");
        String ppt_date_time=obj.getString("ppt_date");
        String other_details=obj.getString("other_details");
        int hired=obj.getInt("hired_people");


        contentValues.put(col_1,name );
        contentValues.put(col_2, criteria);
        contentValues.put(col_3, sal);
        contentValues.put(col_4, back);
        contentValues.put(col_5, ppt_date_time);
        contentValues.put(col_6, other_details);
        contentValues.put(col_9, hired);

        long result=db.insert(table_1,null,contentValues);
        db.close();
        if(result==-1)
            return false;
        return true;


    }

    public boolean message_insert(String title ,String body)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();

        contentValues.put("Title", title);
        contentValues.put("Body", body);

        long result=db.insert(table_2,null,contentValues);
        db.close();
        if(result==-1)
            return false;
        return true;

    }

    public Cursor get_company_cursor() {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from " + table_1,null);
        res.moveToFirst();
        return res;

    }
    public Cursor get_message_cursor() {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from " + table_2,null);
        res.moveToFirst();
        return res;

    }


    public Cursor getCompanyDetails(int position) {

        SQLiteDatabase db=this.getReadableDatabase();
        String query="select * from " + table_1;
        Cursor cur=db.rawQuery(query,null);
        cur.moveToPosition(position);
        return cur;
    }

    public Cursor getMessageDetails(int position) {

        SQLiteDatabase db=this.getReadableDatabase();
        String query="select * from " + table_2;
        Cursor cur=db.rawQuery(query,null);
        cur.moveToPosition(position);
        return cur;
    }


    public boolean checkifexists(String name) {

        SQLiteDatabase db=this.getReadableDatabase();
        String query="select * from " + table_1 +  " where name = '" + name + "'";
        Cursor cur=db.rawQuery(query,null);
        int count=cur.getCount();
        Log.i("My_tag",String.valueOf(count));
        Log.i("My_tag",query);

        if(cur.getCount()==0)
            return false;
        return true;


    }
}
