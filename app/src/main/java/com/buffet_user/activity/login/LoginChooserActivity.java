package com.buffet_user.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.buffet_user.activity.review.BlogHomeActivity;
import com.squareup.picasso.Picasso;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import info.abdolahi.CircularMusicProgressBar;

/**
 * Created by Ankit on 02/08/17.
 */

public class LoginChooserActivity extends BaseActivity implements ITrueCallback {

    TrueClient mTrueClient;
    private String mTruecallerRequestNonce;
    TrueButton trueButton;
    private ImageView imgCover;
    private CircularMusicProgressBar imgLogo;
    private Button btnLoginBuffet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_chooser);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        trueButton = ((TrueButton) findViewById(R.id.com_truecaller_android_sdk_truebutton));
        imgLogo = (CircularMusicProgressBar) findViewById(R.id.imgLogo);
        imgCover = (ImageView) findViewById(R.id.imgCover);
        btnLoginBuffet = (Button) findViewById(R.id.btnLogin);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnLoginBuffet.setBackgroundResource(R.drawable.ripple_intro);
        }

        Picasso.with(LoginChooserActivity.this).load("https://i.ytimg.com/vi/VPHFD_VotGc/maxresdefault.jpg").into(imgCover);
        boolean usable = trueButton.isUsable();
        if (usable) {
            mTrueClient = new TrueClient(this, this);
            trueButton.setTrueClient(mTrueClient);
        } else {
            trueButton.setVisibility(View.GONE);
        }

        btnLoginBuffet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(openActivity(LoginChooserActivity.this,LoginActivity_Buffet.class));

            }
        });
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
