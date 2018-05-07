package com.buffet_user.activity.review;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.pojo.PostFeedDataPOJO;
import com.buffet_user.pojo.PostFeedPOJO;
import com.buffet_user.retrofit.ApiUtils;
import com.buffet_user.retrofit.SOService;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 04/11/17.
 */

public class PostFeedActivity extends AppCompatActivity {

    ImageView imgFeed;
    EditText edtCaption;
    Button btnPost;
    Toolbar toolbar;
    private SOService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_feed);
        imgFeed = (ImageView) findViewById(R.id.imgFeed);
        edtCaption = (EditText) findViewById(R.id.edtCaption);
        btnPost = (Button) findViewById(R.id.btnSubmit);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mService = ApiUtils.getSOService();
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        Picasso.with(PostFeedActivity.this).load(url).into(imgFeed);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFeed(url);
            }
        });

    }

    private void postFeed(String url) {
        PostFeedDataPOJO json = new PostFeedDataPOJO();
        try {
            json.setBlogImageUrl(url);
            json.setBlogCaption("Pizza is always delicious from mobile");
            json.setUserId("1");
            json.setTimeStamp("1509652963");
        } catch (Exception e) {
            Log.e("TAG ERROR", e.getMessage());
        }
        mService.savePost(json).enqueue(new Callback<PostFeedPOJO>() {
            @Override
            public void onResponse(Call<PostFeedPOJO> call, Response<PostFeedPOJO> response) {

                if (response.isSuccessful() && response.body().getError().equals("false")) {
                    Toast.makeText(PostFeedActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    Toast.makeText(PostFeedActivity.this, "Error in hit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostFeedPOJO> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });


    }
}
