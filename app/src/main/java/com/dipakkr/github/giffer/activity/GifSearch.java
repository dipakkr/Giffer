package com.dipakkr.github.giffer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.dipakkr.github.giffer.R;
import com.dipakkr.github.giffer.adapter.RecyclerAdapter;
import com.dipakkr.github.giffer.helper.HttpHandler;
import com.dipakkr.github.giffer.helper.ItemDecoration;
import com.dipakkr.github.giffer.model.GifItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalit on 7/15/17.
 */

public class GifSearch extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private List<GifItem> gifsearch = new ArrayList<>();

    String fetchurl,query;
    ProgressDialog pdiaglog;
    String TAG="TAG: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_search);

        recyclerView=(RecyclerView)findViewById(R.id.rv_main);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClipToPadding(true);
        adapter = new RecyclerAdapter(gifsearch,this,R.layout.item_recycler_view);
        recyclerView.addItemDecoration(new ItemDecoration(2,dpToPx(1),true));
        recyclerView.setAdapter(adapter);

        Intent i=getIntent();
        fetchurl=i.getStringExtra("fetch");
        query=i.getStringExtra("name");
        setTitle(query+" Gifs");
        new getgifs().execute();

    }

    private class getgifs extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdiaglog=new ProgressDialog(GifSearch.this);
            pdiaglog.setMessage("Hold on !! Getting GIF");
            pdiaglog.setCancelable(false);
            pdiaglog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler helper=new HttpHandler();
            String jsonstring=helper.makeServiceCall(fetchurl);
            Log.e(TAG,"Response from server: "+jsonstring);
            if(jsonstring!=null)
            {
                try
                {
                    JSONObject jsonObject=new JSONObject(jsonstring);
                    JSONArray results=jsonObject.getJSONArray("results");
                    for(int i=0;i<results.length();i++)
                    {
                        JSONObject res=results.getJSONObject(i);
                        JSONArray media=res.getJSONArray("media");
                        for(int j=0;j<media.length();j++)
                        {
                            JSONObject inmedia=media.getJSONObject(j);
                            JSONObject gif=inmedia.getJSONObject("tinygif");
                            String gifurl = gif.getString("url");

                            GifItem gifItem = new GifItem(gifurl);
                            gifsearch.add(gifItem);
                        }
                    }
                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "JSON error:" + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"JSON error:" + e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else
            {
                Log.e(TAG, "Couldn't reach Server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Couldn't reach Server",Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(pdiaglog.isShowing())
            {
                pdiaglog.dismiss();
            }


            adapter.notifyDataSetChanged();

        }
    }


    public  int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
