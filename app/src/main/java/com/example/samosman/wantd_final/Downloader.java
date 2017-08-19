package com.example.samosman.wantd_final;

/**
 * Created by James Burgess on 2017-07-31.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class Downloader extends AsyncTask<String, Void, String> {

    public static String response;

    /*
    * doInBackGround Method takes the param (in this case a URL) specified in the execute method
    * URL is used to load the source code
    * sourcecode is searched for a specific tag to find names of criminals
    * */
    @Override
    protected String doInBackground(String... params) {
        response = "!!! Pass a URL, dummy!";
        HttpURLConnection urlConnection = null;
        URL url;
//        Log.i("!!!", "This is params 0: " + params[0]);
        for (String each : params) {
            try {
                url = new URL(each);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

                while ((response = buffer.readLine()) != null) {

                    if (response.startsWith("\t\t\t\t\t\t\t\t\t\t\t\t<h1 property=\"name\" id=\"wb-cont\"")) {
                        if (!response.endsWith("</span></a></div>")) {
                            response = "!!! The line is incomplete!";
                        }
                        break;
                    }
                }

                if (response == null) {
                    response = "!!! Why didn't it find the line?!";
                }
            } catch (IOException e) {
                response = "!!! " + e.getLocalizedMessage();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String string) {
        //these methods will only run when the do in
        //background is finished it's task
        Person.parse(Downloader.response);
        Result.compareNames();
        Result.displayFoundCriminals();
    }

}
