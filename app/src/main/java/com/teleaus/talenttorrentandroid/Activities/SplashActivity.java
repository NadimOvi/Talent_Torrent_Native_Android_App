package com.teleaus.talenttorrentandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.teleaus.talenttorrentandroid.R;

public class SplashActivity extends AppCompatActivity {

    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        splashImage = findViewById(R.id.splashImage);
        /*linear2 = findViewById(R.id.linear2);*/
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        splashImage.setAnimation(animation);
        /*linear2.setAnimation(animation);*/

        Thread timer = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    super.run();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}