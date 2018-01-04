package com.senzecit.iitiimshaadi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.RequestedFriendAdapter;

import java.util.ArrayList;

/**
 * Created by ravi on 15/11/17.
 */

public class RequestedFriendFragment extends Fragment {
    RecyclerView mRecyclerView;
    View view;

    /*ArrayList<String> myFriendList;
    RequestedFriendFragment.OnRequestedFriendListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (RequestedFriendFragment.OnRequestedFriendListener) activity;

    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_requested_friend,container,false);
        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        myFriendList = new ArrayList<>();
//        myFriendList.add("nsp");
//        myFriendList.add("cp");
//        myFriendList.add("mango");
//        myFriendList.add("nsp");
//        myFriendList.add("cp");
//
//        listener.onFragmentSetRequestedFriend(myFriendList);
//
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        RequestedFriendAdapter adapter = new RequestedFriendAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
    }

    private void init(){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.requestFriendListRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

//    public interface OnRequestedFriendListener {
//        public void onFragmentSetRequestedFriend(ArrayList<String> requestAL);
//    }

}
