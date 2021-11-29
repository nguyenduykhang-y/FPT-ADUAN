package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.agrawalsuneet.dotsloader.loaders.TrailingCircularDotsLoader;

public class welcome_screen extends AppCompatActivity {
    TrailingCircularDotsLoader trailingCircularDotsLoaders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        trailingCircularDotsLoaders = findViewById(R.id.loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                TrailingCircularDotsLoader trailingCircularDotsLoader = new TrailingCircularDotsLoader(
                        getApplicationContext(),
                        24,
                        ContextCompat.getColor(getApplicationContext(), android.R.color.holo_green_light),
                        100,
                        5);
                trailingCircularDotsLoader.setAnimDuration(2000);
                trailingCircularDotsLoader.setAnimDelay(200);

                trailingCircularDotsLoaders.addView(trailingCircularDotsLoader);
                finish();
            }
        }, 3100);


    }
}