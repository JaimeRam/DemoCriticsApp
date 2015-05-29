package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;

import org.apache.http.NameValuePair;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jaime on 29/05/15.
 */
public class PostNameUser extends ConnectionPost {
    public PostNameUser(Context mContext, View mRootView, ArrayList<NameValuePair> par) {
        super(mContext, mRootView, par);
    }

    @Override
    protected Void doInBackground(URL... urls) {
        super.doInBackground(urls);
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
}
