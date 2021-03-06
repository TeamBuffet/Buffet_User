package com.buffet_user.activity.login;

import android.content.Intent;
import android.os.Bundle;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.buffet_user.activity.dashboard.DashboardActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    if (sharedPreferences.getString("phonenumber", "").equals("")) {
                        Intent intent = openActivity(SplashActivity.this, FillProfileDetailsActivity.class);
                        intent.putExtra("activity", "buffet");
                        startActivity(intent);
                        finish();
                    } else if (!sharedPreferences.getString("phonenumber", "").equals("")) {
                        startActivity(openActivity(SplashActivity.this, DashboardActivity.class));
                    } else {
                        startActivity(openActivity(SplashActivity.this, IntroActivity.class));
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


    }
}
