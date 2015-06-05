package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.TopItemAdapter;
import com.example.zorbel.apptfg.views.TopItem;
import com.example.zorbel.data_structures.Proposal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetTopProposals extends ConnectionGet {

    private static final String TAG_PROPOSAL_ID = "id";
    private static final String TAG_PROPOSAL_TITLE = "title";
    private static final String TAG_ID_IMAGE = "id_image";
    private static final String TAG_VIEWS = "views";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_DISLIKES = "dislikes";
    private static final String TAG_DATE = "date";
    private static final String TAG_USER = "user";
    private static final String TAG_COMMENTS = "comments";

    private List<TopItem> listTopProposals;

    public GetTopProposals(Context mContext, View mRootView) {
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

                listTopProposals = new ArrayList<TopItem>();

                for (int i = 0; i < ar.length(); i++) {

                    JSONObject ob = ar.getJSONObject(i);

                    int idProposal = ob.getInt(TAG_PROPOSAL_ID);
                    String title = ob.getString(TAG_PROPOSAL_TITLE);
                    String idImage = ob.getString(TAG_ID_IMAGE);

                    int numViews = ob.getInt(TAG_VIEWS);
                    int numLikes = ob.getInt(TAG_LIKES);
                    int numNotUnd = ob.getInt(TAG_NOT_UNDERSTOOD);
                    int numDislikes = ob.getInt(TAG_DISLIKES);

                    String date = ob.getString(TAG_DATE);
                    String user = ob.getString(TAG_USER);
                    int numComments = ob.getInt(TAG_COMMENTS);


                    Proposal prop = new Proposal(idProposal, false, title, null, date, user, idImage, null, null, null, numLikes, numDislikes, numComments, numNotUnd, numViews);
                    listTopProposals.add(prop);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showTop() {
        ListView topTabPageListView = (ListView) super.getmRootView().findViewById(R.id.topListView);
        topTabPageListView.setAdapter(new TopItemAdapter(super.getmContext(), listTopProposals));
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
