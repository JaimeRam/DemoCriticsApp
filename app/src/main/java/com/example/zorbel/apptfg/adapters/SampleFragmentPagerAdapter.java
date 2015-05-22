package com.example.zorbel.apptfg.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.zorbel.apptfg.TabPageFragment;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private int pageCount;
    private int infType;
    private String[] tabTitles;
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context, String[] tabTit, int numPag, int infType) {
        super(fm);
        this.context = context;
        this.tabTitles = tabTit;
        this.pageCount = numPag;
        this.infType = infType;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        return TabPageFragment.newInstance(position + 1, infType);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
