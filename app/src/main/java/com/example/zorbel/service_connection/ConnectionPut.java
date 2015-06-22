package com.example.zorbel.service_connection;

import android.content.Context;
import android.util.Log;
import android.view.View;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPut extends ConnectionURL {

    private HttpURLConnection con;

    private String json;
    private List<NameValuePair> params;

    public ConnectionPut(Context mContext, ArrayList<NameValuePair> par, View mRootView) {
        super(mContext, mRootView);
        this.params = par;
    }

    @Override
    protected Void doInBackground(URL... urls) {

            StringBuilder builder = new StringBuilder();

            try {
                // Establecer la conexión
                con = (HttpURLConnection) urls[0].openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("PUT");
                OutputStreamWriter out = new OutputStreamWriter(
                        con.getOutputStream());
                out.write(getQuery(params));
                //out.write("Resource content");
                out.close();
                con.getInputStream();

                int statusCode = con.getResponseCode();

                if (statusCode == 200) {
                    InputStream in = new BufferedInputStream(con.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } else {
                    Log.e(PostSectionContent.class.toString(), "Failed to get JSON object");
                }

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

            json = builder.toString();

            return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public String getJson() {
        return json;
    }

    protected String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
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
