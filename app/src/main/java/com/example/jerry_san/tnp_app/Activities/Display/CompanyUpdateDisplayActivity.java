package com.example.jerry_san.tnp_app.Activities.Display;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jerry_san.tnp_app.DateTime;
import com.example.jerry_san.tnp_app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyUpdateDisplayActivity extends AppCompatActivity {

DateTime dateTime=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_update_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        String json = getIntent().getExtras().getString("update");
        JSONObject object = null;
        String name=null;
        String reg_link=null;
        String reg_start=null;
        String reg_end=null;
        String other_details=null;

        try {
            object = new JSONObject(json);
            name=object.getString("name");

            if(!object.isNull("reg_link"))
                reg_link = object.getString("reg_link");

            if(!object.isNull("reg_start"))
                reg_start = object.getString("reg_start");

            if(!object.isNull("reg_end"))
                reg_end =object.getString("reg_end");

            if(!object.isNull("other_details"))
                other_details = object.getString("other_details");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView textview =(TextView) findViewById (R.id.name);
        TextView textview2 =(TextView) findViewById (R.id.reg_link);
        TextView textview3 =(TextView) findViewById (R.id.reg_start);
        TextView textview4 =(TextView) findViewById (R.id.reg_end);
        TextView textview5 =(TextView) findViewById(R.id.other_details);

        Log.i("My_tag","reg link " + reg_link);
        Log.i("My_tag","reg start " + reg_start);

        dateTime= new DateTime();
        if(name!=null)
            textview.setText(name);

        if(reg_link!=null)
            textview2.setText(reg_link);

        if(reg_start!=null) {
            String reg_start_new = dateTime.convert(reg_start);
            textview3.setText(reg_start_new);

        }
        if(reg_end!=null) {
            String reg_end_new = dateTime.convert(reg_end);
            textview4.setText(reg_end_new);
        }
        if(other_details!=null)
            textview5.setText(other_details);
    }
    public  void onClickURL(View view){
        EditText editText = (EditText)findViewById(R.id.reg_link);
        String url = editText.getText().toString();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
