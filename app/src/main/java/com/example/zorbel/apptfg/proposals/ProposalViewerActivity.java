package com.example.zorbel.apptfg.proposals;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.PostFavorite;
import com.example.zorbel.service_connection.PostProposalContent;
import com.example.zorbel.service_connection.PutOpinion;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProposalViewerActivity extends MenuActivity {

    private ImageButton favButton;

    private Button commentButton;
    private Button likeButton;
    private Button notUnderstoodButton;
    private Button dislikeButton;

    private Button editHowButton;
    private Button editCostButton;

    private boolean isCollaborative;

    private int proposalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_viewer);

        proposalId = getIntent().getExtras().getInt("ProposalId");
        isCollaborative = getIntent().getExtras().getBoolean("isEditable");

        commentButton = (Button) findViewById(R.id.buttonComment);
        likeButton = (Button) findViewById(R.id.buttonLike);
        notUnderstoodButton = (Button) findViewById(R.id.buttonNotUnderstood);
        dislikeButton = (Button) findViewById(R.id.buttonDislike);
        favButton = (ImageButton) findViewById(R.id.buttonFav);

        editHowButton = (Button) findViewById(R.id.buttonEditPropHow);
        editCostButton = (Button) findViewById(R.id.buttonEditPropCost);
        favButton = (ImageButton) findViewById(R.id.buttonFav);


        if(!isCollaborative) { //The proposal is not editable

            if(super.isNetworkAvailable()) {
                getProposalData(proposalId);
            }

            super.getSupportActionBar().setIcon(R.mipmap.ic_proposals);
            super.getSupportActionBar().setDisplayShowHomeEnabled(true);

            super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B800")));

        } else { //The proposal is collaborative and editable

            super.getSupportActionBar().setIcon(R.mipmap.ic_collaborate);
            super.getSupportActionBar().setDisplayShowHomeEnabled(true);

            super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1919")));

            TextView txtOpinion = (TextView) findViewById(R.id.textLabelOpinion);
            txtOpinion.setVisibility(View.GONE);

            favButton.setVisibility(View.GONE);

            commentButton.setVisibility(View.GONE);
            likeButton.setVisibility(View.GONE);
            notUnderstoodButton.setVisibility(View.GONE);
            dislikeButton.setVisibility(View.GONE);

            if(super.isNetworkAvailable()) {
                getProposalData(proposalId);
            }


        }

        super.setMenus(findViewById(R.id.drawer_layout), 0);



        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent in = new Intent(ProposalViewerActivity.this, CommentsProposalActivity.class);

                Bundle b = new Bundle();
                b.putInt("ProposalId", proposalId);

                in.putExtras(b);

                startActivity(in);
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                putProposalOpinion("like");
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                putProposalOpinion("dislike");
            }
        });

        notUnderstoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                putProposalOpinion("not_understood");
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (favButton.getTag() == true) {
                    favButton.setImageResource(R.mipmap.ic_starfav_black);
                    favButton.setTag(false);
                } else {
                    favButton.setImageResource(R.mipmap.ic_starfav_yellow);
                    favButton.setTag(true);
                }

                URL link;
                try {
                    link = new URL(MainActivity.SERVER + "/favorite");

                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id_user", User.ID_USER));
                    params.add(new BasicNameValuePair("id_proposal", Integer.toString(proposalId)));

                    PostFavorite task = new PostFavorite(ProposalViewerActivity.this, params, findViewById(R.id.activitySectionViewerLayout));
                    task.execute(link);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void putProposalOpinion(String sOpinon) {

        URL link;
        if (super.isNetworkAvailable()) {
            try {
                link = new URL(MainActivity.SERVER + "/proposal");

                ArrayList<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("id_user", User.ID_USER));
                params.add(new BasicNameValuePair("id_proposal", Integer.toString(proposalId)));
                params.add(new BasicNameValuePair("opinion", sOpinon));

                PutOpinion task = new PutOpinion(ProposalViewerActivity.this, params, findViewById(R.id.activityProposalViewerLayout));
                task.execute(link);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }

    private void getProposalData(int id_proposal) {
        URL link = null;
        try {
            link = new URL(MainActivity.SERVER + "/proposal/" + id_proposal);

            ArrayList<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id_user", User.ID_USER));

            PostProposalContent task = new PostProposalContent(this, params, findViewById(R.id.activityProposalViewerLayout), isCollaborative);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    
}
