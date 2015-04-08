package com.example.zorbel.service;

import android.os.AsyncTask;

import org.apache.http.client.ClientProtocolException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jaime on 8/04/15.
 */
public class PutOpinion extends AsyncTask<URL, Void, Void> {

    private HttpURLConnection con;

    public PutOpinion() {

    }

    @Override
    protected Void doInBackground(URL... urls) {

        try {
            // Establecer la conexión
            con = (HttpURLConnection) urls[0].openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream());
            out.write("Resource content");
            out.close();
            con.getInputStream();

            int statusCode = con.getResponseCode();

            con.connect();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return null;
    }
}
