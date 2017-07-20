package com.dipakkr.github.giffer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.dipakkr.github.giffer.R;

/**
 * Created by root on 7/20/17.
 */

public class GifDetailActivity extends AppCompatActivity{

    static String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gif);

      //  this.setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        url = intent.getStringExtra("gifurl");
        Log.v("PASSED_GIF_URL",url);

        ImageView img = (ImageView)findViewById(R.id.img_gif);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(img);
        Glide.with(this).load(url).into(imageViewTarget);

        ImageView share = (ImageView)findViewById(R.id.share_gif);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent();
                send.setAction(Intent.ACTION_SEND);
                send.putExtra(Intent.EXTRA_TEXT,url);
                send.setType("text/plain");
                startActivity(Intent.createChooser(send,"Share File"));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share,menu);
        return true;
    }
}
