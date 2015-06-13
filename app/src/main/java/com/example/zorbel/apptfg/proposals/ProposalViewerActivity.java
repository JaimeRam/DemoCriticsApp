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
import com.example.zorbel.apptfg.collaborate.EditWaveActivity;
import com.example.zorbel.service_connection.GetProposalContent;
import com.example.zorbel.service_connection.PutOpinion;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.net.MalformedURLException;
import java.net.URL;

public class ProposalViewerActivity extends MenuActivity {

    private ImageButton favButton;

    private Button commentButton;
    private Button likeButton;
    private Button notUnderstoodButton;
    private Button dislikeButton;

    private Button editHowButton;
    private Button editCostButton;

    private boolean isEditable;

    private int proposalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_viewer);

        proposalId = getIntent().getExtras().getInt("ProposalId");
        isEditable = getIntent().getExtras().getBoolean("isEditable");

        commentButton = (Button) findViewById(R.id.buttonComment);
        likeButton = (Button) findViewById(R.id.buttonLike);
        notUnderstoodButton = (Button) findViewById(R.id.buttonNotUnderstood);
        dislikeButton = (Button) findViewById(R.id.buttonDislike);
        favButton = (ImageButton) findViewById(R.id.buttonFav);


        if(!isEditable) { //The proposal is not editable

            if(super.isNetworkAvailable()) {
                getProposalData(proposalId);
            }

            super.getSupportActionBar().setIcon(R.mipmap.ic_proposals);
            super.getSupportActionBar().setDisplayShowHomeEnabled(true);

            super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B800")));

        } else { //The proposal is collaborative and editable

           // TODO: get editable proposal info


            super.getSupportActionBar().setIcon(R.mipmap.ic_polls);
            super.getSupportActionBar().setDisplayShowHomeEnabled(true);

            super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1919")));

            TextView txtOpinion = (TextView) findViewById(R.id.textLabelOpinion);
            txtOpinion.setVisibility(View.GONE);

            favButton.setVisibility(View.GONE);

            commentButton.setVisibility(View.GONE);
            likeButton.setVisibility(View.GONE);
            notUnderstoodButton.setVisibility(View.GONE);
            dislikeButton.setVisibility(View.GONE);
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

                String url = "/proposal/" + proposalId + "/like";
                putProposalOpinion(url);
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String url = "/proposal/" + proposalId + "/dislike";
                putProposalOpinion(url);

            }
        });

        notUnderstoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String url = "/proposal/" + proposalId +  "/notUnderstood";
                putProposalOpinion(url);


            }
        });

        setEditProposalButtonListeners();
    }

    private void putProposalOpinion (String url) {

        URL link = null;
        if (super.isNetworkAvailable()) {
            try {
                link = new URL(MainActivity.SERVER + url);

                PutOpinion task = new PutOpinion(ProposalViewerActivity.this, findViewById(R.id.activityProposalViewerLayout));
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

            GetProposalContent task = new GetProposalContent(this, findViewById(R.id.activityProposalViewerLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void setEditProposalButtonListeners() {

        editHowButton = (Button) findViewById(R.id.buttonEditPropHow);
        editCostButton = (Button) findViewById(R.id.buttonEditPropCost);

        editHowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(ProposalViewerActivity.this, EditWaveActivity.class);
                startActivity(in);

            }
        });


        editCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(ProposalViewerActivity.this, EditWaveActivity.class);
                startActivity(in);

            }
        });

    }


}
