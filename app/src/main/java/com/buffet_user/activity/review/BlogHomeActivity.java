package com.buffet_user.activity.review;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.buffet_user.fragment.review.FeedFragment;
import com.buffet_user.pojo.BlogFeedPOJO;
import com.buffet_user.pojo.MessageFeedPOJO;
import com.buffet_user.retrofit.ApiUtils;
import com.buffet_user.retrofit.SOService;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 28/10/17.
 */

public class BlogHomeActivity extends BaseActivity {


    Toolbar toolbar;
    TextView txtTitle;
    private String[] Permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private SOService mService;
    private static final int PERMS_REQUEST_CODE = 140;
    private ArrayList<MessageFeedPOJO> listFeed;
    private Uri uri;
    private static final int CAMERA = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.bottomNav);
        txtTitle.setText("Feeds");
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listFeed = new ArrayList<MessageFeedPOJO>();
        mService = ApiUtils.getSOService();
        loadFeeds();
        if (!checkMediaManagerInitialized) {
            initCloudinary();
        }
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.mipmap.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("Loved", R.mipmap.ic_love));
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.colorPrimary));
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                if ((ContextCompat.checkSelfPermission(BlogHomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(BlogHomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(BlogHomeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                    showFileChooser();
                } else {
                    ActivityCompat.requestPermissions(BlogHomeActivity.this, Permissions, PERMS_REQUEST_CODE);

                }
            }


            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemName.equals("HOME")) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FeedFragment feedFragment = new FeedFragment().newInstance(listFeed);
                    fragmentTransaction.replace(R.id.frame, feedFragment);
                    fragmentTransaction.commit();
                    Toast.makeText(BlogHomeActivity.this, "Home", Toast.LENGTH_SHORT).show();

                }
                if (itemName.equals("Loved")) {
                    Toast.makeText(BlogHomeActivity.this, "Love", Toast.LENGTH_SHORT).show();
                    startActivity(openActivity(BlogHomeActivity.this, ItemReviewActivity.class));
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if (itemName.equals("HOME")) {
                    Toast.makeText(BlogHomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FeedFragment feedFragment = new FeedFragment().newInstance(listFeed);
                    fragmentTransaction.replace(R.id.frame, feedFragment);
                    fragmentTransaction.commit();

                }
                if (itemName.equals("Loved")) {
                    Toast.makeText(BlogHomeActivity.this, "Love", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    private void loadFeeds() {

        mService.getMessage().enqueue(new Callback<BlogFeedPOJO>() {
            @Override
            public void onResponse(Call<BlogFeedPOJO> call, Response<BlogFeedPOJO> response) {

                if (response.isSuccessful() && response.body().getError().equals("false")) {
                    for (MessageFeedPOJO message : response.body().getMessage()) {
                        listFeed.add(message);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        FeedFragment feedFragment = new FeedFragment().newInstance(listFeed);
                        fragmentTransaction.replace(R.id.frame, feedFragment);
                        fragmentTransaction.commit();

                    }
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    Toast.makeText(BlogHomeActivity.this, "Error in hit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BlogFeedPOJO> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });


    }


    private void showFileChooser() {

        Intent CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        uri = FileProvider.getUriForFile(BlogHomeActivity.this, "com.buffet_user.provider", file);

        getSharedPreferences("Temp", MODE_PRIVATE).edit().putString("Uri", file.toString()).apply();
        CamIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        CamIntent.putExtra("return-data", false);
        CamIntent.putExtra("outputX", 960);
        CamIntent.putExtra("outputY", 540);
        CamIntent.putExtra("aspectX", 16);
        CamIntent.putExtra("aspectY", 9);
        CamIntent.putExtra("scale", true);
        CamIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(CamIntent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA && resultCode == RESULT_OK) {
            uri = Uri.parse(getSharedPreferences("Temp", MODE_PRIVATE).getString("Uri", ""));

            upload_to_cloud(uri.toString());

        }


    }


    private void upload_to_cloud(String path) {


        MediaManager.get().upload(path).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                // your code here
                Toast.makeText(BlogHomeActivity.this, "Uploading", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                // example code starts here
                Double progress = (double) bytes / totalBytes;
                // post progress to app UI (e.g. progress bar, notification)
                // example code ends here
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Intent intent = openActivity(BlogHomeActivity.this, PostFeedActivity.class);
                intent.putExtra("url", resultData.get("url").toString());
                startActivity(intent);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Toast.makeText(BlogHomeActivity.this, "error" + error.getDescription(), Toast.LENGTH_SHORT).show();
                Log.e("Error", error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.e("ErrorRE", error.getDescription());

            }
        }).dispatch();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

