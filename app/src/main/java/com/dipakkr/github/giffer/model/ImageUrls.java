package com.dipakkr.github.giffer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deepak on 7/16/17.
 */

public class ImageUrls {

    @SerializedName("raw")
    private String raw_url;

    @SerializedName("full")
    private String full_url;

    @SerializedName("regular")
    private String regular_url;

    @SerializedName("small")
    private String small_url ;

    @SerializedName("thumb")
    private String thumb_url;

    public ImageUrls(String raw_url, String full_url, String regular_url, String small_url, String thumb_url) {
        this.raw_url = raw_url;
        this.full_url = full_url;
        this.regular_url = regular_url;
        this.small_url = small_url;
        this.thumb_url = thumb_url;
    }

    public String getRaw_url() {
        return raw_url;
    }

    public String getFull_url() {
        return full_url;
    }

    public String getRegular_url() {
        return regular_url;
    }

    public String getSmall_url() {
        return small_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

}
