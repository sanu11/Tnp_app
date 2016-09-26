package com.example.jerry_san.tnp_app;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CompanyListActivity extends AppCompatActivity {

    LocalDatabase localDatabase;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        localDatabase = new LocalDatabase(this);
        Cursor cur = localDatabase.get_cursor();
        ListView listView = (ListView) findViewById(R.id.listView);

        String[] columns = new String[]{"Name"};

        int[] views = new int[]{R.id.company_name};

        if (cur.getCount() > 0) {
             adapter = new SimpleCursorAdapter(this, R.layout.list_view_layout, cur, columns, views);

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

                Cursor cursor = localDatabase.getDetails(position);
                String name = cursor.getString(1);
                Log.i("My_tag",name);
                Intent intent = new Intent(CompanyListActivity.this,CompanyDisplayActivity.class);
                String criteria = String.valueOf(cursor.getFloat(cursor.getColumnIndex("Criteria")));
                String salary = String.valueOf(cursor.getString(cursor.getColumnIndex("Salary")));
                String back = cursor.getString(cursor.getColumnIndex("Back"));
                String date_time = cursor.getString(cursor.getColumnIndex("Ppt_date_time"));
                String other_details = cursor.getString(cursor.getColumnIndex("Other_details"));

                intent.putExtra("name", name);
                intent.putExtra("criteria", criteria);
                intent.putExtra("salary", salary);
                intent.putExtra("back", back);
                intent.putExtra("other_details", other_details);
                intent.putExtra("date_time", date_time);
                startActivity(intent);

            }
        });


    }

}
