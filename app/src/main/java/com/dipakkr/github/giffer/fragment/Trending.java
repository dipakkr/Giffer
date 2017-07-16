package com.dipakkr.github.giffer.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipakkr.github.giffer.R;
import com.dipakkr.github.giffer.adapter.RecyclerAdapter;
import com.dipakkr.github.giffer.helper.ItemDecoration;
import com.dipakkr.github.giffer.helper.RecyclerViewClickListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

/**
 * Created by root on 7/13/17.
 */

public class Trending  extends Fragment {

    private static final String API_KEY = "53873c6fc26c2abac786d7822d2e1a93";

    RecyclerView recyclerView;


    RecyclerAdapter adapter;

    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_trending,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_trending);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClipToPadding(true);
        adapter = new RecyclerAdapter(getActivity(),R.layout.item_recycler_view);
        recyclerView.addItemDecoration(new ItemDecoration(2,dpToPx(1),true));
        recyclerView.setAdapter(adapter);

        intializeAd(view);

        fetchDataFromApi();
        handleItemClick();
        return view;

    }

    private void intializeAd(View view){

        mAdView = (AdView)view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public  int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void handleItemClick(){
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(),
                recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Handle clicks
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    public void fetchDataFromApi(){

    }
}
