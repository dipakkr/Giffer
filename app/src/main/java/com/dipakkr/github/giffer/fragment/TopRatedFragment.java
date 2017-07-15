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
import com.dipakkr.github.giffer.adapter.WallPaperRecyclerAdapter;
import com.dipakkr.github.giffer.helper.ItemDecoration;
import com.dipakkr.github.giffer.helper.RecyclerViewClickListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

/**
 * Created by deepak on 7/15/17.
 */

public class TopRatedFragment extends Fragment {

    RecyclerView recyclerView;
    WallPaperRecyclerAdapter adapter;

    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wall_frag_toprated,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_rating);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClipToPadding(true);
        adapter = new WallPaperRecyclerAdapter(getActivity(),R.layout.wallpaper_item);
        recyclerView.addItemDecoration(new ItemDecoration(2,dpToPx(1),true));
        recyclerView.setAdapter(adapter);

        fetchDataFromApi();
        handleItemClick();

        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return view;
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

    public  int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void fetchDataFromApi(){


    }
}
