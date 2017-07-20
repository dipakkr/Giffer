package com.dipakkr.github.giffer.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
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
import com.dipakkr.github.giffer.activity.GifDetailActivity;
import com.dipakkr.github.giffer.adapter.RecyclerAdapter;
import com.dipakkr.github.giffer.helper.HttpHandler;
import com.dipakkr.github.giffer.helper.ItemDecoration;
import com.dipakkr.github.giffer.helper.RecyclerViewClickListener;
import com.dipakkr.github.giffer.model.GifItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 7/13/17.
 */

public class Trending  extends Fragment {

    private static final String API_KEY = "53873c6fc26c2abac786d7822d2e1a93";

    RecyclerView recyclerView;

    RecyclerAdapter adapter;

    private AdView mAdView;

    private List<GifItem> gifItems = new ArrayList<>();

    ProgressDialog  mDialog;
    String url;


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
        adapter = new RecyclerAdapter(gifItems, getActivity(),R.layout.item_recycler_view);
        recyclerView.addItemDecoration(new ItemDecoration(2,dpToPx(1),true));
        recyclerView.setAdapter(adapter);

        intializeAd(view);

        if(gifItems.size() == 0){
            new GetTrendingGif().execute();
        }
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
                String url = gifItems.get(position).getmGifItem();
                Intent detailGif = new Intent(getActivity(),GifDetailActivity.class);
                detailGif.putExtra("gifurl",url);
                startActivity(detailGif);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    public class GetTrendingGif extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage("Getting Trending Gif");
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler httpHelper = new HttpHandler();
            String call_url = httpHelper.createUrl("trending");
            String jsonResponse = httpHelper.makeServiceCall(call_url);

            if(jsonResponse != null){

                try{
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONArray  results = jsonObject.getJSONArray("results");

                    for(int i = 0; i < results.length(); i++ ){
                        JSONObject res = results.getJSONObject(i);
                        JSONArray media = res.getJSONArray("media");

                        for(int j = 0; j< media.length(); j ++){
                            JSONObject med = media.getJSONObject(j);
                            JSONObject tiny = med.getJSONObject("tinygif");
                             url = tiny.getString("url");

                            GifItem gifItem = new GifItem(url);
                            gifItems.add(gifItem);
                        }
                    }
                }
                catch (final JSONException e){
                    Log.e("TRENDING GIF",e.getMessage());
                }

            }
            else{
                Log.e("TRENDING GIF", "Couldn't reach Server");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(mDialog.isShowing()){
                mDialog.dismiss();
            }

            adapter.notifyDataSetChanged();
        }

    }
}
