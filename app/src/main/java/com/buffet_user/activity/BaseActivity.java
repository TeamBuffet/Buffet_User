package com.buffet_user.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ankit on 02/08/17.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "Buffet";
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public static boolean checkMediaManagerInitialized=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("_user_gender", "male");
        editor.apply();

    }

    public void initCloudinary(){
        //initialize cloudinary
        Map config = new HashMap();
        config.put("cloud_name", "buffet");
        config.put("api_key","625937658917463");
        config.put("api_secret","zjSoyCRWA0HgF6aBHlpN51LFKSo");
        MediaManager.init(this, config);
        checkMediaManagerInitialized=true;
    }
    public Intent openActivity(Context first, Class<? extends AppCompatActivity> second) {

        Intent intent = new Intent(first, second);
        return intent;
    }

}
