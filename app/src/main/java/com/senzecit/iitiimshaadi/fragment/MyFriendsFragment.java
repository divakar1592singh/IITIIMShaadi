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
import com.senzecit.iitiimshaadi.activity.FriendsActivity;
import com.senzecit.iitiimshaadi.adapter.MyFriendsAdapter;

import java.util.ArrayList;

/**
 * Created by ravi on 15/11/17.
 */

public class MyFriendsFragment extends Fragment {
    RecyclerView mRecyclerView;
    View view;
  /*  ArrayList<String> myFriendList;
    OnMyFriendListener  listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
         listener = (OnMyFriendListener) activity;

    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_my_friends,container,false);
        return view;
    }

 /*   @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myFriendList = new ArrayList<>();
        myFriendList.add("nsp");
        myFriendList.add("cp");
        myFriendList.add("mango");
        myFriendList.add("nsp");
        myFriendList.add("cp");
        myFriendList.add("mango");

        listener.onFragmentSetSetMyFriend(myFriendList);

    }
*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        MyFriendsAdapter adapter = new MyFriendsAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    private void init(){
        mRecyclerView = view.findViewById(R.id.myFriendListRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

  /*  public interface OnMyFriendListener {
        public void onFragmentSetSetMyFriend(ArrayList<String> ar);
    }*/
}
