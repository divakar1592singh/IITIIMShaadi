package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.chat.SocketSingleChatActivity;
import com.senzecit.iitiimshaadi.model.api_response_model.chat_user.Result;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.List;

/**
 * Created by ravi on 23/11/17.
 */

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.MyViewHolder> {

    Context mContext;
    List<Result> chatList;
    LayoutInflater inflater;
    AppPrefs prefs;

    public ChatUserAdapter(Context mContext, List<Result> chatList){
    this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.chatList = chatList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mProfileIV;
        TextView mUsernameTV, mTimeSpentTV, mMessageTV;
        public MyViewHolder(View itemView) {
            super(itemView);

            mProfileIV = itemView.findViewById(R.id.iv_profile_pic);
            mUsernameTV = itemView.findViewById(R.id.tv_username);
            mTimeSpentTV = itemView.findViewById(R.id.tv_time_notification);
            mMessageTV = itemView.findViewById(R.id.tv_message);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.chatuser_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        prefs = AppController.getInstance().getPrefs();
        try {
            Integer userId = chatList.get(position).getUserId();

            String profileUrl = chatList.get(position).getProfileImage();
            String netProfileUri = CONSTANTS.IMAGE_AVATAR_URL+userId+"/"+profileUrl;

            if (!TextUtils.isEmpty(netProfileUri)) {
                Glide.with(mContext).load(netProfileUri).error(R.drawable.profile_img1).into(holder.mProfileIV);
            }

            String userName = chatList.get(position).getName();
            if (!TextUtils.isEmpty(userName)) {
                holder.mUsernameTV.setText(userName);
            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Current Selection : "+getItemCount(), Toast.LENGTH_SHORT).show();

                String userName = chatList.get(position).getName();
                String userId = String.valueOf(chatList.get(position).getUserId());
                prefs.putString(CONSTANTS.OTHER_USERID, userId);
                prefs.putString(CONSTANTS.OTHER_USERNAME, userName);

                mContext.startActivity(new Intent(mContext, SocketSingleChatActivity.class));

            }
        });
        }catch (NullPointerException npe){
            Log.e("TAG", " #Error ; "+npe, npe );
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

}
