package com.example.zorbel.data_structures;

import java.sql.Blob;
import java.util.HashMap;
import java.util.List;

public class PoliticalParty {

    private int mId;
    private String mName;
    private Blob mLogo;
    private List<Section> mListSections;

    public PoliticalParty(int mId, String mName, Blob mLogo, List<Section> mListSections) {
        this.mId = mId;
        this.mName = mName;
        this.mLogo = mLogo;
        this.mListSections = mListSections;
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

    public List<Section> getmListSections() {
        return mListSections;
    }
}
