package com.example.zorbel.data_structures;

public class Comment {

    private String user;
    private String date;
    private String text;

    public Comment(String user, String date, String text) {
        this.user = user;
        this.date = date;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
