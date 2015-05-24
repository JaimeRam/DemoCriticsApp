package com.example.zorbel.apptfg.programs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.ListIndexAdapter;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.service_connection.GetProgramsData;

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

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4775FF")));

        final int polId = getIntent().getExtras().getInt("PoliticalPartyId");

        polParty = PoliticalGroups.getInstance().getPoliticalParty(polId);

        ImageView logo = (ImageView) findViewById(R.id.partyLogoIndex);
        logo.setImageBitmap(polParty.getmLogo());

        TextView partyName = (TextView) findViewById(R.id.partyNameIndex);
        partyName.setText(polParty.getmName());

        mIndexListView = (ListView) findViewById(R.id.indexListView);

        if (polParty.getmSectionRoot() == null) {
            getProgramSectionsData(polParty.getmId());
        } else {
            List<Section> index = PoliticalGroups.getInstance().getPoliticalParty(polId).getmSectionRoot().getlSections();

            mIndexListView.setAdapter(new ListIndexAdapter(this, index));
        }

        mIndexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(PoliticalProgramIndexActivity.this, SectionViewerActivity.class);

                Section sec = (Section) parent.getItemAtPosition(position);
                int section_id = sec.getmSection();

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyId", polId);
                b.putInt("SectionId", section_id);

                in.putExtras(b);

                startActivity(in);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem itemParty = menu.add("");
        itemParty.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); // ShowAsAction
        itemParty.setIcon(new BitmapDrawable(getResources(), polParty.getmLogo())); // Icon
        itemParty.setEnabled(false);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_political_program_index, menu);
        return true;
    }

    private void getProgramSectionsData(int id) {
        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty/" + id + "/section");
            GetProgramsData task = new GetProgramsData(this, findViewById(R.id.activityPoliticalProgramIndexLayout), id);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
