package com.example.yggdralisk.kundomondo;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yggdralisk on 14.04.16.
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Bind(R.id.splash_layout)
    LinearLayout layout;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);
        context = this;

        layout.post(new Runnable() {
            public void run() {
                setBackground();
                startLoginDelayed();//Move to "login" activity
            }
        });
    }

    private void startLoginDelayed() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(context, LoginActivity.class);
               startActivity(intent);
               // setBackground();
               // startLoginDelayed();
            }
        }, 1500);
    }

    private void setBackground() {
        TypedArray imgs = getResources().obtainTypedArray(R.array.splash_drawables);
        int resID = imgs.getResourceId(new Random().nextInt(imgs.length()), R.drawable.splash_1);
        imgs.recycle();
        Glide.with(this).load(resID).asBitmap().fitCenter().into(new SimpleTarget<Bitmap>(layout.getWidth(), layout.getHeight()) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //Making sure it will happen only on 16+ api
                    layout.setBackground(drawable);
                }
            }
        });
    }
}
