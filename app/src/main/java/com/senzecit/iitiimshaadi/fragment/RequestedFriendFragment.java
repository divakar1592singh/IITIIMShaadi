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
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.viewController.CustomFolderActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ravi on 15/11/17.
 */

public class RequestedFriendFragment extends Fragment {
    RecyclerView mRecyclerView;
    View view;

    List<String> myFriendList;
    RequestedFriendFragment.OnRequestedFriendListener listener;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public RequestedFriendFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RequestedFriendFragment newInstance(String text) {
        RequestedFriendFragment fragment = new RequestedFriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, text);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (RequestedFriendFragment.OnRequestedFriendListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_requested_friend,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myFriendList = new ArrayList<>();
        myFriendList.add("nsp");
        myFriendList.add("cp");
        myFriendList.add("mango");
        myFriendList.add("nsp");
        myFriendList.add("cp");

        listener.onFragmentSetRequestedFriend(myFriendList);


//        AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Dekh", getArguments().getString(ARG_SECTION_NUMBER));
// AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Dekh", new CustomFolderActivity().getViewPagePosition());


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

       /* RequestedFriendAdapter adapter = new RequestedFriendAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);*/
    }

    private void init(){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.requestFriendListRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public interface OnRequestedFriendListener {
        void onFragmentSetRequestedFriend(List<String> requestAL);
    }

}
