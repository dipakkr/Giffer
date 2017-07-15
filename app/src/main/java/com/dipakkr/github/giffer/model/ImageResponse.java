package com.dipakkr.github.giffer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by deepak on 7/16/17.
 */

public class ImageResponse {

    @SerializedName("id")
    private String image_id;

    @SerializedName("urls")
    private List<ImageUrls> imageUrlsList;

    public ImageResponse(String image_id, List<ImageUrls> imageUrlsList) {
        this.image_id = image_id;
        this.imageUrlsList = imageUrlsList;
    }

    public String getImage_id() {
        return image_id;
    }

    public List<ImageUrls> getImageUrlsList() {
        return imageUrlsList;
    }
}
