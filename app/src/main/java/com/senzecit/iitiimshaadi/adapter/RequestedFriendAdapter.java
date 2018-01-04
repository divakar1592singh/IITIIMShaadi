package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.activity.ProfileActivity;

/**
 * Created by ravi on 15/11/17.
 */

public class RequestedFriendAdapter extends RecyclerView.Adapter<RequestedFriendAdapter.MyViewHolder>{

    Context mContext;
    public RequestedFriendAdapter(Context mContext){
        this.mContext = mContext;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mViewProfileLayout;
        Button mAcceptBtn, mCancelReqBtn, mUnshortlistedBtn;
        public MyViewHolder(View itemView) {
            super(itemView);
            mViewProfileLayout = itemView.findViewById(R.id.idViewProfileLayout);

            mAcceptBtn = itemView.findViewById(R.id.idAcceptBtn);
            mCancelReqBtn = itemView.findViewById(R.id.idCancelReqBtn);
            mUnshortlistedBtn = itemView.findViewById(R.id.idUnShortListedBtn);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_recieved_friend_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.mViewProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ProfileActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
