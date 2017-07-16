package com.dipakkr.github.giffer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/30/17.
 */



public class Celebrity {

    @SerializedName("profile_path")
    private String mImagePath;

    public Celebrity(String mImagePath) {
        this.mImagePath = mImagePath;

    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }
}


