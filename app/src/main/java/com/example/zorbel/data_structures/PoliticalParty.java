package com.example.zorbel.data_structures;

import android.graphics.Bitmap;

public class PoliticalParty implements Comparable<Integer> {

    private Integer mId;
    private String mName;
    private Bitmap mLogo;
    private Section mSectionRoot;

    public PoliticalParty(int mId, String mName, Bitmap mLogo, Section mSectionRoot) {
        this.mId = mId;
        this.mName = mName;
        this.mLogo = mLogo;
        this.mSectionRoot = mSectionRoot;
    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public Bitmap getmLogo() {
        return mLogo;
    }

    public Section getmSectionRoot() {
        return mSectionRoot;
    }

    public void setmSectionRoot(Section root) {
        this.mSectionRoot = root;
    }

    @Override
    public int compareTo(Integer another) {
        return mId.compareTo(another);
    }
}
