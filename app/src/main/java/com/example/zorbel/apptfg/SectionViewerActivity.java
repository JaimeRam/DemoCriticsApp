package com.example.zorbel.apptfg;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.service.GetProgramsData;
import com.example.zorbel.service.GetSectionContent;

import org.w3c.dom.Comment;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SectionViewerActivity extends ActionBarActivity {

    //Left Menu
    private ListView drawerListLeft;
    private String[] tagTitles;

    //Right Menu Index
    private ExpandableListView drawerListRight;
    private ExpandableIndexAdapter mRightIndexAdapter;

    //Nav Drawer menus
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    //Index of subsections
    private ListView mIndexListView;


    private TextView sectionTitle;
    private TextView sectionText;

    private Button commentButton;
    private Button likeButton;
    private Button notUnderstoodButton;
    private Button dislikeButton;

    private Section currentSection;
    private int politicalPartyGroupIndex;
    private int sectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_viewer);

        sectionTitle = (TextView) findViewById(R.id.sectionTitle);
        sectionText = (TextView) findViewById(R.id.textSection);

        commentButton = (Button) findViewById(R.id.buttonComment);
        likeButton = (Button) findViewById(R.id.buttonLike);
        notUnderstoodButton = (Button) findViewById(R.id.buttonNotUnderstood);
        dislikeButton  = (Button) findViewById(R.id.buttonDislike);

        mIndexListView =(ListView) findViewById(R.id.indexListView);

        politicalPartyGroupIndex = getIntent().getExtras().getInt("PoliticalPartyIndex");
        sectionId = getIntent().getExtras().getInt("SectionId");

        currentSection = PoliticalGroups.getInstance().getSection(politicalPartyGroupIndex, sectionId);

        //if(currentSection.getmText() == null) { //TODO: check the condition (what if the section doesn't has text?)

            getSectionContentData(currentSection.getmSection(), currentSection.getmPoliticalParty(), politicalPartyGroupIndex);

       /* } else { //The section info has been already retrieved from the server

            sectionTitle.setText(currentSection.getmTitle());
            sectionText.setText(currentSection.getmText());

            likeButton.setText(getString(R.string.name_buttonLike) + "/n" + "(" + currentSection.getNumLikes() + ")");
            dislikeButton.setText(getString(R.string.name_buttonDislike) + "/n" + "(" + currentSection.getNumDislikes() + ")");
            notUnderstoodButton.setText(getString(R.string.name_buttonNotUnderstood) + "/n" + "(" + currentSection.getNumNotUnderstoods() + ")");
            commentButton.setText(getString(R.string.name_buttonComment) + "/n" + "(" + currentSection.getNumComments() + ")");

            mIndexListView.setAdapter(new ListIndexAdapter(this,currentSection.getlSections()));

        }*/

        mIndexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(SectionViewerActivity.this, SectionViewerActivity.class);

                Section sec = (Section) parent.getItemAtPosition(position);
                int section_id = sec.getmSection();

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyIndex", politicalPartyGroupIndex);
                b.putInt("SectionId", section_id);

                in.putExtras(b);
                startActivity(in);
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SectionViewerActivity.this, CommentActivity.class);

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyIndex", currentSection.getmPoliticalParty());
                b.putInt("SectionId", sectionId);

                i.putExtras(b);
                startActivity(i);
            }
        });

        setMenus();

        createRightIndex();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void setMenus() {

        //MENU LEFT NAV DRAWER

        tagTitles = getResources().getStringArray(R.array.MenuEntries);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerListLeft = (ListView) findViewById(R.id.left_drawer);

        drawerListRight = (ExpandableListView) findViewById(R.id.right_drawer);

        //Fill the index right menu
        //getIndexTitles(0);

        //Nueva lista de drawer items
        ArrayList<MenuLeftItem> items = new ArrayList<MenuLeftItem>();
        items.add(new MenuLeftItem(tagTitles[0]));
        items.add(new MenuLeftItem(tagTitles[1]));
        items.add(new MenuLeftItem(tagTitles[2]));
        items.add(new MenuLeftItem(tagTitles[3]));


        drawerListLeft.setAdapter(new MenuLeftListAdapter(this, items));

        drawerListLeft.setOnItemClickListener(new DrawerItemClickListener());


        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                //R.mipmap.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {

                getSupportActionBar().setTitle(getString(R.string.app_name));
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }

            public void onDrawerOpened(View drawerView) {

                if (drawerLayout.isDrawerVisible(Gravity.END)) {
                    getSupportActionBar().setTitle(getString(R.string.titleIndex));
                } else {
                    getSupportActionBar().setTitle(getString(R.string.titleMenu));
                }
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);


    }

    public void createRightIndex() {

        List<Section> headers = PoliticalGroups.getInstance().getMlistOfPoliticalParties().get(politicalPartyGroupIndex).getmSectionRoot().getlSections();
        HashMap<Section, List<Section>> listDataChild = generateSubSections(headers);

        mRightIndexAdapter = new ExpandableIndexAdapter(this, headers, listDataChild);
        drawerListRight.setAdapter(mRightIndexAdapter);

        drawerListRight.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                 //TODO: launch the new SectionViewer Activity

                Intent in = new Intent(SectionViewerActivity.this, SectionViewerActivity.class);

                ExpandableIndexAdapter adapter = (ExpandableIndexAdapter) parent.getExpandableListAdapter();
                Section sec = (Section) adapter.getGroup(groupPosition);
                int section_id = sec.getmSection();

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyIndex", politicalPartyGroupIndex);
                b.putInt("SectionId", section_id);

                in.putExtras(b);

                startActivity(in);

                return true;
            }
        });

        drawerListRight.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //TODO: launch the new SectionViewer Activity

                Intent in = new Intent(SectionViewerActivity.this, SectionViewerActivity.class);

                ExpandableIndexAdapter adapter = (ExpandableIndexAdapter) parent.getExpandableListAdapter();
                Section sec = (Section) adapter.getChild(groupPosition, childPosition);
                int section_id = sec.getmSection();

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyIndex", politicalPartyGroupIndex);
                b.putInt("SectionId", section_id);

                in.putExtras(b);

                startActivity(in);

                return true;

            }
        });


    }

    private HashMap<Section, List<Section>> generateSubSections(List<Section> list) {

        HashMap<Section, List<Section>> listSubSections = new HashMap<Section, List<Section>>();

        for (int i = 0; i < list.size(); i++) {

            List<Section> listDataChild = list.get(i).getlSections();
            listSubSections.put(list.get(i), listDataChild);
        }

        return listSubSections;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // If the nav drawer is open, hide action items related to the content view
        for (int i = 0; i < menu.size(); i++) {

            if (drawerLayout.isDrawerOpen(drawerListLeft) || drawerLayout.isDrawerOpen(drawerListRight)) {
                menu.getItem(i).setVisible(false);
            } else {
                menu.getItem(i).setVisible(true);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_section_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.index_action) {
            drawerLayout.openDrawer(Gravity.END);
        }


        if (drawerToggle.onOptionsItemSelected(item)) {

            if (drawerLayout.isDrawerVisible(Gravity.END)) {
                drawerLayout.closeDrawer(Gravity.END);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {

        if (position == 0) {  //Parties Menu

            Intent in = new Intent(this, PartiesActivity.class);
            startActivity(in);

        }

        drawerListLeft.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerListLeft);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void getSectionContentData(int id_section, int id_politicalParty, int index) {
        URL link = null;
        try {
            link = new URL("http://10.0.2.2/ServiceRest/public/politicalParty/" + id_politicalParty + "/section/" + id_section);

            //Prepare post arguments
            //String parameters = "section=" + URLEncoder.encode(Integer.toString(id_section), "UTF-8") + "&id_political_party=" + URLEncoder.encode(Integer.toString(id_politicalParty), "UTF-8");

            GetSectionContent task = new GetSectionContent(this, findViewById(R.id.activitySectionViewerLayout), index);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
