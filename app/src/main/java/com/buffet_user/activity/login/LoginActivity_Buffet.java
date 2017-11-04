package com.buffet_user.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Ankit on 02/08/17.
 */

public class LoginActivity_Buffet extends BaseActivity {

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
                String phonenumber = edtContact.getText().toString().trim();
                if (phonenumber.length() < 10 || phonenumber.isEmpty()) {
                    Toast.makeText(LoginActivity_Buffet.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("phonenumber", phonenumber);
                    Intent intent = openActivity(LoginActivity_Buffet.this, FillProfileDetailsActivity.class);
                    intent.putExtra("activity", "buffet");
                    intent.putExtra("phonenumber", phonenumber);
                    startActivity(intent);
                }
            }
        });
    }

}
