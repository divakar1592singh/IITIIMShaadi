package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.my_friends.AllFriend;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.my_friends.UserDetail;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CircleImageView;

import java.util.List;

/**
 * Created by ravi on 15/11/17.
 */

public class SubsViewMyFriendAdapter extends RecyclerView.Adapter<SubsViewMyFriendAdapter.MyViewHolder> {

    Context mContext;
    List<AllFriend> allFriendList;

    public SubsViewMyFriendAdapter(Context mContext, List<AllFriend> allFriendList){

        this.mContext = mContext;
        this.allFriendList = allFriendList;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView mCircleIV;
        TextView mUserIdTV, mUserNameTv, mReligionTv, mEducationTV, mJobLocTv;
        Button mProfileBtn, mChatBtn;
        public MyViewHolder(View itemView) {
            super(itemView);

            mCircleIV = itemView.findViewById(R.id.idProfileCIV);
            mUserIdTV = itemView.findViewById(R.id.idUserIDTV);
            mUserNameTv = itemView.findViewById(R.id.idUserNameTV);
            mReligionTv = itemView.findViewById(R.id.idReligionTV);
            mEducationTV = itemView.findViewById(R.id.idEducationTV);
            mJobLocTv = itemView.findViewById(R.id.idJobTv);

            mProfileBtn = (Button)itemView.findViewById(R.id.idViewProfileBtn);
            mChatBtn = (Button)itemView.findViewById(R.id.idChatBtn);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_item_subs_viewer,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        UserDetail userDetail = allFriendList.get(position).getUserDetail();

        try {
            String userId = String.valueOf(userDetail.getUserId());
            String partUrl = userDetail.getProfileImage();
            Glide.with(mContext).load(CONSTANTS.IMAGE_AVATAR_URL + userId + "/" + partUrl).error(R.drawable.profile_img1).into(holder.mCircleIV);
        }catch (NullPointerException npe){
            Log.e("TAG", " #Error : "+npe, npe);
        }

        holder.mUserIdTV.setText(String.valueOf(userDetail.getUserId()));
        holder.mUserNameTv.setText(userDetail.getName());
        holder.mReligionTv.setText(userDetail.getReligion());
        holder.mEducationTV.setText(setCollege(userDetail));
        holder.mJobLocTv.setText(userDetail.getNameOfCompany());

        holder.mProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        holder.mChatBtn.setOnClickListener(new View.OnClickListener() {
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
            return new StringBuilder(userDetail.getGraduation()).append(", ").append(userDetail.getGraduationCollege()).toString();
        }else {
          return new StringBuilder(userDetail.getPostGraduation()).append(", ").append(userDetail.getPostGraduationCollege()).toString();
        }
    }

}
