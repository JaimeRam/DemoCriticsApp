package com.example.zorbel.apptfg.programs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.PartyWidgetAdapter;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.service_connection.GetPoliticalParties;

import java.net.MalformedURLException;
import java.net.URL;

public class PartiesActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parties);

        super.setMenus(findViewById(R.id.drawer_layout), 2);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        if (PoliticalGroups.getInstance().getMlistOfPoliticalParties() == null) {

            getPoliticalPartiesData();

        } else {

            gridview.setAdapter(new PartyWidgetAdapter(this, PoliticalGroups.getInstance().getMlistOfPoliticalParties()));

        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                Intent in = new Intent(PartiesActivity.this, PoliticalProgramIndexActivity.class);
                Bundle b = new Bundle();

                b.putInt("PoliticalPartyIndex", position);
                in.putExtras(b);

                startActivity(in);

            }
        });


    }

    private void getPoliticalPartiesData() {
        URL link = null;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty");
            GetPoliticalParties task = new GetPoliticalParties(this, findViewById(R.id.activityPartiesLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }



}
