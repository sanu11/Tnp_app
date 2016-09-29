package com.example.jerry_san.tnp_app.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;

public class CompanyListActivity extends AppCompatActivity {

    LocalDatabase localDatabase;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        localDatabase = new LocalDatabase(this);
        Cursor cur = localDatabase.get_company_cursor();
        ListView listView = (ListView) findViewById(R.id.listView);

        String[] columns = new String[]{"name"};

        int[] views = new int[]{R.id.company_name};

        if (cur.getCount() > 0) {
             adapter = new SimpleCursorAdapter(this, R.layout.company_list_layout, cur, columns, views);

            listView.setAdapter(adapter);
        } else {
            Toast.makeText(CompanyListActivity.this, "Sorry No data found ", Toast.LENGTH_SHORT).show();
            finish();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                ListView listView = (ListView) findViewById(R.id.listView);

                Cursor cursor = localDatabase.getCompanyDetails(position);
                String name = cursor.getString(1);
                Log.i("My_tag",name);
                Intent intent = new Intent(CompanyListActivity.this,CompanyDisplayActivity.class);
                String criteria = String.valueOf(cursor.getFloat(cursor.getColumnIndex("criteria")));
                String salary = String.valueOf(cursor.getString(cursor.getColumnIndex("salary")));
                String back = cursor.getString(cursor.getColumnIndex("back"));
                String date_time = cursor.getString(cursor.getColumnIndex("ppt_date"));
                String other_details = cursor.getString(cursor.getColumnIndex("other_details"));

                intent.putExtra("name", name);
                intent.putExtra("criteria", criteria);
                intent.putExtra("salary", salary);
                intent.putExtra("back", back);
                intent.putExtra("other_details", other_details);
                intent.putExtra("ppt_date", date_time);



                startActivity(intent);

            }
        });


    }

}