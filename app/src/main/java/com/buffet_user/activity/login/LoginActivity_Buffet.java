package com.buffet_user.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.buffet_user.activity.review.BlogHomeActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Ankit on 02/08/17.
 */

public class LoginActivity_Buffet extends BaseActivity{

    MaterialEditText edtContact;
    Button btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffet_login);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        edtContact = (MaterialEditText) findViewById(R.id.edtContact);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnContinue.setBackgroundResource(R.drawable.ripple_green);
        }
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(openActivity(LoginActivity_Buffet.this,FillProfileDetailsActivity.class));

            }
        });
    }
}
