package com.example.jerry_san.tnp_app.RESTCalls;

/**
 * Created by jerry-san on 8/30/16.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.jerry_san.tnp_app.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.jerry_san.tnp_app.Constants;

public class LoginCheckServer extends AsyncTask<String,String,String >
{
        protected String doInBackground(String... params) {
        String JsonResponse = null;
        String JsonDATA = params[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String TAG="My_tag";
        try {

            URL url = new URL(Constants.studentLoginUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);

            //set headers and method
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            Log.i("My_tag","Connecting server for Login...");

            // is output buffer writter
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);

            writer.close();

            //input stream
            InputStream inputStream = urlConnection.getInputStream();

            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");

            buffer.deleteCharAt(buffer.length()-1);
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            //response data
            JsonResponse = buffer.toString();
           Log.i("My_tag",JsonResponse+"  data");

            try {
                //send to post execute
                return JsonResponse;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;



        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

}

