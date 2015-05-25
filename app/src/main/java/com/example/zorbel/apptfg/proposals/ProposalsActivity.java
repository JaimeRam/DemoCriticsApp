package com.example.zorbel.apptfg.proposals;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.SampleFragmentPagerAdapter;
import com.example.zorbel.tab_layout.SlidingTabLayout;

public class ProposalsActivity extends MenuActivity {

    final int INFTYPE = 2; //For proposals

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposals);

        super.setMenus(findViewById(R.id.drawer_layout), 4);

        super.getSupportActionBar().setIcon(R.mipmap.ic_proposals);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B800")));

        //Titles of Tabs
        String[] tabTitles = getResources().getStringArray(R.array.TabsProposalsEntries);

        //Num of Tabs
        int numT = tabTitles.length;

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                ProposalsActivity.this, tabTitles, numT, INFTYPE, 0));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }

}
