package com.example.zorbel.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zorbel.apptfg.R;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jaime on 8/04/15.
 */
public class PutOpinion extends AsyncTask<URL, Void, Void> {

    private static final String TAG_SECTION_LIKES = "likes";
    private static final String TAG_SECTION_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_SECTION_DISLIKES = "dislikes";
    private static final String TAG_SECTION_NUM_COMMENTS= "comments";

    private HttpURLConnection con;
    private ProgressDialog pDialog;
    private Context mContext;
    private View mRootView;
    private int mListCounters[];

    public PutOpinion(Context mContext, View mRootView) {
        this.mContext = mContext;
        this.mRootView = mRootView;
        mListCounters = new int[4];
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
            out.write("Resource content");
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
                Log.e(GetSectionContent.class.toString(), "Failed to get JSON object");
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

        parseJSON(builder.toString());

        return null;
    }

    private void parseJSON(String JSONString) {
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
        Button likeButton = (Button) mRootView.findViewById(R.id.buttonLike);
        Button notUnderstoodButton = (Button) mRootView.findViewById(R.id.buttonNotUnderstood);
        Button dislikeButton  = (Button) mRootView.findViewById(R.id.buttonDislike);
        Button commentButton = (Button) mRootView.findViewById(R.id.buttonComment);

        likeButton.setText(mContext.getString(R.string.name_buttonLike) + "\n" + "(" + mListCounters[0] + ")");
        notUnderstoodButton.setText(mContext.getString(R.string.name_buttonNotUnderstood) + "\n" + "(" + mListCounters[1] + ")");
        dislikeButton.setText(mContext.getString(R.string.name_buttonDislike) + "\n" + "(" + mListCounters[2] + ")");
        commentButton.setText(mContext.getString(R.string.name_buttonComment) + "\n" + "(" + mListCounters[3] + ")");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.text_dialog_uploading));
        pDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        updateCounters();
        pDialog.dismiss();
    }
}
