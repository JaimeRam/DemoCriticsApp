package com.example.zorbel.data_structures;

import java.util.List;

public class Section {

    private int mSection;
    private int mPoliticalParty;
    private String mTitle;
    private String mText;

    private int numLikes;
    private int numDislikes;
    private int numNotUnderstoods;
    private int numComments;

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

    public int getmPoliticalParty() {return mPoliticalParty; }

    public String getmText() {
        return mText;
    }

    public List<Section> getlSections() {
        return lSections;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumDislikes() {
        return numDislikes;
    }

    public void setNumDislikes(int numDislikes) {
        this.numDislikes = numDislikes;
    }

    public int getNumNotUnderstoods() {
        return numNotUnderstoods;
    }

    public void setNumNotUnderstoods(int numNotUnderstoods) {
        this.numNotUnderstoods = numNotUnderstoods;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public void addSubSection (Section subSec) {
        this.lSections.add(subSec);
    }
}