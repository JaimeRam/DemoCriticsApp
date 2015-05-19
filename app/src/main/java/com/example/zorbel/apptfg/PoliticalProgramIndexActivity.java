package com.example.zorbel.apptfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.service.GetProgramsData;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PoliticalProgramIndexActivity extends MenuActivity {

    //List Index
    private ListView mIndexListView;

    private PoliticalParty polParty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_political_program_index);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        final int polIndex = getIntent().getExtras().getInt("PoliticalPartyIndex");

        polParty = PoliticalGroups.getInstance().getMlistOfPoliticalParties().get(polIndex);

        ImageView logo = (ImageView) findViewById(R.id.partyLogoIndex);
        logo.setImageBitmap(polParty.getmLogo());

        TextView partyName = (TextView) findViewById(R.id.partyNameIndex);
        partyName.setText(polParty.getmName());

        mIndexListView = (ListView) findViewById(R.id.indexListView);

        if (polParty.getmSectionRoot() == null) {
            getProgramSectionsData(polParty.getmId(), polIndex);
        } else {
            List<Section> index = PoliticalGroups.getInstance().getMlistOfPoliticalParties().get(polIndex).getmSectionRoot().getlSections();

            mIndexListView.setAdapter(new ListIndexAdapter(this, index));
        }

        mIndexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(PoliticalProgramIndexActivity.this, SectionViewerActivity.class);

                Section sec = (Section) parent.getItemAtPosition(position);
                int section_id = sec.getmSection();

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyIndex", polIndex);
                b.putInt("SectionId", section_id);

                in.putExtras(b);

                startActivity(in);

            }
        });

    }

    private void getProgramSectionsData(int id, int index) {
        URL link = null;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty/" + id + "/section");
            GetProgramsData task = new GetProgramsData(this, findViewById(R.id.activityPoliticalProgramIndexLayout), id, index);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
