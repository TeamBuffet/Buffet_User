package com.buffet_user.fragment.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.adapter.ReviewFeedsAdapter;
import com.buffet_user.pojo.MessageFeedPOJO;

import java.util.ArrayList;

/**
 * Created by Ankit on 29/10/17.
 */

public class FeedFragment extends Fragment {

    private View parentView;
    private RecyclerView rvFeeds;
    private ArrayList<MessageFeedPOJO> listFeeds;
    private ReviewFeedsAdapter adapter;

    public FeedFragment() {

    }

    public static FeedFragment newInstance(ArrayList<MessageFeedPOJO> list) {
        //Default
        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("listFeed", list);
        feedFragment.setArguments(bundle);
        return feedFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listFeeds = (ArrayList<MessageFeedPOJO>) (getArguments() != null ? getArguments().getParcelableArrayList("listFeed") : "");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_feed, container, false);
        populateFeeds();
        return parentView;

    }

    private void populateFeeds() {
        rvFeeds=(RecyclerView)parentView.findViewById(R.id.rvFeeds);
        adapter = new ReviewFeedsAdapter(getActivity(), listFeeds);
        rvFeeds.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFeeds.setLayoutManager(layoutManager);
    }
}
