package com.example.zorbel.data_structures;

import java.util.List;

public class Section {

    private String mSection;
    private String mPoliticalParty;
    private String mTitle;
    private String mText;
    private List<Section> lSections;

    public Section(String mSection, String mPoliticalParty, String mTitle, String mText, List<Section> lSections) {
        this.mSection = mSection;
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

    public List<Section> getlSections() {
        return lSections;
    }
}