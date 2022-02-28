package com.example.mytest.Model;

import android.net.Uri;

public class ItemModel {

    private int id;
    private String title;
    private String imageRes;
    private int favoriteStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavoriteStatus() {
        return favoriteStatus;
    }

    public void setFavoriteStatus(int favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getImageRes() {
        return imageRes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }
}