package com.buffet_user.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.buffet_user.R;
import com.kogitune.activity_transition.ActivityTransitionLauncher;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        /*

       Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                final ImageView imageView=(ImageView)findViewById(R.id.imageView);
                Intent intent = new Intent(SplashActivity.this, IntroActivity.class);

                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(SplashActivity.this, imageView, "simple_activity_transition");


             //   ActivityOptionsCompat options = ActivityOptionsCompat.
             //    makeSceneTransitionAnimation(SplashActivity.this,
             //                   imageView,
             //                   ViewCompat.getTransitionName(imageView));
                startActivity(intent, options.toBundle());
                finishAfterTransition();

//                finish();

            }
        },2500);

        */

//    /*

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

     //   */

    }
}
