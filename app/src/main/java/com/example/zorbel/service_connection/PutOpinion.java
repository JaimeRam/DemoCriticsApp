package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.example.zorbel.apptfg.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class PutOpinion extends ConnectionPut {

    private static final String TAG_SECTION_LIKES = "likes";
    private static final String TAG_SECTION_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_SECTION_DISLIKES = "dislikes";
    private static final String TAG_SECTION_NUM_COMMENTS = "comments";

    private int mListCounters[];

    public PutOpinion(Context mContext, ArrayList<NameValuePair> par, View mRootView) {
        super(mContext, par, mRootView);
        this.mListCounters = new int[4];
    }

    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        putOp(super.getJson());

        return null;
    }

    private void putOp(String JSONString) {
        if (JSONString != null) {
            try {
                JSONArray response = new JSONArray(JSONString);

                JSONObject s = response.getJSONObject(0);
                mListCounters[0] = s.getInt(TAG_SECTION_LIKES);
                mListCounters[1] = s.getInt(TAG_SECTION_NOT_UNDERSTOOD);
                mListCounters[2] = s.getInt(TAG_SECTION_DISLIKES);
                mListCounters[3] = s.getInt(TAG_SECTION_NUM_COMMENTS);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCounters() {
        Button likeButton = (Button) super.getmRootView().findViewById(R.id.buttonLike);
        Button notUnderstoodButton = (Button) super.getmRootView().findViewById(R.id.buttonNotUnderstood);
        Button dislikeButton = (Button) super.getmRootView().findViewById(R.id.buttonDislike);
        Button commentButton = (Button) super.getmRootView().findViewById(R.id.buttonComment);

        likeButton.setText(" " + mListCounters[0] + " ");
        notUnderstoodButton.setText(" " + mListCounters[1] + " ");
        dislikeButton.setText(" " + mListCounters[2] + " ");
        commentButton.setText(" " + mListCounters[3] + " ");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        updateCounters();
    }
}
