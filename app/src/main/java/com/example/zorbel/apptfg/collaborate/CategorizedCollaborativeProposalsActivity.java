package com.example.zorbel.apptfg.collaborate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.SampleFragmentPagerAdapter;
import com.example.zorbel.tab_layout.SlidingTabLayout;

public class CategorizedCollaborativeProposalsActivity extends MenuActivity {

    private final int INFTYPE = 4; //For collaborative proposals
    private int categoryId;

    public static final String ARG_CATEGORY = "ARG_CATEGORY";
    public static final String ARG_CATEGORYLOGO = "ARG_CATEGORYLOGO";
    public static final String ARG_ID_CATEGORY = "ARG_ID_CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorized_collaborative_proposals);

        String title = getIntent().getExtras().getString(ARG_CATEGORY);
        int photoRes = getIntent().getExtras().getInt(ARG_CATEGORYLOGO);
        categoryId = getIntent().getExtras().getInt(ARG_ID_CATEGORY);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1919")));

        //Set the icon category for the action bar
        super.getSupportActionBar().setIcon(photoRes);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Set the title category for the action bar
        super.getSupportActionBar().setTitle(title);


        // CONFIGURE THE TABS //

        //Titles of Tabs
        String[] tabTitles = getResources().getStringArray(R.array.TabsCategorizedCollaborativeProposalsEntries);

        //Num of Tabs
        int numT = tabTitles.length;

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                CategorizedCollaborativeProposalsActivity.this, tabTitles, numT, INFTYPE, categoryId));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }

}
