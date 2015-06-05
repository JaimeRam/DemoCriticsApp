package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.Proposal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class GetProposalContent extends ConnectionGet {

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
    private static final String TAG_PROPOSAL_NOT_UNDERSTOOD= "not_understood";
    private static final String TAG_PROPOSAL_DISLIKES = "dislikes";
    private static final String TAG_PROPOSAL_NUM_COMMENTS = "comments";

    private Proposal currentProposal;

    public GetProposalContent(Context mContext, View mRootView) {
        super(mContext, mRootView);
    }

    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        getProposalData(super.getJson());

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        updateView();
    }

    protected void updateView() {
        ImageView proposalImage = (ImageView) super.getmRootView().findViewById(R.id.proposalImage);

        TextView proposalTitle = (TextView) super.getmRootView().findViewById(R.id.proposalTitle);
        TextView proposalCategory = (TextView) super.getmRootView().findViewById(R.id.proposalCategory);
        TextView proposalUser = (TextView) super.getmRootView().findViewById(R.id.proposalUser);
        TextView proposalDate = (TextView) super.getmRootView().findViewById(R.id.proposalDate);

        TextView proposalText = (TextView) super.getmRootView().findViewById(R.id.textProposal);
        TextView proposalHow = (TextView) super.getmRootView().findViewById(R.id.howProposal);
        TextView proposalMoney = (TextView) super.getmRootView().findViewById(R.id.moneyProposal);

        Button commentButton = (Button) super.getmRootView().findViewById(R.id.buttonComment);
        Button likeButton = (Button) super.getmRootView().findViewById(R.id.buttonLike);
        Button notUnderstoodButton = (Button) super.getmRootView().findViewById(R.id.buttonNotUnderstood);
        Button dislikeButton = (Button) super.getmRootView().findViewById(R.id.buttonDislike);

        proposalImage.setImageResource(Proposal.getImage(currentProposal.getResLogo()));

        proposalTitle.setText(currentProposal.getTitleProp());
        proposalCategory.setText(currentProposal.getCategory());
        proposalUser.setText(currentProposal.getUser());
        proposalDate.setText(currentProposal.getDate());

        proposalText.setText(currentProposal.getTextProp());
        proposalHow.setText(currentProposal.getHowProp());
        proposalMoney.setText(currentProposal.getMoneyProp());

        likeButton.setText(" " + currentProposal.getNumLikes() + " ");
        dislikeButton.setText(" " + currentProposal.getNumDislikes() + " ");
        notUnderstoodButton.setText(" " + currentProposal.getNumNotUnderstoods() + " ");
        commentButton.setText(" " + currentProposal.getNumComments() + " ");

        //REMOVE COLLABORATIVE PROPOSAL BUTTON
        Button editPropHowButton = (Button) super.getmRootView().findViewById(R.id.buttonEditPropHow);
        Button editPropCostButton = (Button) super.getmRootView().findViewById(R.id.buttonEditPropCost);

        editPropHowButton.setVisibility(View.GONE);
        editPropCostButton.setVisibility(View.GONE);
    }

    protected void getProposalData(String jsonStr) {

        if (jsonStr != null) {
            try {

                JSONArray ar = new JSONArray(jsonStr);

                JSONObject s = ar.getJSONObject(0);

                int id_prop = s.getInt(TAG_PROPOSAL_ID);
                String title = s.getString(TAG_PROPOSAL_TITLE);
                String text = s.getString(TAG_PROPOSAL_TEXT);
                String how = s.getString(TAG_PROPOSAL_HOW);
                String cost = s.getString(TAG_PROPOSAL_COST);

                String image = s.getString(TAG_PROPOSAL_IMAGE);
                String date = s.getString(TAG_PROPOSAL_DATE);
                String category = s.getString(TAG_PROPOSAL_CATEGORY);
                String user = s.getString(TAG_PROPOSAL_USER);

                int views = s.getInt(TAG_PROPOSAL_VIEWS);
                int likes = s.getInt(TAG_PROPOSAL_LIKES);
                int not_understood = s.getInt(TAG_PROPOSAL_NOT_UNDERSTOOD);
                int dislikes = s.getInt(TAG_PROPOSAL_DISLIKES);

                int num_comments = s.getInt(TAG_PROPOSAL_NUM_COMMENTS);

                currentProposal = new Proposal(id_prop, false, title, category, date, user, image, text, how, cost, likes, dislikes, num_comments, not_understood, views);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
