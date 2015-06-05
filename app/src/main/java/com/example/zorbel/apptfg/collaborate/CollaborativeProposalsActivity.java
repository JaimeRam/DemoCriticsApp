package com.example.zorbel.apptfg.collaborate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.SampleFragmentPagerAdapter;
import com.example.zorbel.tab_layout.SlidingTabLayout;

public class CollaborativeProposalsActivity extends MenuActivity {

    private final int INFTYPE = 4; //For collaborative proposals

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaborative_proposals);

        super.setMenus(findViewById(R.id.drawer_layout), 5);

        super.getSupportActionBar().setIcon(R.mipmap.ic_polls);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1919")));


        // CONFIGURE THE TABS //

        //Titles of Tabs
        String[] tabTitles = getResources().getStringArray(R.array.TabsCollaborateEntries);

        //Num of Tabs
        int numT = tabTitles.length;

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                CollaborativeProposalsActivity.this, tabTitles, numT, INFTYPE, 0));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        int focusTab = getIntent().getExtras().getInt("FocusTab");
        viewPager.setCurrentItem(focusTab); // Latest Proposals Tab
    }


}
