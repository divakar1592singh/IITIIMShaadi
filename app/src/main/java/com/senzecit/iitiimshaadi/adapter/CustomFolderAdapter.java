package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.UserDetail;
import com.senzecit.iitiimshaadi.viewController.ProfileActivity;

import java.util.List;

/**
 * Created by ravi on 15/11/17.
 */

public class CustomFolderAdapter extends RecyclerView.Adapter<CustomFolderAdapter.MyViewHolder>{

    Context mContext;
    List<UserDetail> userList;
    public CustomFolderAdapter(Context mContext, List<UserDetail> userList){
        this.mContext = mContext;
        this.userList = userList;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mViewProfileLayout;
        Button mAcceptBtn, mCancelReqBtn, mUnshortlistedBtn;
        TextView mUserIDTV, mNameTV, mReligionTV, mEducationTV, mJobInfoTV;
        public MyViewHolder(View itemView) {
            super(itemView);
            mViewProfileLayout = itemView.findViewById(R.id.idViewProfileLayout);

            mUserIDTV = itemView.findViewById(R.id.idUserIDTV);
            mNameTV = itemView.findViewById(R.id.idNameTV);
            mReligionTV = itemView.findViewById(R.id.idReligionTV);
            mEducationTV = itemView.findViewById(R.id.idEducationTV);
            mJobInfoTV = itemView.findViewById(R.id.idJobInfoTV);

            mAcceptBtn = itemView.findViewById(R.id.idAcceptBtn);
            mCancelReqBtn = itemView.findViewById(R.id.idCancelReqBtn);
            mUnshortlistedBtn = itemView.findViewById(R.id.idUnShortListedBtn);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_folder_adapter_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            holder.mUserIDTV.setText(String.valueOf(userList.get(position).getUserId()));
            holder.mNameTV.setText(userList.get(position).getName());
            holder.mReligionTV.setText(userList.get(position).getReligion());
            holder.mEducationTV.setText(getEducationData(userList, position));
            holder.mJobInfoTV.setText(userList.get(position).getNameOfCompany());
        }catch (NullPointerException npe){
            Log.e("RequestFriend", "# Error : "+npe, npe);
        }

        holder.mViewProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ProfileActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public String getEducationData(List<UserDetail> userList, int position){

        if(userList.get(position).getPostGraduation() != null ){
           return userList.get(position).getPostGraduation()+", "+userList.get(position).getPostGraduationCollege();
        }else {
            return userList.get(position).getGraduation()+", "+userList.get(position).getGraduationCollege();
        }
    }

}
