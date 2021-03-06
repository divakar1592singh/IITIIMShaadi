package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.DateToAgeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.Message;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 23/11/17.
 */

public class AllInterestAdapter extends RecyclerView.Adapter<AllInterestAdapter.MyViewHolder> {

    Context mContext;
    JSONArray jsonArray;
    LayoutInflater inflater;
    AppPrefs prefs;

    public AllInterestAdapter(Context mContext, JSONArray jsonArray){
    this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.jsonArray = jsonArray;
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
        View itemView = inflater.inflate(R.layout.all_interest_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        prefs = AppController.getInstance().getPrefs();

//        UserDetail userDetail = list.get(position).getUserDetail();
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            holder.mUsernameTV.setText(jsonObject.getJSONObject("user_detail").getString("name"));
            holder.mMessageTV.setText(jsonObject.getJSONObject("user_detail").getString("role"));
            formattedDate(holder.mTimeSpentTV, jsonObject.getJSONObject("user_detail").getString("birth_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        holder.mMessageTV.setText(userDetail.getNameOfCompany());

//        formattedDate(holder.mTimeSpentTV, userDetail.getBirthDate());
 /*       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Current Selection : "+userDetail.getUserId(), Toast.LENGTH_SHORT).show();
                String userID = String.valueOf(userDetail.getUserId());
                if(userID.length()> 0){
                    prefs.putString(CONSTANTS.OTHER_USERID, userID);
                    Navigator.getClassInstance().navigateToActivity(mContext, OtherProfileActivity.class);
                }else {

                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public void formattedDate(TextView tv, String _date) {

        try {

            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<DateToAgeResponse> call = apiInterface.dateToAge(_date);
//            ProgressClass.getProgressInstance().showDialog(mContext);
            call.enqueue(new Callback<DateToAgeResponse>() {
                @Override
                public void onResponse(Call<DateToAgeResponse> call, Response<DateToAgeResponse> response) {
                    if (response.isSuccessful()) {
//                        ProgressClass.getProgressInstance().stopProgress();
                        Message message = response.body().getMessage();
                        try {
                            if (message != null) {
                                tv.setText(response.body().getAge());
                            } else {
                                AlertDialogSingleClick.getInstance().showDialog(mContext, "Alert", " Check Religion selected!");
                            }
                        } catch (NullPointerException npe) {
                            Log.e("TAG", "#Error : " + npe, npe);
                            AlertDialogSingleClick.getInstance().showDialog(mContext, "Alert", " Date Format not correct");
                        }
                    }
                }

                @Override
                public void onFailure(Call<DateToAgeResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (NullPointerException npe) {
            Log.e("TAG", "#Error : " + npe, npe);
            AlertDialogSingleClick.getInstance().showDialog(mContext, "Alert", "Religion not seletced");
        }

    }



}
