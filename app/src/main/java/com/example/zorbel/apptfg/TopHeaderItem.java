package com.example.zorbel.apptfg;


public class TopHeaderItem implements TopItem{

    private String headerType;
    private String title;
    private int iconId;

    public TopHeaderItem(String headerType, String title, int iconId) {
        this.headerType = headerType;
        this.title = title;
        this.iconId = iconId;
    }

    public String getHeaderType() {
        return headerType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    @Override
    public boolean isSection() {
        return false;
    }
}
