package com.example.zorbel.apptfg.categories;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.SampleFragmentPagerAdapter;
import com.example.zorbel.tab_layout.SlidingTabLayout;


public class CategoryActivity extends MenuActivity {

    public static final String ARG_CATEGORY = "ARG_CATEGORY";
    public static final String ARG_ID_CATEGORY = "ARG_ID_CATEGORY";
    public static final String ARG_CATEGORYLOGO = "ARG_CATEGORYLOGO";
    private final int INFTYPE = 3; //For categories

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String title = getIntent().getExtras().getString(ARG_CATEGORY);
        int photoRes = getIntent().getExtras().getInt(ARG_CATEGORYLOGO);
        int id_category = getIntent().getExtras().getInt(ARG_CATEGORYLOGO);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        //Set the icon category for the action bar
        super.getSupportActionBar().setIcon(photoRes);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Set the title category for the action bar
        super.getSupportActionBar().setTitle(title);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9900")));


        // CONFIGURE THE TABS //

        //Titles of Tabs
        String[] tabTitles = getResources().getStringArray(R.array.TabsCategoryEntries);

        //Num of Tabs
        int numT = tabTitles.length;

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                CategoryActivity.this, tabTitles, numT, INFTYPE));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }


}
