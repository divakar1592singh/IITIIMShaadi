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

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.DateToAgeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.Message;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.UserDetail;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.FolderListModelResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.MyMeta;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.OtherProfileActivity;

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

public class PaidSearchResultAdapterBKP extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context mContext;
    List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList;
    List<com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query> queryListKeyword;
    AppPrefs prefs;
    LayoutInflater inflater;

    int pageCount = 0;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;



    public PaidSearchResultAdapterBKP(Context mContext, List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList, List<com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query> queryListKey, int pageCount){
        this.mContext = mContext;
        this.queryList = queryList;
        this.queryListKeyword = queryListKey;
        inflater = LayoutInflater.from(mContext);
        prefs  = AppController.getInstance().getPrefs();
        this.pageCount = pageCount;
    }

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


    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtfooter;
        public FooterViewHolder(View v) {
            super(v);
            txtfooter = (TextView) v.findViewById(R.id.idTv2);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     /*   View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item_paid,parent,false);
        return new MyViewHolder(itemView);
*/
        if (viewType == TYPE_ITEM) {
            View itemView = inflater.inflate(R.layout.search_result_item_paid, null);
            return new MyViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            View itemView = inflater.inflate(R.layout.footer_layout, null);
            return new FooterViewHolder(itemView);

        } else if (viewType == TYPE_FOOTER) {
            View itemView = inflater.inflate(R.layout.footer_layout, null);
            return new FooterViewHolder(itemView);

        }
//        return null;
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyViewHolder) {
            if (queryList != null) {
                UserDetail userDetail = queryList.get(position).getUserDetail();

                try {
                    String userId = String.valueOf(userDetail.getUserId());
                    String partUrl = userDetail.getProfileImage();
                    Glide.with(mContext).load(CONSTANTS.IMAGE_AVATAR_URL + userId + "/" + partUrl).error(R.drawable.profile_img1).into(((MyViewHolder)holder).mSearchPartnerIV);
                }catch (NullPointerException npe){
                    Log.e("TAG", " #Error : "+npe, npe);
                }

                ((MyViewHolder)holder).mNameTxt.setText(userDetail.getName());
                ((MyViewHolder)holder).mDietTxt.setText(userDetail.getDiet());
                ((MyViewHolder)holder).mAgeTxt.setText(userDetail.getBirthDate());
                formattedDate(((MyViewHolder)holder).mAgeTxt, userDetail.getBirthDate());
//            ((MyViewHolder)holder).mAgeTxt.setText(formattedDate(((MyViewHolder)holder).mAgeTxt, userDetail.getBirthDate()));
                ((MyViewHolder)holder).mEmploymentTxt.setText(userDetail.getWorkingAs());
                ((MyViewHolder)holder).mCompanyTxt.setText(userDetail.getNameOfCompany());
                ((MyViewHolder)holder).mHeightTxt.setText(userDetail.getHeight());
                ((MyViewHolder)holder).mReligiontxt.setText(userDetail.getReligion());
                ((MyViewHolder)holder).mEducationTxt.setText(userDetail.getHighestEducation());

                ((MyViewHolder)holder).mAddFriendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String userId = String.valueOf(userDetail.getUserId());
                        callWebServiceForManipulatePartner(UserDefinedKeyword.ADD.toString(), userId, null);

                    }
                });
//        ((MyViewHolder)holder).mPartnerPref.setOnClickListener(this);
                ((MyViewHolder)holder).mMoveTo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String userId = String.valueOf(userDetail.getUserId());
                        callWebServiceForCustomFolder(UserDefinedKeyword.MOVETO.toString(), userId);

                    }
                });

