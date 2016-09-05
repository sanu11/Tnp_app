package com.example.jerry_san.myfirstapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Created by jerry-san on 8/13/16.
 */

public class SaveLocalData extends AsyncTask <JSONObject ,String ,String >
{
    String url ="http://localhost/register/";

    protected String doInBackground(JSONObject... obj)
    {

        InputStream inputStream;
        String result = "";

        try{

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = obj.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);

            Log.i("My_tag",json);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type","application/json");

            HttpResponse httpResponse = httpClient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream!=null){
                result = convertInputStreamToString(inputStream);
            }
            else
            {
                result = "Did not work!";
            }
        }catch(Exception e){

            Log.i("My_tag",e.toString());
        }
        Log.i("My_tag",result);
        return result;

    }

    protected void onProgressUpdate() {
    }

    private static String convertInputStreamToString(InputStream is) {
        // convert InputStream to String


        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
