package com.dipakkr.github.giffer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dipakkr.github.giffer.R;
import com.dipakkr.github.giffer.model.Celebrity;

import java.util.List;

/**
 * Created by root on 7/15/17.
 */

public class WallPaperRecyclerAdapter extends RecyclerView.Adapter<WallPaperRecyclerAdapter.WallPaperHolder> {

    List<Celebrity> celebrities;
    private int columnLayout;
    private Context context;

    public WallPaperRecyclerAdapter(List<Celebrity> celebrities, Context context, int columnLayout) {
        this.celebrities = celebrities;
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

        if (celebrities != null) {
            String img = celebrities.get(position).getmImagePath();
            String IMG_URL = BASE_URL + img;
            Log.v("IMage " + position, IMG_URL);

            Glide.with(context).load(IMG_URL)
                    .thumbnail(1f)
                    .crossFade()
                    //Center Crop fits the image to image view
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);

        }
    }
        @Override
        public int getItemCount () {
            return 20;
        }

    }

