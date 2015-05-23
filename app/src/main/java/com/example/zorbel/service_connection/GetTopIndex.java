package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.TopItemAdapter;
import com.example.zorbel.apptfg.views.TopHeaderItem;
import com.example.zorbel.apptfg.views.TopItem;
import com.example.zorbel.data_structures.Section;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class GetTopIndex extends ConnectionGet {

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

    private ArrayList<TopItem> listTop;

    public GetTopIndex(Context mContext, View mRootView) {
        super(mContext, mRootView);
    }

    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        getTop3(super.getJson());

        return null;
    }

    private void getTop3(String JSONString) {
        if (JSONString != null) {
            try {

                JSONObject s = new JSONObject(JSONString);

                //Processing the views top 3

                listTop = new ArrayList<TopItem>();

                TopHeaderItem hV = new TopHeaderItem(TAG_VIEWS, super.getmContext().getString(R.string.name_headerMoreViews), R.mipmap.ic_views_eye);

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

                    Section sec = new Section(idSec, idPol, title, "", numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }


                //Processing the likes top 3

                TopHeaderItem hL = new TopHeaderItem(TAG_LIKES, super.getmContext().getString(R.string.name_headerMoreLikes), R.mipmap.ic_greenlike);

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

                    Section sec = new Section(idSec, idPol, title, "", numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }


                //Processing the dislikes top 3

                TopHeaderItem hD = new TopHeaderItem(TAG_DISLIKES, super.getmContext().getString(R.string.name_headerMoreDislikes), R.mipmap.ic_reddislike);

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

                    Section sec = new Section(idSec, idPol, title, "", numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }


                //Processing the not understood top 3

                TopHeaderItem hN = new TopHeaderItem(TAG_NOT_UNDERSTOOD, super.getmContext().getString(R.string.name_headerMoreNotUnderstood), R.mipmap.ic_bluenotunderstood);

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

                    Section sec = new Section(idSec, idPol, title, "", numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }


                //Processing the comments top 3

                TopHeaderItem hC = new TopHeaderItem(TAG_COMMENTS, super.getmContext().getString(R.string.name_headerMoreComments), R.mipmap.ic_commentwhite);

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

                    Section sec = new Section(idSec, idPol, title, "", numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop.add(sec);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method shows the Top3 on the MainActivity.
     */

    private void showTop() {

        ListView topListView = (ListView) super.getmRootView().findViewById(R.id.topTabPageListView);
        topListView.setAdapter(new TopItemAdapter(super.getmContext(), listTop));

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        showTop();
    }
}
