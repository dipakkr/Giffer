package com.dipakkr.github.giffer.adapter;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
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
 * Created by root on 7/13/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageHolder> {


    List<Celebrity> celebrities;
    private int columnLayout;
    private Context context;

    public class ImageHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.item_trending);
        }
    }

    public RecyclerAdapter(List<Celebrity> celebrities, Context context,int columnLayout){
        this.celebrities = celebrities;
        this.context =context;
        this.columnLayout = columnLayout;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(columnLayout,parent,false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {

        String BASE_URL = "https://image.tmdb.org/t/p/w500";

        if(celebrities != null){
            String img = celebrities.get(position).getmImagePath();
            String IMG_URL = BASE_URL + img ;
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
    public int getItemCount() {
        return 20;
    }

}
