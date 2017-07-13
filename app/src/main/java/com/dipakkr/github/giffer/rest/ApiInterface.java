package com.dipakkr.github.giffer.rest;

import com.dipakkr.github.giffer.model.PopularCelebrity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 7/14/17.
 */

public interface ApiInterface {

    @GET("person/popular")
    Call<PopularCelebrity> getPopCelebrity(@Query("api_key") String apikey);

}
