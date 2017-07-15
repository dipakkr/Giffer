package com.dipakkr.github.giffer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.dipakkr.github.giffer.R;

import java.util.List;

/**
 * Created by root on 7/13/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageHolder> {

    private int columnLayout;
    private Context context;

    public class ImageHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.image);
        }
    }

    public RecyclerAdapter( Context context,int columnLayout){
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

        String url = "https://media.tenor.com/images/eb4c4ff13e54cf3a4b5495c2c98c87b1/tenor.gif";

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.imageView);
        Glide.with(context).load(url).centerCrop().placeholder(R.drawable.back2).into(imageViewTarget);
    }

    @Override
    public int getItemCount() {
        return 15;
    }

}

