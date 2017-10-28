package com.buffet_user.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ankit on 02/08/17.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "Buffet";
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("_user_gender", "male");
        editor.apply();
    }

    public Intent openActivity(Context first, Class<? extends AppCompatActivity> second) {

        Intent intent = new Intent(first, second);
        return intent;
    }

}
