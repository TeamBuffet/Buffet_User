package com.buffet_user.fragment.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buffet_user.R;

/**
 * Created by Ankit on 29/10/17.
 */

public class FeedFragment extends Fragment {

    private View parentView;
    private RecyclerView rvFeeds;
    public FeedFragment(){
        //Default
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView=inflater.inflate(R.layout.fragment_feed,container,false);
        return parentView;

    }
}
