package com.dipakkr.github.giffer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dipakkr.github.giffer.R;

import java.util.List;

/**
 * Created by root on 7/15/17.
 */

public class WallPaperRecyclerAdapter extends RecyclerView.Adapter<WallPaperRecyclerAdapter.WallPaperHolder> {


    private int columnLayout;
    private Context context;

    public WallPaperRecyclerAdapter(Context context, int columnLayout) {
        this.context = context;
        this.columnLayout = columnLayout;
    }


    public class WallPaperHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public WallPaperHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }

    @Override
    public WallPaperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(columnLayout,parent,false);
        return new WallPaperHolder(view);
    }

    @Override
    public void onBindViewHolder(WallPaperHolder holder, int position) {

        String BASE_URL = "https://image.tmdb.org/t/p/w500";

        String TEST_URL = "https://images.unsplash.com/photo-1465865523598-a834aac5d3fa?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=f7984651545f9054e7803bb0dcae91e4";
/*

        if (celebrities != null) {
            String img = celebrities.get(position).getmImagePath();
            String IMG_URL = BASE_URL + img;
            Log.v("Image " + position, IMG_URL);
        }
*/

        Glide.with(context).load(TEST_URL)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

    }
        @Override
        public int getItemCount () {
            return 200;
        }

    }

