package com.example.mytest.Model;

import android.net.Uri;

public class ItemModel {

    private String title;
    private String imageRes;
    private boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
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