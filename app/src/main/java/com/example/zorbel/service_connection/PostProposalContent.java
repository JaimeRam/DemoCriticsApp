package com.example.zorbel.service_connection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.collaborate.EditWaveActivity;
import com.example.zorbel.data_structures.Proposal;
import com.example.zorbel.data_structures.User;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.swellrt.android.service.SwellRTService;
import org.swellrt.model.generic.Model;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PostProposalContent extends ConnectionPost {

    private static final String TAG_PROPOSAL_ID = "id";
    private static final String TAG_PROPOSAL_TITLE = "title";
    private static final String TAG_PROPOSAL_TEXT = "text";
    private static final String TAG_PROPOSAL_HOW = "how";
    private static final String TAG_PROPOSAL_COST = "cost";
    private static final String TAG_PROPOSAL_IMAGE = "image";
    private static final String TAG_PROPOSAL_DATE = "date";
    private static final String TAG_PROPOSAL_CATEGORY = "category";
    private static final String TAG_PROPOSAL_USER = "user";

    private static final String TAG_PROPOSAL_WAVE = "id_wave";

    private static final String TAG_PROPOSAL_VIEWS = "views";
    private static final String TAG_PROPOSAL_LIKES = "likes";
    private static final String TAG_PROPOSAL_NOT_UNDERSTOOD= "not_understood";
    private static final String TAG_PROPOSAL_DISLIKES = "dislikes";
    private static final String TAG_PROPOSAL_NUM_COMMENTS = "comments";
    private static final String TAG_PROPOSAL_FAVORITE = "favorite";

    private boolean isCollaborative;
    private Proposal currentProposal;

    public PostProposalContent(Context mContext, ArrayList<NameValuePair> par, View mRootView, boolean isCollaborative) {
        super(mContext, mRootView, par);
        this.isCollaborative = isCollaborative;
    }

    private class AddWaveParticipantClass implements ServiceConnection, SwellRTService.SwellRTServiceCallback {

        private String modelId;
        private Context mContext;
        private Model mModel;
        private SwellRTService mSwellRT;


        public AddWaveParticipantClass(String id, Context mCon) {
            this.modelId = id;
            this.mContext = mCon;
            bindSwellRTService();
        }

        protected void bindSwellRTService() {

            if (mSwellRT == null) {
                final Intent mWaveServiceIntent = new Intent(mContext, SwellRTService.class);
                mContext.bindService(mWaveServiceIntent, this, Context.BIND_AUTO_CREATE);
            }

        }

        public void doStartSession() {

            try {
                mSwellRT.startSession(MainActivity.WAVE_SERVER,
                        "admin" + "@local.net", "password");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (InvalidParticipantAddress invalidParticipantAddress) {
                invalidParticipantAddress.printStackTrace();
            }
        }

        private void doStopSession() {

            if (mSwellRT != null) {
                mSwellRT.stopSession();
            }

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mSwellRT = ((SwellRTService.SwellRTBinder) service).getService(this);
            doStartSession();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onStartSessionSuccess(String s) {
            mSwellRT.openModel(modelId);
        }

        @Override
        public void onStartSessionFail(String s) {

        }

        @Override
        public void onCreate(Model model) {

        }

        @Override
        public void onOpen(Model model) {
            // Gets a reference to an already open Model
            mModel = mSwellRT.getModel(modelId);

            //Add actual participant
            mModel.addParticipant("" + User.ID_USER + "@local.net");

            mSwellRT.closeModel(modelId);
            doStopSession();
        }

        @Override
        public void onClose(boolean b) {

        }

        @Override
        public void onUpdate(int i, int i1, int i2) {

        }

        @Override
        public void onError(String s) {

        }

        @Override
        public void onDebugInfo(String s) {

        }

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

        if(!isCollaborative) {
            //REMOVE COLLABORATIVE PROPOSAL BUTTON
            Button editPropHowButton = (Button) super.getmRootView().findViewById(R.id.buttonEditPropHow);
            Button editPropCostButton = (Button) super.getmRootView().findViewById(R.id.buttonEditPropCost);
            Button savePropButton = (Button) super.getmRootView().findViewById(R.id.buttonSaveProp);

            editPropHowButton.setVisibility(View.GONE);
            editPropCostButton.setVisibility(View.GONE);
            savePropButton.setVisibility(View.GONE);
        }
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

        proposalImage.setImageResource(Proposal.getImage(currentProposal.getResLogo()));

        proposalTitle.setText(currentProposal.getTitleProp());
        proposalCategory.setText(currentProposal.getCategory());
        proposalUser.setText(currentProposal.getUser());
        proposalDate.setText(currentProposal.getDate());

        proposalText.setText(currentProposal.getTextProp());

        if(!isCollaborative) {
            TextView proposalHow = (TextView) super.getmRootView().findViewById(R.id.howProposal);
            TextView proposalMoney = (TextView) super.getmRootView().findViewById(R.id.moneyProposal);

            Button commentButton = (Button) super.getmRootView().findViewById(R.id.buttonComment);
            Button likeButton = (Button) super.getmRootView().findViewById(R.id.buttonLike);
            Button notUnderstoodButton = (Button) super.getmRootView().findViewById(R.id.buttonNotUnderstood);
            Button dislikeButton = (Button) super.getmRootView().findViewById(R.id.buttonDislike);
            ImageButton favButton = (ImageButton) super.getmRootView().findViewById(R.id.buttonFav);

            proposalHow.setText(currentProposal.getHowProp());
            proposalMoney.setText(currentProposal.getMoneyProp());

            likeButton.setText(" " + currentProposal.getNumLikes() + " ");
            dislikeButton.setText(" " + currentProposal.getNumDislikes() + " ");
            notUnderstoodButton.setText(" " + currentProposal.getNumNotUnderstoods() + " ");
            commentButton.setText(" " + currentProposal.getNumComments() + " ");

            if (currentProposal.isFavorite()) {
                favButton.setTag(true);
                favButton.setImageResource(R.mipmap.ic_starfav_yellow);
            } else {
                favButton.setTag(false);
                favButton.setImageResource(R.mipmap.ic_starfav_black);
            }

        } else {

            TextView proposalHow = (TextView) super.getmRootView().findViewById(R.id.howProposal);
            TextView proposalMoney = (TextView) super.getmRootView().findViewById(R.id.moneyProposal);

            Button editPropHowButton = (Button) super.getmRootView().findViewById(R.id.buttonEditPropHow);
            Button editPropCostButton = (Button) super.getmRootView().findViewById(R.id.buttonEditPropCost);

            Button savePropButton = (Button) super.getmRootView().findViewById(R.id.buttonSaveProp);

/*            if(currentProposal.getUserID() != User.ID_USER) {
                savePropButton.setVisibility(View.GONE);
            } else {
                savePropButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateProposal();
                    }
                });
            }*/

            //Start session as admin and add current user to the list of participants of the wave
            AddWaveParticipantClass add = new AddWaveParticipantClass(currentProposal.getIdWave(), super.getmContext());

            editPropHowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(PostProposalContent.super.getmContext(), EditWaveActivity.class);

                    in.putExtra("MODEL_ID", currentProposal.getIdWave());
                    in.putExtra("PAD_NAME", "padHow");
                    PostProposalContent.super.getmContext().startActivity(in);
                }
            });

            editPropCostButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(PostProposalContent.super.getmContext(), EditWaveActivity.class);

                    in.putExtra("MODEL_ID", currentProposal.getIdWave());
                    in.putExtra("PAD_NAME", "padCost");
                    PostProposalContent.super.getmContext().startActivity(in);
                }
            });

            if(!currentProposal.getHowProp().toString().equalsIgnoreCase("null")) {
                proposalHow.setText(currentProposal.getHowProp());
                editPropHowButton.setVisibility(View.GONE);
            }

            if(!currentProposal.getMoneyProp().toString().equalsIgnoreCase("null")){
                proposalMoney.setText(currentProposal.getMoneyProp());
                editPropCostButton.setVisibility(View.GONE);
            }
        }
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

                String idWave = s.getString(TAG_PROPOSAL_WAVE);
                String favorite = s.getString(TAG_PROPOSAL_FAVORITE);

                boolean isCollaborative = (idWave.length() > 0);

                currentProposal = new Proposal(id_prop, isCollaborative, title, category, date, user, image, text, how, cost, likes, dislikes, num_comments, not_understood, views, idWave);
                currentProposal.setFavorite(favorite.equalsIgnoreCase("yes"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
