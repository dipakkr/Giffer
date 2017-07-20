package com.dipakkr.github.giffer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.dipakkr.github.giffer.R;
import com.dipakkr.github.giffer.adapter.RecyclerAdapter;
import com.dipakkr.github.giffer.helper.HttpHandler;
import com.dipakkr.github.giffer.model.Celebrity;
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

    //int cn=2;
    String fetchurl;
    ProgressDialog pdiaglog;
    String TAG="TAG: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_search);

        recyclerView=(RecyclerView)findViewById(R.id.rv_main);
        adapter = new RecyclerAdapter(gifsearch,this,R.layout.item_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Intent i=getIntent();
        fetchurl=i.getStringExtra("fetch");
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
                            /*HashMap<String,String> hash=new HashMap<>();
                            hash.put("gifurl",gifurl);
                            urls.add(hash);*/
                            GifItem gifItem = new GifItem(gifurl);
                            gifsearch.add(gifItem);
                            //Log.e("add",temp.getmImagePath());
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

         /*   ListAdapter adapter=new SimpleAdapter(MainActivity.this,urls,R.layout.temp_layout,new String[]{"gifurl"},new int[]{R.id.temp_disp});
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   //Toast.makeText(MainActivity.this,urls[position].get(toString()),Toast.LENGTH_SHORT).show();
                     Picasso.with(MainActivity.this).load("https://media.tenor.com/images/8d7038f55b11b8385fa5d242be0481d9/tenor.gif").into(disp);
                }
            });*/

            adapter.notifyDataSetChanged();

        }
    }
}
