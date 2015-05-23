package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.TopItemAdapter;
import com.example.zorbel.apptfg.views.TopItem;
import com.example.zorbel.data_structures.Section;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class GetTopSections extends ConnectionGet {

    private static final String TAG_ID_POLITICAL_PARTY = "id_political_party";
    private static final String TAG_SECTION_ID = "section";
    private static final String TAG_SECTION_TITLE = "title";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_DISLIKES = "dislikes";
    private static final String TAG_VIEWS = "views";
    private static final String TAG_COMMENTS = "comments";
    private static final String TAG_CATEGORY = "category";

    private ArrayList<TopItem> listTopSections;

    public GetTopSections(Context mContext, View mRootView) {
        super(mContext, mRootView);
    }


    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        getTop(super.getJson());

        return null;
    }

    private void getTop(String JSONString) {
        if (JSONString != null) {
            try {

                JSONArray ar = new JSONArray(JSONString);

                listTopSections = new ArrayList<TopItem>();

                for (int i = 0; i < ar.length(); i++) {

                    JSONObject ob = ar.getJSONObject(i);

                    int idPol = ob.getInt(TAG_ID_POLITICAL_PARTY);
                    int idSec = ob.getInt(TAG_SECTION_ID);
                    String title = ob.getString(TAG_SECTION_TITLE);
                    String category = ob.getString(TAG_CATEGORY);

                    int numLikes = ob.getInt(TAG_LIKES);
                    int numNotUnd = ob.getInt(TAG_NOT_UNDERSTOOD);
                    int numDislikes = ob.getInt(TAG_DISLIKES);
                    int numViews = ob.getInt(TAG_VIEWS);
                    int numComments = ob.getInt(TAG_COMMENTS);

                    Section sec = new Section(idSec, idPol, title, category, numLikes, numDislikes, numNotUnd, numComments, numViews);
                    listTopSections.add(sec);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method shows the Top10 list on the Top10Activity.
     */

    private void showTop() {
        ListView topTabPageListView = (ListView) super.getmRootView().findViewById(R.id.topTabPageListView);
        topTabPageListView.setAdapter(new TopItemAdapter(super.getmContext(), listTopSections));
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