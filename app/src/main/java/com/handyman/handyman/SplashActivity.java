package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
Thread splash;
ImageView imageView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView=(ImageView)findViewById(R.id.image_view);
        Animation animation= AnimationUtils.loadAnimation(this, R.anim.fade);
        imageView.startAnimation(animation);
        splash=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent i =new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {

                    Toast.makeText(SplashActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        };
        splash.start();
    }
}
