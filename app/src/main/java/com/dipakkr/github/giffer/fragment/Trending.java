package com.dipakkr.github.giffer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dipakkr.github.giffer.R;
import com.dipakkr.github.giffer.adapter.RecyclerAdapter;
import com.dipakkr.github.giffer.model.Celebrity;
import com.dipakkr.github.giffer.model.PopularCelebrity;
import com.dipakkr.github.giffer.rest.ApiClient;
import com.dipakkr.github.giffer.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 7/13/17.
 */

public class Trending  extends Fragment {

    private static final String API_KEY = "53873c6fc26c2abac786d7822d2e1a93";

    RecyclerView recyclerView;

    List<Celebrity> celebrities;

    RecyclerAdapter adapter;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_trending,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_trending);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClipToPadding(true);
        adapter = new RecyclerAdapter(celebrities,getActivity(),R.layout.item_recycler_view);
        recyclerView.setAdapter(adapter);
        fetchDataFromApi();
        return view;
    }
    public void fetchDataFromApi(){

        Call<PopularCelebrity> popularCelebrityCall = apiInterface.getPopCelebrity(API_KEY);
        popularCelebrityCall.enqueue(new Callback<PopularCelebrity>() {
            @Override
            public void onResponse(Call<PopularCelebrity> call, Response<PopularCelebrity> response) {
                celebrities = response.body().getCelebrities();
                recyclerView.setAdapter(adapter);
                int t = celebrities.size();
                Log.v("SIZE",String.valueOf(t));
            }

            @Override
            public void onFailure(Call<PopularCelebrity> call, Throwable t) {

            }
        });
    }
}
