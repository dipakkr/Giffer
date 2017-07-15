package com.dipakkr.github.giffer.rest;

import com.dipakkr.github.giffer.model.ImageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 7/15/17.
 */

public interface UnsplashInterface {

    @GET("photos")
    Call<List<ImageResponse>> getImageResponse(@Query("client_id") String client_id, @Query("per_page") String page, @Query("order_by") String key);

}
