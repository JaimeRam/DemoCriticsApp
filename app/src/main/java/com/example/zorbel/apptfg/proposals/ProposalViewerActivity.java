package com.example.zorbel.apptfg.proposals;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.programs.CommentsSectionActivity;
import com.example.zorbel.service_connection.GetProposalContent;
import com.example.zorbel.service_connection.GetSectionContent;

import java.net.MalformedURLException;
import java.net.URL;

public class ProposalViewerActivity extends MenuActivity {

    private Button commentButton;
    private Button likeButton;
    private Button notUnderstoodButton;
    private Button dislikeButton;

    private int proposalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_viewer);

        proposalId = getIntent().getExtras().getInt("ProposalId");

        getProposalData(proposalId);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        super.getSupportActionBar().setIcon(R.mipmap.ic_proposals);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B800")));

        commentButton = (Button) findViewById(R.id.buttonComment);
        likeButton = (Button) findViewById(R.id.buttonLike);
        notUnderstoodButton = (Button) findViewById(R.id.buttonNotUnderstood);
        dislikeButton = (Button) findViewById(R.id.buttonDislike);

        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(ProposalViewerActivity.this, CommentsProposalActivity.class);
                startActivity(i);
            }
        });
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


}
