package com.example.zorbel.data_structures;

import java.util.List;

public class Section {

    private String mSection;
    private String mSectionParent;
    private String mPoliticalParty;
    private String mTitle;
    private String mText;
    private List<String> lSections;

    public Section(String mSection, String mSectionParent, String mPoliticalParty, String mTitle, String mText, List<String> lSections) {
        this.mSection = mSection;
        this.mSectionParent = mSectionParent;
        this.mPoliticalParty = mPoliticalParty;
        this.mTitle = mTitle;
        this.mText = mText;
        this.lSections = lSections;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmText() {
        return mText;
    }

    public List<String> getlSections() {
        return lSections;
    }
}