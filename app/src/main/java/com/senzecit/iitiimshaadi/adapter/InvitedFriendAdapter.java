package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.invited.AllInvitedFriend;
import com.senzecit.iitiimshaadi.model.api_response_model.friends.invited.UserDetail;
import com.senzecit.iitiimshaadi.model.api_response_model.my_profile.MyProfileResponse;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.viewController.OtherProfileActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        TextView mUserIdTV, mUserNameTv, mReligionTv, mEducationTV, mJobLocTv;
        Button mCancelReqBtn, mShortlistBtn, mViewProfileBtn;
        public MyViewHolder(View itemView) {
            super(itemView);

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
