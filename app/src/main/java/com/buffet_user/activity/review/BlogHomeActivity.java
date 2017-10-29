package com.buffet_user.activity.review;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.buffet_user.fragment.review.FeedFragment;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Ankit on 28/10/17.
 */

public class BlogHomeActivity extends BaseActivity {


    Toolbar toolbar;
    TextView txtTitle;
    private int permissionCheck2;
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 245;
    private int PICK_IMAGE_REQUEST = 1;
    private Cursor cursor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        txtTitle.setText("Feeds");
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FeedFragment feedFragment = new FeedFragment();
        fragmentTransaction.replace(R.id.frame, feedFragment);
        fragmentTransaction.commit();
        initCloudinary();
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.bottomNav);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.mipmap.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("Loved", R.mipmap.ic_love));
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.colorPrimary));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                permissionCheck2 = ContextCompat.checkSelfPermission(BlogHomeActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);


                Toast.makeText(BlogHomeActivity.this, "Upload", Toast.LENGTH_SHORT).show();
                if (permissionCheck2 == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser();
                } else

                {
                    ActivityCompat.requestPermissions(BlogHomeActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_STORAGE);
                }

            }


            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemName.equals("Home")) {
                    FeedFragment feedFragment = new FeedFragment();
                    fragmentTransaction.replace(R.id.frame, feedFragment);
                    fragmentTransaction.commit();

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if (itemName.equals("Home")) {
                    FeedFragment feedFragment = new FeedFragment();
                    fragmentTransaction.replace(R.id.frame, feedFragment);
                    fragmentTransaction.commit();

                }

            }
        });


    }


    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            upload_to_cloud(getPathFromURI(BlogHomeActivity.this,fileUri));

        }


    }
    public String getPathFromURI(Context context,Uri uri) {
            String filePath = "";
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = { MediaStore.Images.Media.DATA };

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{ id }, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
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
                Double progress = (double) bytes/totalBytes;
                // post progress to app UI (e.g. progress bar, notification)
                // example code ends here
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Toast.makeText(BlogHomeActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Toast.makeText(BlogHomeActivity.this, "error"+error.getDescription(), Toast.LENGTH_SHORT).show();
                Log.e("Error",error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.e("ErrorRE",error.getDescription());

            }}).dispatch();

    }




}

