package com.example.jerry_san.tnp_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Float2;
import android.util.Log;
/**
 * Created by jerry-san on 8/9/16.
 */
public class LocalDatabase extends SQLiteOpenHelper
{

    public static final String db_name="Tnp.db";
    public static final  String table_name = "Company";
    public static final  String col_0 = "C_id";

    public static final  String col_1 = "Name";
    public static final  String col_2 = "Criteria";
    public static final  String col_3 = "Salary";
    public static final  String col_4 = "Back";

    public static final  String col_5 = "Ppt_date_time";
    public static final  String col_6 = "Other_details";
    public static final  String col_7 = "Reg_start_date_time";
    public static final  String col_8 = "Reg_end_date_time";
    public static final  String col_9 = "Hired_people";


    public LocalDatabase(Context context)
    {
        super(context,db_name, null , 1);
        Log.i("My_tag","created");

    }

    public boolean insert(String name, String criteria, String salary, String back,String ppt_date_time,String other_details )
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
        contentValues.put(col_7, other_details);
        contentValues.put(col_8, other_details);
        contentValues.put(col_9, 0);

        long result=db.insert(table_name,null,contentValues);
        db.close();
        if(result==-1)
            return false;
        return true;

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("create table " + table_name + "( " + col_0
                + " integer primary key autoincrement, " + col_1
                + " text not null, " + col_2 + " real, " +col_3 + " real ," + col_4 + " text not null , " + col_5 +" text ,"  +col_6 +" text , " +col_7 + " text ,"+col_8 +"  text ,"+ col_9+" integer  );");

   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + table_name);
        onCreate(db);
    }

    public boolean isTableExists() {
        boolean isExist = false;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + table_name + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                isExist = true;
            }
            cursor.close();
        }
        return isExist;
    }
    public Cursor display()
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from " + table_name,null);
        int count=res.getCount();
        
        res.moveToFirst();
        String name = res.getString(1);
        Log.i("My_tag",name);
        return res;

    }
}
