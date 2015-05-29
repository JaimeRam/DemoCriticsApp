package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;

import com.example.zorbel.data_structures.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by jaime on 29/05/15.
 */
public class GetUser extends ConnectionGet {
    public GetUser(Context mContext, View mRootView) {
        super(mContext, mRootView);
    }

    @Override
    protected Void doInBackground(URL... urls) {
        super.doInBackground(urls);
        getUserData(super.getJson());
        return null;
    }

    private void getUserData(String jsonStr) {
        if (jsonStr != null) {
            try {
                JSONArray ar = new JSONArray(jsonStr);

                JSONObject s = ar.getJSONObject(0);

                User.ID_USER = s.getString("id");
                User.EMAIL = s.getString("email");
                User.NICKNAME = s.getString("nickname");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getJson() {
        return super.getJson();
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
