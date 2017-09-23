package com.buffet_user.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.buffet_user.R;
import com.buffet_user.adapter.CustomPagerAdapter;
import com.buffet_user.transitions.DepthPageTransformer;
import com.buffet_user.transitions.ZoomOutPageTransformer;
//import com.kogitune.activity_transition.ActivityTransition;

import me.relex.circleindicator.CircleIndicator;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);



        int[] mResources = {
                R.drawable.buffet,
                R.drawable.met_ic_clear,
                R.drawable.com_truecaller_icon,
        };


        Button buttonContinue=(Button)findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, LoginChooserActivity.class);
                startActivity(intent);
            }
        });

        ViewPager viewPager=(ViewPager)findViewById(R.id.viewPager);
        //   viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        viewPager.setPageTransformer(true, new DepthPageTransformer());

        CustomPagerAdapter customPagerAdapter=new CustomPagerAdapter(this,mResources);
        viewPager.setAdapter(customPagerAdapter);
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }
}
