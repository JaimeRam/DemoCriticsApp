package com.example.zorbel.apptfg;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.zorbel.apptfg.categories.CategoriesListActivity;
import com.example.zorbel.apptfg.polls.PollsActivity;
import com.example.zorbel.apptfg.programs.ProgramsActivity;
import com.example.zorbel.apptfg.proposals.ProposalsActivity;
import com.example.zorbel.apptfg.views.TopHeaderItem;
import com.example.zorbel.apptfg.views.TopItem;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.service_connection.GetPoliticalParties;
import com.example.zorbel.service_connection.GetTopIndex;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends MenuActivity {

    public static String USER_ID;
    public static String SERVER = "https://apptfg-servicerest.rhcloud.com";
    private Button btnPrograms;
    private Button btnComparatives;
    private Button btnProposals;
    private Button btnPolls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        USER_ID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

        super.setMenus(findViewById(R.id.drawer_layout), 1);

        if (PoliticalGroups.getInstance().getMlistOfPoliticalParties() == null)
            getPoliticalPartiesData();

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

                Intent in = new Intent(MainActivity.this, PollsActivity.class);
                startActivity(in);
            }
        });

    }



    private void getPoliticalPartiesData() {
        URL link;
        try {
            link = new URL (SERVER + "/politicalParty");
            GetPoliticalParties task = new GetPoliticalParties(this, findViewById(R.id.activityPartiesLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}