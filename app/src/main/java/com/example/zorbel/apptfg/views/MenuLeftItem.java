package com.example.zorbel.apptfg.views;

import android.graphics.drawable.Drawable;

public class MenuLeftItem {

    private String name;
    private Drawable image;

    public MenuLeftItem(String name) {

        this.name = name;
    }

    public MenuLeftItem(String name, Drawable image) {

        this.name = name;
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
