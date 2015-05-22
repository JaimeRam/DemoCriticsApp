package com.example.zorbel.apptfg;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zorbel.apptfg.adapters.SampleFragmentPagerAdapter;
import com.example.zorbel.tab_layout.SlidingTabLayout;


public class FavoritesActivity extends MenuActivity {

    private final int INFTYPE = 4; //For favorites

    public static final String ARG_USER = "ARG_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //int idUser = getIntent().getExtras().getInt(ARG_USER);

        super.setMenus(findViewById(R.id.drawer_layout), 6);

        //Set the icon category for the action bar
        super.getSupportActionBar().setIcon(R.mipmap.ic_starfav_yellow);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CCCC00")));


        // CONFIGURE THE TABS //

        //Titles of Tabs
        String[] tabTitles = getResources().getStringArray(R.array.TabsCategoryEntries);

        //Num of Tabs
        int numT = tabTitles.length;

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                FavoritesActivity.this, tabTitles, numT, INFTYPE));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);


    }


}

