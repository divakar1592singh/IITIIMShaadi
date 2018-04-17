package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.UserDetail;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.OtherProfileActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 15/11/17.
 */

public class CustomFolderAdapter extends RecyclerView.Adapter<CustomFolderAdapter.MyViewHolder>{

    Context mContext;
    List<UserDetail> userList;
    AppPrefs prefs;

    public CustomFolderAdapter(Context mContext, List<UserDetail> userList){
        this.mContext = mContext;
        this.userList = userList;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mViewProfileLayout;
        CircleImageView mImageView;
        Button mAcceptBtn, mCancelReqBtn, mUnshortlistedBtn;
        TextView mUserIDTV, mNameTV, mReligionTV, mEducationTV, mJobInfoTV;
        public MyViewHolder(View itemView) {
            super(itemView);
            mViewProfileLayout = itemView.findViewById(R.id.idViewProfileLayout);

            mImageView = itemView.findViewById(R.id.idImageView);
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

        prefs = AppController.getInstance().getPrefs();
        try {


            try {
                String userId = String.valueOf(userList.get(position).getUserId());
                String partUrl = userList.get(position).getProfileImage();
                Glide.with(mContext).load(CONSTANTS.IMAGE_AVATAR_URL + userId + "/" + partUrl).error(R.drawable.profile_img1).into(holder.mImageView);
            }catch (NullPointerException npe){
                Log.e("TAG", " #Error : "+npe, npe);
            }

            holder.mUserIDTV.setText(String.valueOf(userList.get(position).getUserId()));
            holder.mNameTV.setText(userList.get(position).getName());
            holder.mReligionTV.setText(userList.get(position).getReligion());
            holder.mEducationTV.setText(getEducationData(userList, position));
            holder.mJobInfoTV.setText(userList.get(position).getNameOfCompany());
        }catch (NullPointerException npe){
            Log.e("RequestFriend", "# Error : "+npe, npe);
        }

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = "";
                userID = String.valueOf(userList.get(position).getUserId());
                if (userID.length() > 0) {
                    prefs.putString(CONSTANTS.OTHER_USERID, userID);
                    Navigator.getClassInstance().navigateToActivity(mContext, OtherProfileActivity.class);
                }
            }
        });

        holder.mAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = String.valueOf(userList.get(position).getUserId());
                callWebServiceForManipulateFriend(UserDefinedKeyword.ADD.toString(), userId);

            }
        });
        holder.mCancelReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = String.valueOf(userList.get(position).getUserId());
                callWebServiceForManipulateFriend(UserDefinedKeyword.CANCEL.toString(), userId);

            }
        });
        holder.mUnshortlistedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = String.valueOf(userList.get(position).getUserId());
                callWebServiceForManipulateFriend(UserDefinedKeyword.UNSHORTLIST.toString(), userId);

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

    //

    /** API - Friend Manipulation Task */
    private void callWebServiceForManipulateFriend(String typeOf, String otherUserID)
    {
        if(NetworkClass.getInstance().checkInternet(mContext) == true){

            ProgressClass.getProgressInstance().showDialog(mContext);
            Call<AddFolderResponse> call = callManipulationMethod(typeOf, otherUserID);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    try {
                        if (response.isSuccessful()) {
                            if(response.body().getMessage().getSuccess().length() > 0){
                                String msg = response.body().getMessage().getSuccess();
                                AlertDialogSingleClick.getInstance().showDialog(mContext, "Friend Alert", msg);
                            }
                        }
                    }catch (NullPointerException npe)
                    {
                        Log.e("TAG", "Error : "+npe, npe);
                    }
                }

                @Override
                public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(mContext);
        }
    }
    public Call<AddFolderResponse> callManipulationMethod(String typeOf, String friend_user)
    {
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);

        if(typeOf.equalsIgnoreCase(UserDefinedKeyword.ADD.toString())){
            return apiInterface.serviceAcceptFriend(token, friend_user);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.REMOVE.toString())){
            return apiInterface.serviceRemoveFriend(token, friend_user);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.CANCEL.toString())){
            return apiInterface.serviceCancelFriend(token, friend_user);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.SHORTLIST.toString())){
            return apiInterface.serviceShortlistFriend(token, friend_user);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.UNSHORTLIST.toString())){
            return apiInterface.serviceUnShortlistFriend(token, friend_user);
        }else {
//            Toast.makeText(FriendsActivity.this, "Default Called", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

}
