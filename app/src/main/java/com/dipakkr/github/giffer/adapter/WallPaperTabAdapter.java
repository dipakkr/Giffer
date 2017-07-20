package com.dipakkr.github.giffer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dipakkr.github.giffer.fragment.Emotions;
import com.dipakkr.github.giffer.fragment.LatestFragment;
import com.dipakkr.github.giffer.fragment.Memes;
import com.dipakkr.github.giffer.fragment.PopularFragment;
import com.dipakkr.github.giffer.fragment.Reactions;
import com.dipakkr.github.giffer.fragment.Recent;
import com.dipakkr.github.giffer.fragment.TopRatedFragment;
import com.dipakkr.github.giffer.fragment.Trending;

/**
 * Created by root on 7/15/17.
 */

public class WallPaperTabAdapter extends FragmentPagerAdapter {

    Context mContext;

    private String[] tabTitles = new String[]{ "Latest", "Popular", "Top Rated"};

    public WallPaperTabAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0 : return new LatestFragment();

            case 1 : return new PopularFragment();

            case 2 : return new TopRatedFragment();

        }
        return new LatestFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

}
