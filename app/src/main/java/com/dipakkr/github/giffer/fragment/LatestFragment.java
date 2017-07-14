package com.dipakkr.github.giffer.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipakkr.github.giffer.R;
import com.dipakkr.github.giffer.adapter.RecyclerAdapter;
import com.dipakkr.github.giffer.adapter.WallPaperRecyclerAdapter;
import com.dipakkr.github.giffer.helper.ItemDecoration;
import com.dipakkr.github.giffer.model.Celebrity;
import com.dipakkr.github.giffer.model.PopularCelebrity;
import com.dipakkr.github.giffer.rest.ApiClient;
import com.dipakkr.github.giffer.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by deepak on 7/15/17.
 */

public class LatestFragment extends Fragment {

    RecyclerView recyclerView;
    WallPaperRecyclerAdapter adapter;

    List<Celebrity> celebrities;
    ApiInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wall_frag_latest,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_latest);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClipToPadding(true);
        adapter = new WallPaperRecyclerAdapter(celebrities,getActivity(),R.layout.wallpaper_item);
        recyclerView.addItemDecoration(new ItemDecoration(2,dpToPx(1),true));
        recyclerView.setAdapter(adapter);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        fetchDataFromApi();

        return view;
    }

    public  int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void fetchDataFromApi(){

        Call<PopularCelebrity> popularCelebrityCall = apiInterface.getPopCelebrity(getResources().getString(R.string.API_KEY_TEST));
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
