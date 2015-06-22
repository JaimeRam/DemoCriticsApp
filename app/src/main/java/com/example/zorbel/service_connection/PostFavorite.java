package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

/**
 * Created by jaime on 22/06/15.
 */
public class PostFavorite extends ConnectionPost {

    public PostFavorite(Context mContext, ArrayList<NameValuePair> par, View mRootView) {
        super(mContext, mRootView, par);
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
