package com.example.zorbel.service;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by jaime on 7/04/15.
 */
public class PostComment extends AsyncTask<URL, Void, Void> {

    private HttpURLConnection con;
    private int id_user;
    private int section;
    private int id_political_party;
    private String text;
    private List<NameValuePair> params;

    public PostComment(int id_user, int section, int id_political_party, String text) {
        this.id_user = id_user;
        this.section = section;
        this.id_political_party = id_political_party;
        this.text = text;

        this.params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id_political_party", Integer.toString(id_political_party)));
        params.add(new BasicNameValuePair("id_user", Integer.toString(id_user)));
        params.add(new BasicNameValuePair("section", Integer.toString(section)));
        params.add(new BasicNameValuePair("text", text));
    }

    @Override
    protected Void doInBackground(URL... urls) {
        StringBuilder builder = new StringBuilder();

        try {
            // Establecer la conexión
            con = (HttpURLConnection) urls[0].openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();

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

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
