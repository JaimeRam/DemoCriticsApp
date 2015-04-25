package com.example.zorbel.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.TopHeaderItem;
import com.example.zorbel.apptfg.TopItem;
import com.example.zorbel.apptfg.TopItemAdapter;
import com.example.zorbel.data_structures.Section;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jaime on 15/04/15.
 */
public class GetTopIndex extends AsyncTask<URL, Void, Void> {

    private static final String TAG_ID_POLITICAL_PARTY = "id_political_party";
    private static final String TAG_SECTION_ID = "section";
    private static final String TAG_SECTION_TITLE = "title";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_DISLIKES = "dislikes";
    private static final String TAG_VIEWS = "views";
    private static final String TAG_COMMENTS = "comments";

    private static final String TAG_TOP_VIEWS = "top_views";
    private static final String TAG_TOP_LIKES = "top_likes";
    private static final String TAG_TOP_DISLIKES = "top_dislikes";
    private static final String TAG_TOP_NOT_UNDERSTOOD = "top_not_understood";
    private static final String TAG_TOP_COMMENTS = "top_comments";



    private HttpURLConnection con;
    private Context mContext;
    private View mRootView;
    private ProgressDialog pDialog;

    private ArrayList<TopItem> listTop;


    public GetTopIndex(Context mContext, View mRootView) {
        this.mContext = mContext;
        this.mRootView = mRootView;
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

                //Processing the views top 3

                listTop = new ArrayList<TopItem>();

                TopHeaderItem hV = new TopHeaderItem(TAG_VIEWS, mContext.getString(R.string.name_headerMoreViews), R.mipmap.ic_views_eye);

                listTop.add(hV);

                JSONArray arV = s.getJSONArray(TAG_TOP_VIEWS);

                for (int i = 0; i < arV.length(); i++) {

                    JSONObject ob = arV.getJSONObject(i);

                    int idPol = ob.getInt(TAG_ID_POLITICAL_PARTY);
                    int idSec = ob.getInt(TAG_SECTION_ID);
                    String title = ob.getString(TAG_SECTION_TITLE);

                    int numLikes = ob.getInt(TAG_LIKES);
                    int numNotUnd = ob.getInt(TAG_NOT_UNDERSTOOD);
                    int numDislikes = ob.getInt(TAG_DISLIKES);
                    int numViews = ob.getInt(TAG_VIEWS);
                    int numComments = ob.getInt(TAG_COMMENTS);

                    Section sec = new Section(idSec, idPol, title, numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }


                //Processing the likes top 3

                TopHeaderItem hL = new TopHeaderItem(TAG_LIKES, mContext.getString(R.string.name_headerMoreLikes), R.mipmap.ic_greenlike);

                listTop.add(hL);

                JSONArray arL = s.getJSONArray(TAG_TOP_LIKES);

                for (int i = 0; i < arL.length(); i++) {

                    JSONObject ob = arL.getJSONObject(i);

                    int idPol = ob.getInt(TAG_ID_POLITICAL_PARTY);
                    int idSec = ob.getInt(TAG_SECTION_ID);
                    String title = ob.getString(TAG_SECTION_TITLE);

                    int numLikes = ob.getInt(TAG_LIKES);
                    int numNotUnd = ob.getInt(TAG_NOT_UNDERSTOOD);
                    int numDislikes = ob.getInt(TAG_DISLIKES);
                    int numViews = ob.getInt(TAG_VIEWS);
                    int numComments = ob.getInt(TAG_COMMENTS);

                    Section sec = new Section(idSec, idPol, title, numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }


                //Processing the dislikes top 3

                TopHeaderItem hD = new TopHeaderItem(TAG_DISLIKES, mContext.getString(R.string.name_headerMoreDislikes), R.mipmap.ic_reddislike);

                listTop.add(hD);

                JSONArray arD = s.getJSONArray(TAG_TOP_DISLIKES);

                for (int i = 0; i < arD.length(); i++) {

                    JSONObject ob = arD.getJSONObject(i);

                    int idPol = ob.getInt(TAG_ID_POLITICAL_PARTY);
                    int idSec = ob.getInt(TAG_SECTION_ID);
                    String title = ob.getString(TAG_SECTION_TITLE);

                    int numLikes = ob.getInt(TAG_LIKES);
                    int numNotUnd = ob.getInt(TAG_NOT_UNDERSTOOD);
                    int numDislikes = ob.getInt(TAG_DISLIKES);
                    int numViews = ob.getInt(TAG_VIEWS);
                    int numComments = ob.getInt(TAG_COMMENTS);

                    Section sec = new Section(idSec, idPol, title, numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }


                //Processing the not understood top 3

                TopHeaderItem hN = new TopHeaderItem(TAG_NOT_UNDERSTOOD, mContext.getString(R.string.name_headerMoreNotUnderstood), R.mipmap.ic_bluenotunderstood);

                listTop.add(hN);

                JSONArray arN = s.getJSONArray(TAG_TOP_NOT_UNDERSTOOD);

                for (int i = 0; i < arN.length(); i++) {

                    JSONObject ob = arN.getJSONObject(i);

                    int idPol = ob.getInt(TAG_ID_POLITICAL_PARTY);
                    int idSec = ob.getInt(TAG_SECTION_ID);
                    String title = ob.getString(TAG_SECTION_TITLE);

                    int numLikes = ob.getInt(TAG_LIKES);
                    int numNotUnd = ob.getInt(TAG_NOT_UNDERSTOOD);
                    int numDislikes = ob.getInt(TAG_DISLIKES);
                    int numViews = ob.getInt(TAG_VIEWS);
                    int numComments = ob.getInt(TAG_COMMENTS);

                    Section sec = new Section(idSec, idPol, title, numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }


                //Processing the comments top 3

                TopHeaderItem hC = new TopHeaderItem(TAG_COMMENTS, mContext.getString(R.string.name_headerMoreComments), R.mipmap.ic_commentwhite);

                listTop.add(hC);

                JSONArray arC = s.getJSONArray(TAG_TOP_COMMENTS);

                for (int i = 0; i < arC.length(); i++) {

                    JSONObject ob = arC.getJSONObject(i);

                    int idPol = ob.getInt(TAG_ID_POLITICAL_PARTY);
                    int idSec = ob.getInt(TAG_SECTION_ID);
                    String title = ob.getString(TAG_SECTION_TITLE);

                    int numLikes = ob.getInt(TAG_LIKES);
                    int numNotUnd = ob.getInt(TAG_NOT_UNDERSTOOD);
                    int numDislikes = ob.getInt(TAG_DISLIKES);
                    int numViews = ob.getInt(TAG_VIEWS);
                    int numComments = ob.getInt(TAG_COMMENTS);

                    Section sec = new Section(idSec, idPol, title, numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method shows the list Top3 on the MainActivity.
     */

    private void showTop() {


        ListView topListView = (ListView) mRootView.findViewById(R.id.topIndexListView);
        topListView.setAdapter(new TopItemAdapter(mContext, listTop));

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.text_dialog_downloading));
        pDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        pDialog.dismiss();
        showTop();
    }
}
