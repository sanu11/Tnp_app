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

import com.example.jerry_san.tnp_app.Activities.Display.MessageDisplayActivity;
import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;

public class MessageListActivity extends AppCompatActivity {

    LocalDatabase localDatabase;
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        localDatabase = new LocalDatabase(this);
        Cursor cur = localDatabase.getMessageReverseCursor();
        ListView listView = (ListView) findViewById(R.id.listView);

        String[] columns = new String[]{"Title"};

        int[] views = new int[]{R.id.title};

        if (cur.getCount() > 0) {
            adapter = new SimpleCursorAdapter(this, R.layout.message_list_layout, cur, columns, views);

            listView.setAdapter(adapter);
        } else {
            Toast.makeText(MessageListActivity.this, "Sorry No data found ", Toast.LENGTH_SHORT).show();
            finish();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                int n = localDatabase.getMessageCount();

                Cursor cursor = localDatabase.getMessageDetails(n-position-1);
                String name = cursor.getString(1);
                Log.i("My_tag",name);
                Intent intent = new Intent(MessageListActivity.this,MessageDisplayActivity.class);
                String title = String.valueOf(cursor.getString(cursor.getColumnIndex("Title")));
                String body = String.valueOf(cursor.getString(cursor.getColumnIndex("Body")));
                intent.putExtra("title",title);
                intent.putExtra("body", body);
                startActivity(intent);

            }
        });

    }
}
