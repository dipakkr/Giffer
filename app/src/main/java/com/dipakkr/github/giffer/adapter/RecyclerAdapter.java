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
import com.dipakkr.github.giffer.model.GifItem;

import java.util.List;

/**
 * Created by root on 7/13/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageHolder> {

    private int columnLayout;
    private Context context;
    private List<GifItem> gifItems;

    public class ImageHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.image);
        }
    }

    public RecyclerAdapter(List<GifItem> gifItems, Context context,int columnLayout){
        this.gifItems = gifItems;
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

        if(gifItems != null){
            if(gifItems.size() > 0){
                String IMG_URL = gifItems.get(position).getmGifItem();

                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.imageView);
                Glide.with(context).load(IMG_URL).placeholder(R.drawable.back2).into(imageViewTarget);
            }
            }
        else {
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.imageView);
            Glide.with(context).load(url).placeholder(R.drawable.back2).into(imageViewTarget);
        }


    }

    @Override
    public int getItemCount() {
        if (gifItems != null){

            if(gifItems.size() > 0){

                return gifItems.size();
            }
        }
        return 40;
    }
}

