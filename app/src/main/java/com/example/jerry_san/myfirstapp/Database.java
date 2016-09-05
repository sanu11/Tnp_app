package com.example.jerry_san.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by jerry-san on 8/9/16.
 */
public class Database extends SQLiteOpenHelper
{

    public static final String db_name="Tnp2.db";
    public static final  String table_name = "Register";
    public static final  String col_1 = "Name";
    public static final  String col_2 = "Email";
    public static final  String col_3 = "Phone";
    public static final  String col_4 = "Address";


    public Database(Context context)
    {
        super(context,db_name, null , 1);
        Log.i("DATABASE","created");

    }

    public boolean insert(String name, String email, String number, String address)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(col_1, name);
        contentValues.put(col_2, email);
        contentValues.put(col_3, number);
        contentValues.put(col_4, address);
        long result=db.insert(table_name,null,contentValues);
        if(result==-1)
            return false;
        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("create table " + table_name +  "(name text, email text  , phone text ,address text );" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + table_name);
        onCreate(db);
    }
    public Cursor display()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + table_name,null);
        return  res;

    }
}
