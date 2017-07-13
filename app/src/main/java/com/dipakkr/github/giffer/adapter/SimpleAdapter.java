package com.dipakkr.github.giffer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dipakkr.github.giffer.R;
import com.dipakkr.github.giffer.fragment.Emotions;
import com.dipakkr.github.giffer.fragment.Memes;
import com.dipakkr.github.giffer.fragment.Reactions;
import com.dipakkr.github.giffer.fragment.Recent;
import com.dipakkr.github.giffer.fragment.Trending;

/**
 * Created by root on 7/13/17.
 */

public class SimpleAdapter extends FragmentPagerAdapter {

    Context mContext;

    private String[] tabTitles = new String[]{"Trending", "Recent", "Reactions","Emotions","Memes"};


    public SimpleAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0 : return new Trending();

            case 1 : return new Recent();

            case 2 : return new Reactions();

            case 3 : return new Emotions();

            case 4 : return new Memes();

        }
        return new Trending();
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
