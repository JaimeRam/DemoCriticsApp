package com.example.zorbel.apptfg;

import android.content.Intent;
import android.os.Bundle;
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

    public static String SERVER = "https://apptfg-servicerest.rhcloud.com";
    String a = "kk";
    private ListView topIndexListView;
    private Button btnPrograms;
    private Button btnComparatives;
    private Button btnProposals;
    private Button btnPolls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        super.setMenus(findViewById(R.id.drawer_layout), 1);

        if (PoliticalGroups.getInstance().getMlistOfPoliticalParties() == null)
            getPoliticalPartiesData();

        //setListListeners();

        //getTop3Ranking();

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

    private void setListListeners() {

        //set more Views header

        topIndexListView = (ListView) findViewById(R.id.topIndexListView);

        topIndexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TopItem it = (TopItem) parent.getItemAtPosition(position);

                if (it != null) {

                    if (it.isSection()) {

                        // TODO: launch the SectionViewerActivity

                        Section sec = (Section) it;

                        PoliticalParty pol = PoliticalGroups.getInstance().getPoliticalParty(sec.getmPoliticalParty());

                        if (pol.getmSectionRoot() == null) {
                            //getProgramSectionsData();
                        }

                    } else {

                        TopHeaderItem header = (TopHeaderItem) it;

                        Intent in = new Intent(MainActivity.this, Top10Activity.class);

                        Bundle b = new Bundle();
                        b.putString("TopURL", header.getHeaderType());

                        in.putExtras(b);

                        startActivity(in);

                    }
                }
            }
        });

    }

    private void getPoliticalPartiesData() {
        URL link = null;
        try {
            link = new URL (SERVER + "/politicalParty");
            GetPoliticalParties task = new GetPoliticalParties(this, findViewById(R.id.activityPartiesLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void getTop3Ranking() {
        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/top");

            GetTopIndex task = new GetTopIndex(MainActivity.this, findViewById(R.id.layoutHome));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


}