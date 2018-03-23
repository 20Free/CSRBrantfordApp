package com.csrbrantford.csrbrantfordapp.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.csrbrantford.csrbrantfordapp.asyncTask.AsyncResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jonathan on 7/6/2016.
 */
public class JSONAsyncTask extends AsyncTask<String,Void,String>{

    public AsyncResponse delegate = null;

    public JSONAsyncTask(AsyncResponse asyncResponse){
        delegate = asyncResponse;
    }

    @Override
    protected String doInBackground(String... urls) {

        StringBuffer response = null;

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            response= new StringBuffer();

            while((input = in.readLine()) != null){
                response.append(input);
            }
            in.close();

        } catch (Exception e){
            Log.e("CONNECTION ERROR", "There is a problem submitting the get request");
            e.printStackTrace();
        }

        if(response != null) {
            return response.toString();
        }

        String nullResponse = "Connection error, Info could not be retrieved.";

        return nullResponse;
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        delegate.processFinish(json);
    }
}