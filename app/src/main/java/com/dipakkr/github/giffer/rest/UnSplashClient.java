package com.dipakkr.github.giffer.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 7/15/17.
 */

public class UnSplashClient {

    public static final String BASE_URL = "https://api.unsplash.com/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
