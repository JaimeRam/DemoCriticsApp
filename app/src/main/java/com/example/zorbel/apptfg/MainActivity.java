package com.example.zorbel.apptfg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Button;

import com.example.zorbel.apptfg.categories.CategoriesListActivity;
import com.example.zorbel.apptfg.collaborate.CollaborativeProposalsActivity;
import com.example.zorbel.apptfg.programs.ProgramsActivity;
import com.example.zorbel.apptfg.proposals.ProposalsActivity;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.GetPoliticalParties;
import com.example.zorbel.service_connection.GetUser;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends MenuActivity {

    public static String SERVER = "https://apptfg-servicerest.rhcloud.com";
    private Button btnPrograms;
    private Button btnComparatives;
    private Button btnProposals;
    private Button btnPolls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        if(super.isNetworkAvailable()) {
            this.getUserData();
            if (PoliticalGroups.getInstance().getMlistOfPoliticalParties() == null)
                getPoliticalPartiesData();
        }

        super.setMenus(findViewById(R.id.drawer_layout), 1);


        btnPrograms = (Button) findViewById(R.id.btn_Programs);
        btnPrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, ProgramsActivity.class);
                startActivity(in);
            }
        });

        btnComparatives = (Button) findViewById(R.id.btn_Comparatives);
        btnComparatives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, CategoriesListActivity.class);
                startActivity(in);
            }
        });

        btnProposals = (Button) findViewById(R.id.btn_Proposals);
        btnProposals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, ProposalsActivity.class);
                in.putExtra("FocusTab", 2);
                startActivity(in);
            }
        });

        btnPolls = (Button) findViewById(R.id.btn_Polls);
        btnPolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, CollaborativeProposalsActivity.class);
                in.putExtra("FocusTab", 0);
                startActivity(in);
            }
        });

    }

    private void getPoliticalPartiesData() {

        URL link;
        try {
            link = new URL(SERVER + "/politicalParty");
            GetPoliticalParties task = new GetPoliticalParties(this, findViewById(R.id.activityPartiesLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void getUserData() {

        User.ID_USER = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

        URL link;
        try {
            link = new URL(SERVER + "/user/" + User.ID_USER);
            GetUser task = new GetUser(this, findViewById(R.id.drawer_layout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}