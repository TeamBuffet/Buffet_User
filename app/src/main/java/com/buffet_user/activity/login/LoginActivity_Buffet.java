package com.buffet_user.activity.login;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ankit on 02/08/17.
 */

public class LoginActivity_Buffet extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    MaterialEditText edtContact;
    Button btnContinue;
    private final Integer RESOLVE_HINT = 1;

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


        if (sharedPreferences.getString("phonenumber", "") != "") {
            edtContact.setText(sharedPreferences.getString("phonenumber", ""));
        } else {
            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(LoginActivity_Buffet.this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Auth.CREDENTIALS_API)
                    .build();
            HintRequest hintRequest = new HintRequest.Builder()
                    .setPhoneNumberIdentifierSupported(true)
                    .build();
            PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                    googleApiClient, hintRequest);
            try {
                startIntentSenderForResult(intent.getIntentSender(),
                        RESOLVE_HINT, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }

            btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenumber = edtContact.getText().toString().trim();
                if (phonenumber.length() < 10 || phonenumber.isEmpty()) {
                    Toast.makeText(LoginActivity_Buffet.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("phonenumber", phonenumber);
                    editor.apply();
                    Intent intent = openActivity(LoginActivity_Buffet.this, FillProfileDetailsActivity.class);
                    intent.putExtra("activity", "buffet");
                    intent.putExtra("phonenumber", phonenumber);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                // Log.e("TAGPHONE", credential.getId());  //<-- will need to process phone number string
                edtContact.setText(credential.getId());
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
