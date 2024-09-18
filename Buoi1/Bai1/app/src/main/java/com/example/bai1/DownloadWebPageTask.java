package com.example.bai1;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    private TextView textView;

    public DownloadWebPageTask() {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder response = new StringBuilder();
        for (String url : urls) {
            try {
                URL urlObj = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
    }
}