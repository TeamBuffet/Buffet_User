package com.buffet_user.activity.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import info.abdolahi.CircularMusicProgressBar;

/**
 * Created by Ankit on 12/08/17.
 */

public class FillProfileDetailsActivity extends BaseActivity {

    TextView txtGen;
    CircularMusicProgressBar profile;
    FloatingActionButton btnNext;
    int i = 0;
    ImageView imgFemale, imgMale;
    Toolbar toolbar;
    AppCompatImageView imgProfileEdit;
    MaterialEditText edtName, edtEmail, edtDob;
    private String[] Permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int PERMS_REQUEST_CODE = 140;
    String phonenumber, email, name, gender, avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(sharedPreferences.getString("user_gender", "Male").equals("Female") ? R.style.AppThemeFemale : R.style.AppTheme);
        setContentView(R.layout.activity_fill_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgProfileEdit = (AppCompatImageView) findViewById(R.id.profilePicEdit);
        profile = (CircularMusicProgressBar) findViewById(R.id.profilepic);
        btnNext = (FloatingActionButton) findViewById(R.id.btnNext);
        imgFemale = (ImageView) findViewById(R.id.imgFemale);
        imgMale = (ImageView) findViewById(R.id.imgMale);
        txtGen = (TextView) findViewById(R.id.txtGen);
        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtDob = (MaterialEditText) findViewById(R.id.edtDOB);
        set_user_color();
        profile.setValue(90);
        profile.setProgressAnimationState(true);

        Intent intent = getIntent();
        String activity = intent.getStringExtra("activity");
        if (activity.equals("buffet")) {
            phonenumber = sharedPreferences.getString("phonenumber", "");
        } else if (activity.equals("truecaller")) {

            name = sharedPreferences.getString("name", "");
            email = sharedPreferences.getString("email", "");
            gender = sharedPreferences.getString("gender", "");
            phonenumber = sharedPreferences.getString("phonenumber", "");
            avatar = sharedPreferences.getString("avatar", "");
            if (!avatar.equals(""))
                Picasso.with(FillProfileDetailsActivity.this).load(avatar).into(profile);
            edtEmail.setText(email);
            edtName.setText(name);
            if (gender.equals("Female")) {
                imgFemale.performClick();
            } else {
                imgMale.performClick();
            }
        }

        imgFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtDob.getText().length() > 0 || edtName.getText().length() > 0 || edtEmail.getText().length() > 0) {

                    editor.putString("user_dob", edtDob.getText().toString().trim());
                    editor.putString("user_name", edtName.getText().toString().trim());
                    editor.putString("user_email", edtEmail.getText().toString().trim());

                }

                editor.putString("user_gender", "Female");
                editor.apply();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        imgMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtDob.getText().length() > 0 || edtName.getText().length() > 0 || edtEmail.getText().length() > 0) {

                    editor.putString("user_dob", edtDob.getText().toString().trim());
                    editor.putString("user_name", edtName.getText().toString().trim());
                    editor.putString("user_email", edtEmail.getText().toString().trim());

                }

                editor.putString("user_gender", "Male");
                editor.apply();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((ContextCompat.checkSelfPermission(FillProfileDetailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(FillProfileDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                    startActivity(openActivity(FillProfileDetailsActivity.this, PickUpLocationActivity.class));


                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(Permissions, PERMS_REQUEST_CODE);
                    } else {
                        ActivityCompat.requestPermissions(FillProfileDetailsActivity.this, Permissions, PERMS_REQUEST_CODE);
                    }
                }

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == 0 && grantResults[1] == 0) {
            btnNext.performClick();
        }
    }

    private void set_user_color() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(((sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark)))));
        btnNext.setBackgroundTintList(ColorStateList.valueOf(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark)));
        profile.setBorderProgressColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        Drawable mDrawable = getResources().getDrawable(R.drawable.ic_add_a_photo_black_24dp);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY));
        imgProfileEdit.setBackgroundDrawable(mDrawable);
        txtGen.setTextColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtName.setUnderlineColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtName.setBaseColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtName.setTextColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtName.setPrimaryColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtEmail.setUnderlineColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtEmail.setBaseColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtEmail.setTextColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtEmail.setPrimaryColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtDob.setUnderlineColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtDob.setBaseColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtDob.setTextColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtDob.setPrimaryColor(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark));
        edtName.setText(sharedPreferences.getString("user_name", ""));
        edtEmail.setText(sharedPreferences.getString("user_email", ""));
        edtDob.setText(sharedPreferences.getString("user_dob", ""));

    }
}
