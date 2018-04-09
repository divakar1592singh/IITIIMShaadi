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
import com.senzecit.iitiimshaadi.model.api_response_model.notification.all.GetAllNotificaiton;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.OtherProfileActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ravi on 23/11/17.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {

    Context mContext;
    List<GetAllNotificaiton> list;
    LayoutInflater inflater;
    AppPrefs prefs;

    public NotificationsAdapter(Context mContext, List<GetAllNotificaiton> list){
    this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.list = list;
        prefs = AppController.getInstance().getPrefs();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mProfileIV;
        TextView mUsernameTV, mTimeSpentTV, mMessageTV;
        public MyViewHolder(View itemView) {
            super(itemView);

            mProfileIV = itemView.findViewById(R.id.iv_profile_pic);
            mUsernameTV = itemView.findViewById(R.id.tv_type);
            mTimeSpentTV = itemView.findViewById(R.id.tv_time_notification);
            mMessageTV = itemView.findViewById(R.id.tv_message);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.notificationuser_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        mProfileIV = itemView.findViewById(R.id.iv_profile_pic);
        holder.mUsernameTV.setText(list.get(position).getType());
        holder.mTimeSpentTV.setText(getDate(list.get(position).getDateAdded()));
        holder.mMessageTV.setText(list.get(position).getMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Toast.makeText(mContext, "Current Selection : "+getItemCount(), Toast.LENGTH_SHORT).show();
                String userID = String.valueOf(list.get(position).getId());
                if(userID.length()> 0){
                    prefs.putString(CONSTANTS.OTHER_USERID, userID);
                    Navigator.getClassInstance().navigateToActivity(mContext, OtherProfileActivity.class);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static String getDate(String _Date){

//        String _Date = "2010-09-29 08:45:22";
//        String _Date = "2018-05-02T00:00:00+0000";

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmt2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = fmt.parse(_Date);
            return fmt2.format(date);
        }
        catch(ParseException pe) {

            return "Date";
        }

    }
}
