package com.example.zorbel.data_structures;

import java.util.List;

public class Section {

    private int mSection;
    private int mPoliticalParty;
    private String mTitle;
    private String mText;

    public void setlSections(List<Section> lSections) {
        this.lSections = lSections;
    }

    private List<Section> lSections;

    public Section(int mSection, int mPoliticalParty, String mTitle, String mText, List<Section> lSections) {
        this.mSection = mSection;
        this.mPoliticalParty = mPoliticalParty;
        this.mTitle = mTitle;
        this.mText = mText;
        this.lSections = lSections;
    }

    public int getmSection() { return mSection; }

    public String getmTitle() {
        return mTitle;
    }

    public String getmText() {
        return mText;
    }

    public List<Section> getlSections() {
        return lSections;
    }

    public void addSubSection (Section subSec) {
        this.lSections.add(subSec);
    }
}