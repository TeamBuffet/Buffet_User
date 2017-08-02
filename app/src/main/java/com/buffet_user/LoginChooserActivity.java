package com.buffet_user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

/**
 * Created by Ankit on 02/08/17.
 */

public class LoginChooserActivity extends AppCompatActivity implements ITrueCallback {

    TrueClient mTrueClient;
    private String mTruecallerRequestNonce;
    TrueButton trueButton;
    private ImageView imgCover;
    private ImageView imgLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_chooser);
        trueButton = ((TrueButton) findViewById(R.id.com_truecaller_android_sdk_truebutton));
        imgLogo=(ImageView)findViewById(R.id.imgLogo);
        imgCover = (ImageView) findViewById(R.id.imgCover);
        Picasso.with(LoginChooserActivity.this).load("http://ilovepapers.com/wp-content/uploads/papers.co-mv40-food-salad-instagram-hunger-city-life-6-wallpaper.jpg").into(imgCover);
        boolean usable = trueButton.isUsable();
        if (usable) {
            mTrueClient = new TrueClient(this, this);
            trueButton.setTrueClient(mTrueClient);
        } else {
            trueButton.setVisibility(View.GONE);
        }
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {
        final String fullName = trueProfile.firstName + " " + trueProfile.lastName;

    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Toast.makeText(this, "Failed sharing - Reason: " + trueError.getErrorType(), Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (null != mTrueClient && mTrueClient.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (trueButton.isUsable())
            mTruecallerRequestNonce = mTrueClient.generateRequestNonce();
    }
}
