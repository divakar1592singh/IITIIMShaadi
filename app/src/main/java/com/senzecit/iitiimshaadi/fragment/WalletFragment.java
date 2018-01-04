package com.senzecit.iitiimshaadi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.activity.WalletActivity;
import com.senzecit.iitiimshaadi.adapter.WalletTransactionAdapter;

/**
 * Created by ravi on 10/11/17.
 */

public class WalletFragment extends Fragment implements View.OnClickListener {
    View view;
    RecyclerView mRecyclerView;
    ScrollView mWalletScrollView;
    Button mReferFriendBtn;
    WalletFragmentCommunicator communicator;

    public void setWalletFragmentCommunicator(WalletFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        WalletTransactionAdapter adapter = new WalletTransactionAdapter();
        mRecyclerView.setAdapter(adapter);

        mReferFriendBtn.setOnClickListener(this);
    }

    private void init(){

        mWalletScrollView = (ScrollView)view.findViewById(R.id.walletScrollView);
        mWalletScrollView.smoothScrollTo(0,0);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.transactionListRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mReferFriendBtn = view.findViewById(R.id.referFriendBTN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.referFriendBTN:
                communicator.referralFriend();
                break;
        }
    }

    public interface WalletFragmentCommunicator{
        void referralFriend();
    }
}
