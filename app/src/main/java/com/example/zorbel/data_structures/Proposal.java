package com.example.zorbel.data_structures;

import com.example.zorbel.apptfg.views.TopItem;

public class Proposal implements TopItem {

    private int propId;
    private String titleProp;
    private String category; //ID
    private String date;
    private String user; //ID
    private int resLogo;
    private String textProp;
    private String howProp;
    private String moneyProp;

    private int numLikes;
    private int numDislikes;
    private int numNotUnderstoods;
    private int numComments;
    private int numViews;


    public Proposal(int propId, String titleProp, String category, String date, String user,
                    int resLogo, String textProp, String howProp, String moneyProp, int numLikes,
                    int numDislikes, int numComments, int numNotUnderstoods, int numViews) {
        this.propId = propId;
        this.titleProp = titleProp;
        this.category = category;
        this.date = date;
        this.user = user;
        this.resLogo = resLogo;
        this.textProp = textProp;
        this.howProp = howProp;
        this.moneyProp = moneyProp;
        this.numLikes = numLikes;
        this.numDislikes = numDislikes;
        this.numComments = numComments;
        this.numNotUnderstoods = numNotUnderstoods;
        this.numViews = numViews;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public boolean isProposal() {
        return true;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public String getTitleProp() {
        return titleProp;
    }

    public void setTitleProp(String title) {
        this.titleProp = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getResLogo() {
        return resLogo;
    }

    public void setResLogo(int resLogo) {
        this.resLogo = resLogo;
    }

    public String getTextProp() {
        return textProp;
    }

    public void setTextProp(String textProp) {
        this.textProp = textProp;
    }

    public String getHowProp() {
        return howProp;
    }

    public void setHowProp(String howProp) {
        this.howProp = howProp;
    }

    public String getMoneyProp() {
        return moneyProp;
    }

    public void setMoneyProp(String moneyProp) {
        this.moneyProp = moneyProp;
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

    public int getNumViews() {
        return numViews;
    }

    public void setNumViews(int numViews) {
        this.numViews = numViews;
    }
}
