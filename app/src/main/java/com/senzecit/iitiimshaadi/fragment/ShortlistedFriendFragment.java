package com.senzecit.iitiimshaadi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ShortlistFriendAdapter;

/**
 * Created by ravi on 15/11/17.
 */

public class ShortlistedFriendFragment extends Fragment {
    RecyclerView mRecyclerView;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_shortlisted_friend,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        ShortlistFriendAdapter adapter = new ShortlistFriendAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    private void init(){
        mRecyclerView = view.findViewById(R.id.shortlistFriendListRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
