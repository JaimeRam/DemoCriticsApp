package com.example.zorbel.apptfg.programs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.apptfg.FavoritesActivity;
import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.ExpandableIndexAdapter;
import com.example.zorbel.apptfg.adapters.MenuLeftListAdapter;
import com.example.zorbel.apptfg.categories.CategoriesListActivity;
import com.example.zorbel.apptfg.collaborate.CollaborativeProposalsActivity;
import com.example.zorbel.apptfg.proposals.ProposalsActivity;
import com.example.zorbel.apptfg.views.MenuLeftItem;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.GetProgramsData;
import com.example.zorbel.service_connection.PostFavorite;
import com.example.zorbel.service_connection.PostSectionContent;
import com.example.zorbel.service_connection.PutOpinion;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
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
    private WebView sectionText;

    private ImageButton favButton;

    private Button commentButton;
    private Button likeButton;
    private Button notUnderstoodButton;
    private Button dislikeButton;

    private int politicalPartyId;
    private int sectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_viewer);

        sectionTitle = (TextView) findViewById(R.id.sectionTitle);
        sectionText = (WebView) findViewById(R.id.textSection);

        commentButton = (Button) findViewById(R.id.buttonComment);
        likeButton = (Button) findViewById(R.id.buttonLike);
        notUnderstoodButton = (Button) findViewById(R.id.buttonNotUnderstood);
        dislikeButton = (Button) findViewById(R.id.buttonDislike);
        favButton = (ImageButton) findViewById(R.id.buttonFav);

        mIndexListView = (ListView) findViewById(R.id.indexListView);

        politicalPartyId = getIntent().getExtras().getInt("PoliticalPartyId");
        sectionId = getIntent().getExtras().getInt("SectionId");


        PoliticalParty pol = PoliticalGroups.getInstance().getPoliticalParty(politicalPartyId);

        setMenus();

        if (pol.getmSectionRoot() == null) {

            if(isNetworkAvailable())
                getProgramSectionsData(politicalPartyId, sectionId);

        } else {

            if(isNetworkAvailable()) {
                getSectionContentData(sectionId, politicalPartyId);
                createRightIndex();
            }
        }

        mIndexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(SectionViewerActivity.this, SectionViewerActivity.class);

                Section sec = (Section) parent.getItemAtPosition(position);
                int section_id = sec.getmSection();

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyId", politicalPartyId);
                b.putInt("SectionId", section_id);

                in.putExtras(b);
                startActivity(in);
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SectionViewerActivity.this, CommentsSectionActivity.class);

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyId", politicalPartyId);
                b.putInt("SectionId", sectionId);

                i.putExtras(b);
                startActivity(i);
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                putOpinion("like");
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                putOpinion("dislike");
            }
        });

        notUnderstoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                putOpinion("not_understood");
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (favButton.getTag() == true) {
                    favButton.setImageResource(R.mipmap.ic_starfav_black);
                    favButton.setTag(false);
                } else {
                    favButton.setImageResource(R.mipmap.ic_starfav_yellow);
                    favButton.setTag(true);
                }

                URL link;
                try {
                    link = new URL(MainActivity.SERVER + "/favorite");

                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id_user", User.ID_USER));
                    params.add(new BasicNameValuePair("id_political_party", Integer.toString(politicalPartyId)));
                    params.add(new BasicNameValuePair("section", Integer.toString(sectionId)));

                    PostFavorite task = new PostFavorite(SectionViewerActivity.this, params, findViewById(R.id.activitySectionViewerLayout));
                    task.execute(link);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4775FF")));

    }

    public void setMenus() {

        //MENU LEFT NAV DRAWER

        tagTitles = getResources().getStringArray(R.array.MenuEntries);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerListLeft = (ListView) findViewById(R.id.left_drawer);

        drawerListRight = (ExpandableListView) findViewById(R.id.right_drawer);

        //ListView Header
        View header = getLayoutInflater().inflate(R.layout.menu_left_header, drawerListLeft, false);
        drawerListLeft.addHeaderView(header);

        //New list of drawer items
        ArrayList<MenuLeftItem> items = new ArrayList<MenuLeftItem>();
        items.add(new MenuLeftItem(tagTitles[0], getResources().getDrawable(R.mipmap.ic_home_icon_blue)));
        items.add(new MenuLeftItem(tagTitles[1], getResources().getDrawable(R.mipmap.ic_programs)));
        items.add(new MenuLeftItem(tagTitles[2], getResources().getDrawable(R.mipmap.ic_topics)));
        items.add(new MenuLeftItem(tagTitles[3], getResources().getDrawable(R.mipmap.ic_proposals)));
        items.add(new MenuLeftItem(tagTitles[4], getResources().getDrawable(R.mipmap.ic_collaborate)));
        items.add(new MenuLeftItem(tagTitles[5], getResources().getDrawable(R.mipmap.ic_starfav_yellow)));


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

        List<Section> headers = PoliticalGroups.getInstance().getPoliticalParty(politicalPartyId).getmSectionRoot().getlSections();
        HashMap<Section, List<Section>> listDataChild = generateSubSections(headers);

        mRightIndexAdapter = new ExpandableIndexAdapter(this, headers, listDataChild);
        drawerListRight.setAdapter(mRightIndexAdapter);

        drawerListRight.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Intent in = new Intent(SectionViewerActivity.this, SectionViewerActivity.class);

                ExpandableIndexAdapter adapter = (ExpandableIndexAdapter) parent.getExpandableListAdapter();
                Section sec = (Section) adapter.getGroup(groupPosition);
                int section_id = sec.getmSection();

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyId", politicalPartyId);
                b.putInt("SectionId", section_id);

                in.putExtras(b);

                startActivity(in);

                return true;
            }
        });

        drawerListRight.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent in = new Intent(SectionViewerActivity.this, SectionViewerActivity.class);

                ExpandableIndexAdapter adapter = (ExpandableIndexAdapter) parent.getExpandableListAdapter();
                Section sec = (Section) adapter.getChild(groupPosition, childPosition);
                int section_id = sec.getmSection();

                Bundle b = new Bundle();
                b.putInt("PoliticalPartyId", politicalPartyId);
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

        MenuItem itemParty = menu.add("");
        itemParty.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); // ShowAsAction
        itemParty.setIcon(new BitmapDrawable(getResources(), PoliticalGroups.getInstance().getPoliticalParty(politicalPartyId).getmLogo())); // Icon
        itemParty.setEnabled(false);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_section_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


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

        if (position == 1) { //Home Menu Option

            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);

        } else if (position == 2) {  //Political Parties Menu Option

            Intent in = new Intent(this, ProgramsActivity.class);
            startActivity(in);

        } else if (position == 3) {  //Comparatives Menu Option

            Intent in = new Intent(this, CategoriesListActivity.class);
            startActivity(in);

        } else if (position == 4) {  //Proposals Menu Option

            Intent in = new Intent(this, ProposalsActivity.class);
            in.putExtra("FocusTab", 2);
            startActivity(in);

        } else if (position == 5) {  //Collaborative Proposals Menu Option

            Intent in = new Intent(this, CollaborativeProposalsActivity.class);
            in.putExtra("FocusTab", 2);
            startActivity(in);

        } else if (position == 6) {  //Favourites Menu Option

            Intent in = new Intent(this, FavoritesActivity.class);
            startActivity(in);

        }

        drawerListLeft.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerListLeft);
    }

    private void getSectionContentData(int id_section, int id_politicalParty) {
        URL link = null;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty/" + id_politicalParty + "/section/" + id_section);

            //Prepare post arguments
            //String parameters = "section=" + URLEncoder.encode(Integer.toString(id_section), "UTF-8") + "&id_political_party=" + URLEncoder.encode(Integer.toString(id_politicalParty), "UTF-8");

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_user", User.ID_USER));

            PostSectionContent task = new PostSectionContent(this, params, findViewById(R.id.activitySectionViewerLayout), id_politicalParty);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void getProgramSectionsData(int id_pol, int id_sec) {
        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty/" + id_pol + "/section");
            GetProgramsData task = new GetProgramsData(this, findViewById(R.id.activitySectionViewerLayout), id_pol, id_sec);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void putOpinion(String sOpinon) {

        URL link;

        if(isNetworkAvailable()) {
            try {
                link = new URL(MainActivity.SERVER);

                ArrayList<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("id_user", User.ID_USER));
                params.add(new BasicNameValuePair("id_political_party", Integer.toString(politicalPartyId)));
                params.add(new BasicNameValuePair("section", Integer.toString(sectionId)));
                params.add(new BasicNameValuePair("opinion", sOpinon));

                PutOpinion task = new PutOpinion(SectionViewerActivity.this, params, findViewById(R.id.activitySectionViewerLayout));
                task.execute(link);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnectedOrConnecting()) {

            AlertDialog dialog = new AlertDialog.Builder(SectionViewerActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                    .setTitle(getResources().getString(R.string.title_dialogNoConnection))
                    .setMessage(getResources().getString(R.string.text_dialogNoConnection))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(R.mipmap.ic_action_person)
                    .show();

        }

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
