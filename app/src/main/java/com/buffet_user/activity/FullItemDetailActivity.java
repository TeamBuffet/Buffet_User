package com.buffet_user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.buffet_user.R;
import com.buffet_user.pojo.getmenupojo.SingleMenuPojo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class FullItemDetailActivity extends AppCompatActivity {

    private Button buttonAdd;
    private Button buttonCancel;
    private String menuDIntent;
    private SingleMenuPojo singlemenupojo;
    private TextView textViewItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_item_detail);



        buttonAdd=(Button)findViewById(R.id.buttonAdd);
        buttonCancel=(Button)findViewById(R.id.buttonCancel);
        textViewItemName=(TextView)findViewById(R.id.textViewItemName);

        menuDIntent =getIntent().getStringExtra("itemDetails");
        initialize();


        textViewItemName.setText(singlemenupojo.getMenu_name());

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialize() {

        JsonElement jsonElement=new JsonParser().parse(menuDIntent);
        GsonBuilder gsonBuilder=new GsonBuilder();
        Gson gson=gsonBuilder.create();
        singlemenupojo=(gson.fromJson(jsonElement,SingleMenuPojo.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
