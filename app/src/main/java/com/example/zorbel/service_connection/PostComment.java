package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.example.zorbel.apptfg.adapters.CommentListAdapter;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.Comment;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostComment extends ConnectionPost {

    private static final String TAG_COMMENT_USER = "nickname";
    private static final String TAG_COMMENT_TEXT = "text";
    private static final String TAG_COMMENT_DATE = "date";

    private List<Comment> listComments;

    public PostComment(Context mContext, ArrayList<NameValuePair> par, View mRootView) {

        super(mContext, mRootView, par);
    }

    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        getComments(super.getJson());

        return null;
    }

    private void getComments(String jsonStr) {

        listComments = new ArrayList<Comment>();

        if (jsonStr != null) {
            try {

                JSONArray sections = new JSONArray(jsonStr);

                for (int i = 0; i < sections.length(); i++) {
                    JSONObject s = sections.getJSONObject(i);

                    String user = s.getString(TAG_COMMENT_USER);
                    String text = s.getString(TAG_COMMENT_TEXT);
                    String date = s.getString(TAG_COMMENT_DATE);

                    Comment com = new Comment(user, date, text);

                    listComments.add(com);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ListView listview = (ListView) super.getmRootView().findViewById(R.id.listViewComments);
        listview.setAdapter(new CommentListAdapter(super.getmContext(), listComments));
    }



}
