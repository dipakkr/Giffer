package com.dipakkr.github.giffer.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.compat.BuildConfig;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipakkr.github.giffer.R;
import com.dipakkr.github.giffer.activity.WallpaperDetail;
import com.dipakkr.github.giffer.adapter.WallPaperRecyclerAdapter;
import com.dipakkr.github.giffer.helper.ItemDecoration;
import com.dipakkr.github.giffer.helper.RecyclerViewClickListener;
import com.dipakkr.github.giffer.model.ImageResponse;
import com.dipakkr.github.giffer.model.ImageUrls;
import com.dipakkr.github.giffer.rest.UnSplashClient;
import com.dipakkr.github.giffer.rest.UnsplashInterface;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by deepak on 7/15/17.
 */

public class LatestFragment extends Fragment {

    RecyclerView recyclerView;
    WallPaperRecyclerAdapter adapter;

    private AdView mAdView;

    UnsplashInterface apiInterface;

    List<ImageResponse> imageResponses ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wall_frag_latest,container,false);

        apiInterface = UnSplashClient.getClient().create(UnsplashInterface.class);

        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_latest);
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

        return view;
    }

    private void handleItemClick(){
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(),
                recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), WallpaperDetail.class));
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
        Call<List<ImageResponse>> responseCall = apiInterface.getImageResponse(getString(R.string.uns_client_id),"30","latest");
        HttpUrl url =  responseCall.request().url();
        Log.v("URL-->",String.valueOf(url));
        responseCall.enqueue(new Callback<List<ImageResponse>>() {
            @Override
            public void onResponse(Call<List<ImageResponse>> call, Response<List<ImageResponse>> response) {
                imageResponses = response.body();
                int id  = imageResponses.size();
                String id2 = imageResponses.get(1).getImage_id();

                Log.v("ID1----",String.valueOf(id));
                Log.v("ID2----",id2);
            }
            @Override
            public void onFailure(Call<List<ImageResponse>> call, Throwable t) {

            }
        });
    }
}
