package com.example.zorbel.apptfg;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.service.GetPoliticalParties;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends MenuActivity {

    //Necessary for the tabs
    private FragmentTabHost mTabHost;

    private String titleTab1;
    private String titleTab2;

    public static String SERVER = "https://apptfg-servicerest.rhcloud.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.setMenus(findViewById(R.id.drawer_layout),1);

        if (PoliticalGroups.getInstance().getMlistOfPoliticalParties() == null)
            getPoliticalPartiesData();

        //TABS

        titleTab1 = getString(R.string.titleTab1);
        titleTab2 = getString(R.string.titleTab2);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec(titleTab1).setIndicator(titleTab1, null),
                ProgramsFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec(titleTab2).setIndicator(titleTab2, null),
                ProposalsFragment.class, null);

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


}