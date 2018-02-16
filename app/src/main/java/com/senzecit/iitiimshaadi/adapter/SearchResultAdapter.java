package com.senzecit.iitiimshaadi.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.FolderListModelResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.MyMeta;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.viewController.CustomFoldersActivity;
import com.senzecit.iitiimshaadi.viewController.FriendsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 14/11/17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> {

    Context mContext;
    List<Query> queryList;
    public SearchResultAdapter(Context mContext, List<Query> queryList){
        this.mContext = mContext;
        this.queryList = queryList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDietTxt, mEmploymentTxt, mCompanyTxt, mHeightTxt, mReligiontxt, mEducationTxt;
        Button mAddFriendBtn,mMoveTo;
        public MyViewHolder(View itemView) {
            super(itemView);

            mDietTxt = itemView.findViewById(R.id.idDietTV);
            mEmploymentTxt = itemView.findViewById(R.id.idEmploymentTV);
            mCompanyTxt = itemView.findViewById(R.id.idCompanyTV);
            mHeightTxt = itemView.findViewById(R.id.idHeightTV);
            mReligiontxt = itemView.findViewById(R.id.idReligionTV);
            mEducationTxt = itemView.findViewById(R.id.idEducationTV);

            mAddFriendBtn = itemView.findViewById(R.id.idAddFriendBtn);
            mMoveTo = itemView.findViewById(R.id.moveToBtm);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item,parent,false);
        return new SearchResultAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mDietTxt.setText(queryList.get(position).getDiet());
        holder.mEmploymentTxt.setText(queryList.get(position).getWorkingAs());
        holder.mCompanyTxt.setText(queryList.get(position).getNameOfCompany());
        holder.mHeightTxt.setText(queryList.get(position).getHeight());
        holder.mReligiontxt.setText(queryList.get(position).getReligion());
        holder.mEducationTxt.setText(queryList.get(position).getHighestEducation() );

        holder.mAddFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = String.valueOf(queryList.get(position).getUserId());
                callWebServiceForManipulatePartner(UserDefinedKeyword.ADD.toString(), userId, null);

            }
        });
//        holder.mPartnerPref.setOnClickListener(this);
        holder.mMoveTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = String.valueOf(queryList.get(position).getUserId());
                callWebServiceForCustomFolder(UserDefinedKeyword.MOVETO.toString(), userId);

            }
        });
    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

/*    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idAddFriendBtn:
                callWebServiceForManipulateFriend(String typeOf, String friend, String folder)
                break;
            case R.id.moveToBtm:

            break;
        }
    }*/

    /** API - Friend Manipulation Task */
    private void callWebServiceForManipulatePartner(String typeOf, String friend, String folder)
    {

        ProgressClass.getProgressInstance().showDialog(mContext);
        Call<AddFolderResponse> call = callManipulationMethod(typeOf, friend, folder);
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
                    Log.e("SearchResultAdapter", "Error : "+npe, npe);
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public Call<AddFolderResponse> callManipulationMethod(String typeOf, String s1, String s2)
    {
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);

        if(typeOf.equalsIgnoreCase(UserDefinedKeyword.ADD.toString())){
            return apiInterface.serviceAddAsFriend(Constants.Token_Paid, s1);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.MOVETO.toString())){
            return apiInterface.serviceMoveTo(Constants.Token_Paid, s1, s2);
        }else {
            Toast.makeText(mContext, "Default Called", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    /** Folder Title */
    public void callWebServiceForCustomFolder(String typeOf, String friend_id){

        String token = Constants.Token_Paid;
//        String token = prefs.getString(Constants.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(mContext);
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<FolderListModelResponse> call = apiInterface.customFolderList(token);
        call.enqueue(new Callback<FolderListModelResponse>() {
            @Override
            public void onResponse(Call<FolderListModelResponse> call, Response<FolderListModelResponse> response) {
                List<MyMeta> myMetaList = null;
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){

//                        removeTab();
                        myMetaList = response.body().getMyMetas();

//                        setTitle(myMetaList);
//                        setDropDown(myMetaList);
                        processMoveTo(typeOf, friend_id, myMetaList);

                    }else {
                        AlertDialogSingleClick.getInstance().showDialog(mContext, "Search Partner", "Opps");
                    }

                }
            }

            @Override
            public void onFailure(Call<FolderListModelResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    public void processMoveTo(String typeOf, String friend_id, List<MyMeta> myMetaList){

        List<String> folderList = new ArrayList<>();
        String[] folderArray = new String[myMetaList.size()];
        for(MyMeta myMeta :  myMetaList){
            folderList.add(myMeta.getMetaValue());

        }

        //
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.custom_dialog_list);

        ListView lv = (ListView) dialog.findViewById(R.id.lv);

//        String[] foldername = {"ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC", "ABC"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, folderList);
        lv.setAdapter(adapter);
        dialog.setCancelable(true);
        dialog.setTitle("ListView");
        dialog.show();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(mContext, "Output : " + position, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                String folderId = String.valueOf(myMetaList.get(position).getId());
                callWebServiceForManipulatePartner(UserDefinedKeyword.MOVETO.toString(), friend_id, folderId);

            }
        });


    }

}
