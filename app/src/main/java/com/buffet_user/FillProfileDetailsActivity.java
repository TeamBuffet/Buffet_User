package com.buffet_user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import info.abdolahi.CircularMusicProgressBar;

/**
 * Created by Ankit on 12/08/17.
 */

public class FillProfileDetailsActivity extends AppCompatActivity {

    CircularMusicProgressBar profile;
    FloatingActionButton btnNext;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);
        profile = (CircularMusicProgressBar) findViewById(R.id.profilepic);
        btnNext = (FloatingActionButton) findViewById(R.id.btnNext);
        profile.setValue(100);
        profile.setProgressAnimationState(true);
    }
}