// ---
                ((MyViewHolder)holder).mSearchPartnerIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userId = String.valueOf(userDetail.getUserId());
                        if(userId.length()> 0){
                            prefs.putString(CONSTANTS.OTHER_USERID, userId);
                            Navigator.getClassInstance().navigateToActivity(mContext, OtherProfileActivity.class);
                        }
                    }
                });

            } else if (queryListKeyword != null) {

                try {
                    String userId = String.valueOf(queryListKeyword.get(position).getUserId());
                    String partUrl = queryListKeyword.get(position).getProfileImage();
                    Glide.with(mContext).load(CONSTANTS.IMAGE_AVATAR_URL + userId + "/" + partUrl).error(R.drawable.profile_img1).into(((MyViewHolder)holder).mSearchPartnerIV);
                }catch (NullPointerException npe){
                    Log.e("TAG", " #Error : "+npe, npe);
                }

                ((MyViewHolder)holder).mNameTxt.setText(queryListKeyword.get(position).getName());
                ((MyViewHolder)holder).mDietTxt.setText(queryListKeyword.get(position).getDiet());
//            holder.mAgeTxt.setText(queryListKeyword.get(position).getBirthDate());
                formattedDate(((MyViewHolder)holder).mAgeTxt, queryListKeyword.get(position).getBirthDate());
                ((MyViewHolder)holder).mEmploymentTxt.setText(queryListKeyword.get(position).getWorkingAs());
                ((MyViewHolder)holder).mCompanyTxt.setText(queryListKeyword.get(position).getNameOfCompany());
                ((MyViewHolder)holder).mHeightTxt.setText(queryListKeyword.get(position).getHeight());
                ((MyViewHolder)holder).mReligiontxt.setText(queryListKeyword.get(position).getReligion());
                ((MyViewHolder)holder).mEducationTxt.setText(queryListKeyword.get(position).getHighestEducation());

                ((MyViewHolder)holder).mAddFriendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String userId = String.valueOf(queryListKeyword.get(position).getUserId());
                        callWebServiceForManipulatePartner(UserDefinedKeyword.ADD.toString(), userId, null);

                    }
                });
//        ((MyViewHolder)holder).mPartnerPref.setOnClickListener(this);
                ((MyViewHolder)holder).mMoveTo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String userId = String.valueOf(queryListKeyword.get(position).getUserId());
                        callWebServiceForCustomFolder(UserDefinedKeyword.MOVETO.toString(), userId);

                    }
                });

// ---
                ((MyViewHolder)holder).mSearchPartnerIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userId = String.valueOf(queryListKeyword.get(position).getUserId());
                        if(userId.length()> 0){
                            prefs.putString(CONSTANTS.OTHER_USERID, userId);
                            Navigator.getClassInstance().navigateToActivity(mContext, OtherProfileActivity.class);
                        }
                    }
                });


            } else {

            }

        }else if(holder instanceof FooterViewHolder){

        }



    }

    @Override
    public int getItemCount() {
//        return 10;
        if(queryList != null) {
            return pageCount < queryList.size() ? pageCount + 2 : queryList.size() + 1;
        }else if(queryListKeyword != null) {
            return pageCount < queryListKeyword.size() ? pageCount + 2 : queryListKeyword.size() + 1;
        }else {
            return 0;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }
    private boolean isPositionFooter(int position) {
//        return position > list.size();
        return position > pageCount;

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

        if(NetworkClass.getInstance().checkInternet(mContext) == true){

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
        }else {
            NetworkDialogHelper.getInstance().showDialog(mContext);
        }

    }
    public Call<AddFolderResponse> callManipulationMethod(String typeOf, String s1, String s2)
    {
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);

        if(typeOf.equalsIgnoreCase(UserDefinedKeyword.ADD.toString())){
            return apiInterface.serviceAddAsFriend(token, s1);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.MOVETO.toString())){
            return apiInterface.serviceMoveTo(token, s1, s2);
        }else {
//            Toast.makeText(mContext, "Default Called", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    /** Folder Title */
    public void callWebServiceForCustomFolder(String typeOf, String friend_id){

//        String token = CONSTANTS.Token_Paid;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(mContext) == true){

            ProgressClass.getProgressInstance().showDialog(mContext);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
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
    }else {
        NetworkDialogHelper.getInstance().showDialog(mContext);
    }
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, folderList);
        lv.setAdapter(adapter);
        dialog.setCancelable(true);
        dialog.setTitle("ListView");
        dialog.show();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(mContext, "Output : " + position, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                String folderId = String.valueOf(myMetaList.get(position).getId());
                callWebServiceForManipulatePartner(UserDefinedKeyword.MOVETO.toString(), friend_id, folderId);

            }
        });


    }

    public static String getDate(String _Date){

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
