package com.buffet_user.activity.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.fragment.review.FeedFragment;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

/**
 * Created by Ankit on 28/10/17.
 */

public class BlogHomeActivity extends AppCompatActivity{


    Toolbar toolbar;
    TextView txtTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_home);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtTitle=(TextView)toolbar.findViewById(R.id.toolbar_title);
        txtTitle.setText("Feeds");
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FeedFragment feedFragment = new FeedFragment();
        fragmentTransaction.replace(R.id.frame, feedFragment);
        fragmentTransaction.commit();

        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.bottomNav);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.mipmap.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("Loved", R.mipmap.ic_love));
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.colorPrimary));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {

                Toast.makeText(BlogHomeActivity.this, "Upload", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if(itemName.equals("Home")){
                    FeedFragment feedFragment = new FeedFragment();
                    fragmentTransaction.replace(R.id.frame, feedFragment);
                    fragmentTransaction.commit();

                    }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if(itemName.equals("Home")){
                    FeedFragment feedFragment = new FeedFragment();
                    fragmentTransaction.replace(R.id.frame, feedFragment);
                    fragmentTransaction.commit();

                }

            }
        });


    }
}
