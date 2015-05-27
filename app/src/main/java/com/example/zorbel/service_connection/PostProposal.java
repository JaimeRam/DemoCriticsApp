package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;

import com.example.zorbel.data_structures.Comment;
import com.example.zorbel.data_structures.Proposal;

import org.apache.http.NameValuePair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostProposal extends ConnectionPost {

    private static final String TAG_PROPOSAL_ID = "id";
    private static final String TAG_PROPOSAL_TITLE = "title";
    private static final String TAG_PROPOSAL_TEXT = "text";
    private static final String TAG_PROPOSAL_HOW = "how";
    private static final String TAG_PROPOSAL_COST = "cost";
    private static final String TAG_PROPOSAL_IMAGE = "image";
    private static final String TAG_PROPOSAL_DATE = "date";
    private static final String TAG_PROPOSAL_CATEGORY = "category";
    private static final String TAG_PROPOSAL_USER = "user";

    private static final String TAG_PROPOSAL_VIEWS = "views";
    private static final String TAG_PROPOSAL_LIKES = "likes";
    private static final String TAG_PROPOSAL_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_PROPOSAL_DISLIKES = "dislikes";
    private static final String TAG_PROPOSAL_NUM_COMMENTS = "comments";

    private List<Comment> listComments;
    private Proposal currentProposal;

    public PostProposal(Context mContext, ArrayList<NameValuePair> par, View mRootView) {
        super(mContext, mRootView, par);
    }

    @Override
    protected Void doInBackground(URL... urls) {
        super.doInBackground(urls);
        return null;
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
