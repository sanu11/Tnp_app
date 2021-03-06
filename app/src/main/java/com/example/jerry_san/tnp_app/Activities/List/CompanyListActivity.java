package com.example.jerry_san.tnp_app.Activities.List;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.jerry_san.tnp_app.Activities.Display.CompanyDisplayActivity;
import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;

public class CompanyListActivity extends AppCompatActivity {

    LocalDatabase localDatabase;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        localDatabase = new LocalDatabase(this);
        Cursor cur = localDatabase.getCompanyReverseCursor();
        ListView listView = (ListView) findViewById(R.id.listView);

        String[] columns = new String[]{"name","salary"};

        int[] views = new int[]{R.id.name,R.id.salary};

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

                Intent intent = new Intent(CompanyListActivity.this,CompanyDisplayActivity.class);
                int n = localDatabase.getCompanyCount();
                intent.putExtra("position",n-position-1);
                Log.i("My_tag"," position " + position);
                startActivity(intent);

            }

        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}