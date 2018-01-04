package com.senzecit.iitiimshaadi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.senzecit.iitiimshaadi.R;

/**
 * Created by ravi on 10/11/17.
 */

public class ReferFriendFragment extends Fragment implements View.OnClickListener {

    View view;
    LinearLayout mReferFreindDtlsLL;
    FrameLayout mReferFriendFL;
    boolean referDetails = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_refer_friend,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        mReferFriendFL.setOnClickListener(this);
    }

    private void init(){
        mReferFriendFL = view.findViewById(R.id.referFreindFL);
        mReferFreindDtlsLL = view.findViewById(R.id.referFriendDetailsLL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.referFreindFL:
                if(referDetails){
                    mReferFreindDtlsLL.setVisibility(View.GONE);
                    referDetails = false;
                }else{
                    mReferFreindDtlsLL.setVisibility(View.VISIBLE);
                    referDetails = true;
                }
                break;
        }
    }
}
