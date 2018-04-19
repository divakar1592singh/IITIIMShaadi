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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.invited.AllInvitedFriend;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.invited.UserDetail;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.List;

/**
 * Created by ravi on 15/11/17.
 */

public class InvitedFriendAdapter extends RecyclerView.Adapter<InvitedFriendAdapter.MyViewHolder> {


    Context mContext;
    AppPrefs prefs;
    List<AllInvitedFriend> allFriendList;

    public InvitedFriendAdapter(Context mContext, List<AllInvitedFriend> allFriendList){

        this.mContext = mContext;
        this.allFriendList = allFriendList;
        prefs = AppPrefs.getInstance(mContext);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout mSubsLayout, mPaidLayout;
        ImageView mFriendIV;
        TextView mUserIdTV, mUserNameTv, mReligionTv, mEducationTV, mJobLocTv;

        public MyViewHolder(View itemView) {
            super(itemView);

            mSubsLayout = itemView.findViewById(R.id.idSubsLayout);
            mPaidLayout = itemView.findViewById(R.id.idPaidLayout);

            mFriendIV = itemView.findViewById(R.id.idProfileCIV);
            mUserIdTV = itemView.findViewById(R.id.idUserIDTV);
            mUserNameTv = itemView.findViewById(R.id.idUserNameTV);
            mReligionTv = itemView.findViewById(R.id.idReligionTV);
            mEducationTV = itemView.findViewById(R.id.idEducationTV);
            mJobLocTv = itemView.findViewById(R.id.idJobTv);


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

        String userType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
        if (userType.equalsIgnoreCase("paid_subscriber_viewer")) {
            holder.mPaidLayout.setVisibility(View.VISIBLE);
            holder.mSubsLayout.setVisibility(View.GONE);
        }else if (userType.equalsIgnoreCase("subscriber_viewer")) {
            holder.mPaidLayout.setVisibility(View.GONE);
            holder.mSubsLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return allFriendList.size();
    }


    public String setCollege(UserDetail userDetail){

        if(TextUtils.isEmpty(userDetail.getPostGraduation())){
            return new StringBuilder(userDetail.getGraduation()).append(", ").append(userDetail.getGraduationCollege()).toString();
        }else {
            return new StringBuilder(userDetail.getPostGraduation()).append(", ").append(userDetail.getPostGraduationCollege()).toString();
        }
    }



}
