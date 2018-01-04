package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.fragment.ChatUserFragment;

import java.util.List;

/**
 * Created by ravi on 23/11/17.
 */

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.MyViewHolder> {

    Context mContext;
    List<String> list;
    LayoutInflater inflater;

    public ChatUserAdapter(Context mContext, List<String> list){
    this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.list = list;
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

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Current Selection : "+getItemCount(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
