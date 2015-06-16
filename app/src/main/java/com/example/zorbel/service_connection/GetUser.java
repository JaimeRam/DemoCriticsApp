package com.example.zorbel.service_connection;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by jaime on 29/05/15.
 */
public class GetUser extends ConnectionGet {

    private String nick;
    private String email;
    private String id;

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

                id = s.getString("id");
                email = s.getString("email");
                nick = s.getString("nickname");

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

        TextView txt = (TextView) super.getmRootView().findViewById(R.id.userNameMenu);
        txt.setText(nick);

        User.ID_USER = id;
        User.EMAIL = email;
        User.NICKNAME = nick;
    }

}
