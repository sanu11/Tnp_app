package com.example.jerry_san.tnp_app.Activities.List;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;

public class ResultListActivity extends AppCompatActivity {

    LocalDatabase localDatabase;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        localDatabase = new LocalDatabase(this);
        Cursor cur = localDatabase.getResultReverseCursor();
        ListView listView = (ListView) findViewById(R.id.listView);

        String[] columns = new String[]{"Title","url"};

        int[] views = new int[]{R.id.title,R.id.body};

        if (cur.getCount() > 0) {
            adapter = new SimpleCursorAdapter(this, R.layout.result_list_layout, cur, columns, views);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(ResultListActivity.this, "Sorry No data found ", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}