package com.example.jerry_san.tnp_app.RESTCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry_san.tnp_app.DatabaseHelper.LocalDatabase;

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

public class SyncDatabase extends AsyncTask<String,String,String >
{
    Context context;
    public SyncDatabase(Context context)
    {
        this.context=context;
    }

    LocalDatabase localDatabase;
    protected String doInBackground(String... params) {
        String JsonResponse = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String TAG="My_tag";
       localDatabase =new LocalDatabase(context);
        try {

            URL url = new URL("http://tnp-app.herokuapp.com/sync/");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            //append all the data to json string
            String json="";

            int data = isw.read();
            while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    json= json + String.valueOf(current);
            }

            Log.i("My_tag",json);

            JSONArray jsonArray = new JSONArray(json);
            int num=jsonArray.length();
            JSONObject jObject;
            String name;
            Log.i("My_tag",String.valueOf(num));

            for(int i=0;i<num;i++) {


                jObject = jsonArray.getJSONObject(i).getJSONObject("fields");
                name = jObject.getString("name");
                Log.i("My_tag",name);
                boolean val =localDatabase.checkifexists(name);


                if ( ! val ) {
                    //since we get dat_time in this format2016-10-10T10:00:00Z

                    String arr[]=jObject.getString("ppt_date").split("T");
                    String temp = arr[1].split("Z")[0];
                    String ppt_date=arr[0] +" " + temp;
                    jObject.put("ppt_date",ppt_date);

                    boolean res=localDatabase.company_insert_json(jObject);
                    Log.i("My_tag",String.valueOf(res));

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
