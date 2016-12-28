package com.example.jerry_san.tnp_app.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jerry_san.tnp_app.NetworkConnection;
import com.example.jerry_san.tnp_app.R;
import com.example.jerry_san.tnp_app.RESTCalls.Verify;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NetworkConnection connection = new NetworkConnection(this.getApplicationContext());
        boolean con = connection.checkNetwork();
        //Log.i("My_tag", "connection " + con);
        if (!con) {
            Toast.makeText(VerifyActivity.this, "Check your Network", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onClickVerify(View view) throws ExecutionException, InterruptedException, JSONException {

        NetworkConnection connection = new NetworkConnection(this.getApplicationContext());
        boolean con = connection.checkNetwork();
        //Log.i("My_tag", "connection " + con);
        if (!con) {
            Toast.makeText(VerifyActivity.this, "Check your Network", Toast.LENGTH_SHORT).show();
            return;
        }

        String prn = null;

        EditText editText = (EditText) findViewById(R.id.verify);
        prn = editText.getText().toString();
        prn.trim();
        if (prn.equals("")) {
            Toast.makeText(VerifyActivity.this, "Enter PRN eg C2K13104366", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject obj = new JSONObject();

        obj.put("prn", prn);
        Log.i("My_tag","prn " + prn);
        Verify verify = new Verify();
        String res = null;
        try {
            res = verify.execute(obj.toString()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (res == null) {
            Toast.makeText(VerifyActivity.this, "Error", Toast.LENGTH_SHORT).show();
        } else if (res.equals("Success")) {
            Toast.makeText(VerifyActivity.this, "Successful Verified", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else if (res.equals("Failed")) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Verification Failed");
            alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });
            alertDialogBuilder.setMessage("Enter valid PRN");
            alertDialogBuilder.show();
        } else {
            Toast.makeText(VerifyActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

}
