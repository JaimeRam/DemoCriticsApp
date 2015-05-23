package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;

import org.apache.http.NameValuePair;

import java.net.URL;
import java.util.ArrayList;

public class PostProposal extends ConnectionPost {

    public PostProposal(Context mContext, ArrayList<NameValuePair> par, View mRootView) {
        super(mContext, mRootView, par);
    }

    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        //getComments(super.getJson());

        return null;
    }
}
