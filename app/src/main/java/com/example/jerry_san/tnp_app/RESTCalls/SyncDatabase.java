package com.example.jerry_san.tnp_app.RESTCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;
import com.example.jerry_san.tnp_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jerry-san on 9/27/16.
 */

public class SyncDatabase extends AsyncTask<String, String, String> {
    Context context;

    public SyncDatabase(Context context) {
        this.context = context;
    }

    LocalDatabase localDatabase;

    protected String doInBackground(String... params) {
        String JsonResponse = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String TAG = "My_tag";
        localDatabase = new LocalDatabase(context);
        try {

            URL url = new URL(R.string.digitalocean + "sync/");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            //append all the data to json string
            String json = "";

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                json = json + String.valueOf(current);
            }

            Log.i("My_tag", json);

            JSONArray jsonArray = new JSONArray(json);
            int num = jsonArray.length();
            JSONObject jObject;
            String name;

            for (int i = 0; i < num; i++) {

                jObject = jsonArray.getJSONObject(i).getJSONObject("fields");
                name = jObject.getString("name");
                boolean val = localDatabase.checkCompanyifExists(name);
                String temp = null;

                if (!val) {
                    //since we get dat_time in this format2016-10-10T10:00:00Z
                    if (!jObject.isNull("ppt_date")) {

                        temp = jObject.getString("ppt_date");
                        String arr[] = temp.split("T");
                        temp = arr[1].split("Z")[0];
                        String ppt_date = arr[0] + " " + temp;
                        jObject.put("ppt_date", ppt_date);
                    }

                    if (!jObject.isNull("reg_start_date")) {
                        temp = jObject.getString("reg_start_date");
                        String arr2[] = temp.split("T");
                        temp = arr2[1].split("Z")[0];
                        String reg_start = arr2[0] + " " + temp;
                        jObject.put("reg_start_date", reg_start);
                    }

                    if (!jObject.isNull("reg_end_date")) {
                        temp = jObject.getString("reg_end_date");
                        String arr3[] = temp.split("T");
                        temp = arr3[1].split("Z")[0];
                        String reg_end = arr3[0] + " " + temp;
                        jObject.put("reg_end_date", reg_end);
                    }

                    long res = localDatabase.companyInsertJson(jObject);
                    Log.i("My_tag", String.valueOf(res));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub

        super.onPreExecute();

    }


    @Override
    protected void onPostExecute(String args) {
        // TODO Auto-generated method stub


    }
}


//                         Iterator<String> keys = jObject.keys();
//                          while (keys.hasNext()) {
//
//                        String key = keys.next();
//                        String value = jObject.getString(key);
//                        Log.i("My_tag", key + "  " + value);
//
//
//                    }
