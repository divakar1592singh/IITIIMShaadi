package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.shortlisted.AllShortlistedFriend;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.shortlisted.UserDetail;
import com.senzecit.iitiimshaadi.utils.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by ravi on 15/11/17.
 */

public class ShortlistFriendAdapter extends RecyclerView.Adapter<ShortlistFriendAdapter.MyViewHolder> {

    Context mContext;
    List<AllShortlistedFriend> allFriendList;

    public ShortlistFriendAdapter(Context mContext, List<AllShortlistedFriend> allFriendList){

        this.mContext = mContext;
        this.allFriendList = allFriendList;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mUserIdTV, mUserNameTv, mReligionTv, mEducationTV, mJobLocTv;
        Button mAddFriendBtn, mUnShortlistFriendBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            mUserIdTV = itemView.findViewById(R.id.idUserIDTV);
            mUserNameTv = itemView.findViewById(R.id.idUserNameTV);
            mReligionTv = itemView.findViewById(R.id.idReligionTV);
            mEducationTV = itemView.findViewById(R.id.idEducationTV);
            mJobLocTv = itemView.findViewById(R.id.idJobTv);

            mAddFriendBtn = itemView.findViewById(R.id.idAddFriendBtn);
            mUnShortlistFriendBtn = itemView.findViewById(R.id.idUnShortlistBtn);

            itemView.clearFocus();
        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shortlist_friend_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        UserDetail userDetail = allFriendList.get(position).getUserDetail();

        holder.mUserIdTV.setText(String.valueOf(userDetail.getUserId()));
        holder.mUserNameTv.setText(userDetail.getName());
        holder.mReligionTv.setText(userDetail.getReligion());
        holder.mEducationTV.setText(setCollege(userDetail));
        holder.mJobLocTv.setText(userDetail.getNameOfCompany());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return allFriendList.size();
    }


    public String setCollege(UserDetail userDetail){

        if(TextUtils.isEmpty(userDetail.getPostGraduation())){
            return new StringBuilder(userDetail.getGraduation()).append(", "+userDetail.getGraduationCollege()).toString();
        }else {
            return new StringBuilder(userDetail.getPostGraduation()).append(", "+userDetail.getPostGraduationCollege()).toString();
        }
    }


}
