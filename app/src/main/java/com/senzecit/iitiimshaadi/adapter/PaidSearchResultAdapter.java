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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.DateToAgeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.Message;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.UserDetail;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.FolderListModelResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.MyMeta;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ravi on 13/11/17.
 */

public class PaidSearchResultAdapter extends RecyclerView.Adapter<PaidSearchResultAdapter.MyViewHolder> {

    boolean detail,partnerPref,album;

    Context mContext;
    List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList;
    List<com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query> queryListKeyword;

    public PaidSearchResultAdapter(Context mContext, List<Query> queryList, List<com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query> queryListKey){
        this.mContext = mContext;
        this.queryList = queryList;
        this.queryListKeyword = queryListKey;
    }

 /*   public PaidSearchResultAdapter(Context mContext, List<com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query> queryList){
        this.mContext = mContext;
        this.queryListKeyword = queryList;
    }
*/
    class MyViewHolder extends RecyclerView.ViewHolder{

        Button mAddFriendBtn,mMoveTo;
        ImageView mSearchPartnerIV;
        TextView mNameTxt, mDietTxt, mAgeTxt, mEmploymentTxt, mCompanyTxt, mHeightTxt, mReligiontxt, mEducationTxt;

        public MyViewHolder(View itemView) {
            super(itemView);

            mNameTxt = itemView.findViewById(R.id.idNameTV);
            mDietTxt = itemView.findViewById(R.id.idDietTV);
            mAgeTxt = itemView.findViewById(R.id.idAgeTV);
            mAgeTxt = itemView.findViewById(R.id.idAgeTV);
            mEmploymentTxt = itemView.findViewById(R.id.idEmploymentTV);
            mCompanyTxt = itemView.findViewById(R.id.idCompanyTV);
            mHeightTxt = itemView.findViewById(R.id.idHeightTV);
            mReligiontxt = itemView.findViewById(R.id.idReligionTV);
            mEducationTxt = itemView.findViewById(R.id.idEducationTV);

            mAddFriendBtn = itemView.findViewById(R.id.idAddFriendBtn);
            mMoveTo = itemView.findViewById(R.id.moveToBtm);

            mSearchPartnerIV = itemView.findViewById(R.id.idSearchpartnerIV);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item_paid,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if( queryList != null) {

            UserDetail userDetail = queryList.get(position).getUserDetail();

            holder.mNameTxt.setText(userDetail.getName());
            holder.mDietTxt.setText(userDetail.getDiet());
            holder.mAgeTxt.setText(userDetail.getBirthDate());
            formattedDate(holder.mAgeTxt, userDetail.getBirthDate());
//            holder.mAgeTxt.setText(formattedDate(holder.mAgeTxt, userDetail.getBirthDate()));
            holder.mEmploymentTxt.setText(userDetail.getWorkingAs());
            holder.mCompanyTxt.setText(userDetail.getNameOfCompany());
            holder.mHeightTxt.setText(userDetail.getHeight());
            holder.mReligiontxt.setText(userDetail.getReligion());
            holder.mEducationTxt.setText(userDetail.getHighestEducation());

            holder.mAddFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userId = String.valueOf(userDetail.getUserId());
                    callWebServiceForManipulatePartner(UserDefinedKeyword.ADD.toString(), userId, null);

                }
            });
//        holder.mPartnerPref.setOnClickListener(this);
            holder.mMoveTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userId = String.valueOf(userDetail.getUserId());
                    callWebServiceForCustomFolder(UserDefinedKeyword.MOVETO.toString(), userId);

                }
            });
        }else if(queryListKeyword != null ) {

            holder.mNameTxt.setText(queryListKeyword.get(position).getName());
            holder.mDietTxt.setText(queryListKeyword.get(position).getDiet());
//            holder.mAgeTxt.setText(queryListKeyword.get(position).getBirthDate());
            formattedDate(holder.mAgeTxt, queryListKeyword.get(position).getBirthDate());
            holder.mEmploymentTxt.setText(queryListKeyword.get(position).getWorkingAs());
            holder.mCompanyTxt.setText(queryListKeyword.get(position).getNameOfCompany());
            holder.mHeightTxt.setText(queryListKeyword.get(position).getHeight());
            holder.mReligiontxt.setText(queryListKeyword.get(position).getReligion());
            holder.mEducationTxt.setText(queryListKeyword.get(position).getHighestEducation());

            holder.mAddFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userId = String.valueOf(queryListKeyword.get(position).getUserId());
                    callWebServiceForManipulatePartner(UserDefinedKeyword.ADD.toString(), userId, null);

                }
            });
//        holder.mPartnerPref.setOnClickListener(this);
            holder.mMoveTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userId = String.valueOf(queryListKeyword.get(position).getUserId());
                    callWebServiceForCustomFolder(UserDefinedKeyword.MOVETO.toString(), userId);

                }
            });

        }else {

        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }
/*

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detailsBtn:
                break;
            case R.id.albumsBtm:
//                communicator.albums();
                break;
            case R.id.idSearchpartnerIV:
//                communicator.details();
                break;

        }
    }
*/


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


    public void formattedDate(TextView tv, String _date) {

//        String _date = "1988-08-28";

        try {

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
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
