package com.example.zorbel.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.zorbel.apptfg.R;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jaime on 15/04/15.
 */
public class GetTop10 extends AsyncTask<URL, Void, Void> {

    private static final String TAG_VIEWS = "views";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_DISLIKES = "dislikes";
    private static final String TAG_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_COMMENTS = "comments";

    private static final String TAG_SECTION_ID = "section";
    private static final String TAG_SECTION_TITLE = "title";
    private static final String TAG_SECTION_TEXT = "text";
    private static final String TAG_SECTION_LIKES = "likes";
    private static final String TAG_SECTION_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_SECTION_DISLIKES = "dislikes";
    private static final String TAG_SECTION_NUM_COMMENTS= "comments";

    private HttpURLConnection con;
    private Context mContext;
    private View mRootView;
    private Activity mActivityMain;
    private ProgressDialog pDialog;

    public GetTop10(HttpURLConnection con, Context mContext, View mRootView, Activity mActivityMain) {
        this.con = con;
        this.mContext = mContext;
        this.mRootView = mRootView;
        this.mActivityMain = mActivityMain;
    }

    @Override
    protected Void doInBackground(URL... urls) {
        StringBuilder builder = new StringBuilder();

        try {

            con = (HttpURLConnection) urls[0].openConnection();

            int statusCode = con.getResponseCode();

            if (statusCode == 200) {
                InputStream in = new BufferedInputStream(con.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(GetPoliticalParties.class.toString(), "Failed to get JSON object");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        parseJSON(builder.toString());

        return null;
    }

    private void parseJSON(String JSONString) {
        if (JSONString != null) {
            try {

                JSONObject s = new JSONObject(JSONString);

                //TODO: Process JSON Object

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        showTop();
    }

    /**
     * This method shows the list Top3 on the MainActivity.
     */

    private void showTop() {
        //TODO: Show data
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.text_dialog_downloading));
        pDialog.show();
    }
}