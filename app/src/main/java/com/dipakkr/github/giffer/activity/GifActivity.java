package com.dipakkr.github.giffer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dipakkr.github.giffer.R;
import com.dipakkr.github.giffer.adapter.SimpleAdapter;
import com.dipakkr.github.giffer.helper.HttpHandler;
import com.dipakkr.github.giffer.model.Celebrity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 7/15/17.
 */

public class GifActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    SearchView searchView;

    public String key,fetchurl;
    private ListView lv;
    ArrayList<HashMap<String,String>> urls;
    ProgressDialog pdiaglog;
    ImageView disp;
    private ArrayList<Celebrity> giflist=new ArrayList<>();
    String TAG="LOG:";

    ArrayList<String> suggestions=new ArrayList<>();
    String autourl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        setTitle("Giffer-Gifs");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupTabs();

        //LW
        key=getResources().getString(R.string.tkey);
        autourl = "https://api.tenor.com/v1/autocomplete?tag=&key="+key;
        new updatesuggestions().execute();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void setupTabs(){

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gif_search,menu);

        MenuItem item=menu.findItem(R.id.gif_search);

        searchView =(SearchView) MenuItemCompat.getActionView(item);

        final SearchView.SearchAutoComplete auto=(SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,suggestions);
        auto.setAdapter(adapter);

        auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub

                String searchString=(String)parent.getItemAtPosition(position);
                auto.setText(""+searchString);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchurl="https://api.tenor.com/v1/search?tag="+query+"&key="+key;
                Intent i=new Intent(GifActivity.this,GifSearch.class);
                i.putExtra("fetch",fetchurl);
                i.putExtra("name",query);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                autourl="https://api.tenor.com/v1/autocomplete?tag="+newText+"&key="+key;
                new updatesuggestions().execute();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.gif_search){

        }

        return super.onOptionsItemSelected(item);
    }



    private class updatesuggestions extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // suggestions.clear();
            HttpHandler helper=new HttpHandler();
            String jsonstring=helper.makeServiceCall(autourl);
            Log.e(TAG,"Response from server: "+jsonstring);
            if(jsonstring!=null)
            {
                try
                {
                    JSONObject jsonObject=new JSONObject(jsonstring);
                    JSONArray results=jsonObject.getJSONArray("results");
                    for(int i=0;i<results.length();i++)
                    {
                        String got=results.getString(i);
                        suggestions.add(got);
                    }
                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "JSON error:" + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"JSON error:"+e.getMessage(),Toast.LENGTH_LONG).show();
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        suggestions.clear();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
