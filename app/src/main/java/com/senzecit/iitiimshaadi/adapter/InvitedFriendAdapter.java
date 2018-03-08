package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.invited.AllInvitedFriend;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.invited.UserDetail;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;

import java.util.List;

/**
 * Created by ravi on 15/11/17.
 */

public class InvitedFriendAdapter extends RecyclerView.Adapter<InvitedFriendAdapter.MyViewHolder> implements View.OnClickListener{


    Context mContext;
    List<AllInvitedFriend> allFriendList;

    public InvitedFriendAdapter(Context mContext, List<AllInvitedFriend> allFriendList){

        this.mContext = mContext;
        this.allFriendList = allFriendList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mFriendIV;
        TextView mUserIdTV, mUserNameTv, mReligionTv, mEducationTV, mJobLocTv;
        Button mCancelReqBtn, mShortlistBtn, mViewProfileBtn;
        public MyViewHolder(View itemView) {
            super(itemView);

            mFriendIV = itemView.findViewById(R.id.idFriendIV);
            mUserIdTV = itemView.findViewById(R.id.idUserIDTV);
            mUserNameTv = itemView.findViewById(R.id.idUserNameTV);
            mReligionTv = itemView.findViewById(R.id.idReligionTV);
            mEducationTV = itemView.findViewById(R.id.idEducationTV);
            mJobLocTv = itemView.findViewById(R.id.idJobTv);

            mCancelReqBtn = itemView.findViewById(R.id.idCancelReqBtn);
            mShortlistBtn = itemView.findViewById(R.id.idShortlistBtn);
            mViewProfileBtn = itemView.findViewById(R.id.idViewProfileBtn);


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invited_friend_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        UserDetail userDetail = allFriendList.get(position).getUserDetail();

        try {
            String userId = String.valueOf(userDetail.getUserId());
            String partUrl = userDetail.getProfileImage();
            Glide.with(mContext).load(CONSTANTS.IMAGE_AVATAR_URL + userId + "/" + partUrl).error(R.drawable.profile_img1).into(holder.mFriendIV);
        }catch (NullPointerException npe){
            Log.e("TAG", " #Error : "+npe, npe);
        }

        holder.mUserIdTV.setText(String.valueOf(userDetail.getUserId()));
        holder.mUserNameTv.setText(userDetail.getName());
        holder.mReligionTv.setText(userDetail.getReligion());
        holder.mEducationTV.setText(setCollege(userDetail));
        holder.mJobLocTv.setText(userDetail.getNameOfCompany());

        holder.mCancelReqBtn.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return allFriendList.size();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.idCancelReqBtn:

                break;
            case R.id.idShortlistBtn:

                break;
            case R.id.idViewProfileBtn:

                break;

        }
    }


    public String setCollege(UserDetail userDetail){

        if(TextUtils.isEmpty(userDetail.getPostGraduation())){
            return new StringBuilder(userDetail.getGraduation()).append(", "+userDetail.getGraduationCollege()).toString();
        }else {
            return new StringBuilder(userDetail.getPostGraduation()).append(", "+userDetail.getPostGraduationCollege()).toString();
        }
    }



}
