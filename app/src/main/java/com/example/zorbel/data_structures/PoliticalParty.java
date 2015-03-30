package com.example.zorbel.data_structures;

import java.sql.Blob;
import java.util.HashMap;
import java.util.List;

public class PoliticalParty {

    private int mId;
    private String mName;
    private Blob mLogo;
    private Section mSectionRoot;

    public PoliticalParty(int mId, String mName, Blob mLogo, Section mSectionRoot) {
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

    public Blob getmLogo() {
        return mLogo;
    }

    public Section getmSectionRoot() {
        return mSectionRoot;
    }

    public void setmSectionRoot(Section root) {
        this.mSectionRoot = root;
    }
}
