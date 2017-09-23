package com.buffet_user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.buffet_user.FillProfileDetailsActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import com.buffet_user.R;

/**
 * Created by Ankit on 02/08/17.
 */

public class LoginActivity_Buffet extends AppCompatActivity {

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
    btnContinue.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity_Buffet.this,FillProfileDetailsActivity.class);
            startActivity(intent);
        }
    });
    }
}
