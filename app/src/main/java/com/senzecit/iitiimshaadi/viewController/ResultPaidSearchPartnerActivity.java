package com.senzecit.iitiimshaadi.viewController;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.PaidSearchResultAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.fragment.PaidSearchPartnerFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.PaidSubscriberResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.SubsAdvanceSearchResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.search_partner_subs.PaidSubsAdvanceSearchRequest;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultPaidSearchPartnerActivity extends AppCompatActivity implements PaidSearchPartnerFragment.PaidSearchPartnerFragmentCommunicator {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    boolean loading = false;
    FrameLayout mContainerFragLayout, mContainerResLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    PaidSearchPartnerFragment paidSearchPartnerFragment;
    LinearLayout mCurrentSearchLayout;
    AppPrefs prefs;

    RecyclerView mSearchResultRecyclerView;
    LinearLayoutManager layoutManager;
    PaidSearchResultAdapter adapter;
    Button mCurrentSearchBtn;
    int pageCount = 1;

    List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList1;
    List<Query> queryList2;

    TextView mAgeMin,mAgeMax,mCountry,mCity,mReligion,mMotherTongue,mmaritalStatus,mIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result_paid_search_partner);

        getSupportActionBar().hide();
        prefs = AppController.getInstance().getPrefs();

        paidSearchPartnerFragment = new PaidSearchPartnerFragment();

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.search_partner_FL, paidSearchPartnerFragment);
        mFragmentTransaction.commit();

        paidSearchPartnerFragment.setPaidSearchPartnerFragmentCommunicator(this);

        init();
        handleView();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mCurrentSearchBtn = (Button)findViewById(R.id.idCurrentSearchBtn);
        mContainerFragLayout = (FrameLayout) findViewById(R.id.search_partner_FL);
        mContainerResLayout = (FrameLayout) findViewById(R.id.search_result_FL);

        mCurrentSearchLayout = (LinearLayout)findViewById(R.id.idCurrentSearchLayout);

        mSearchResultRecyclerView = (RecyclerView) findViewById(R.id.partnerSearchResulttRV);

        mAgeMin = findViewById(R.id.minAgeTV);
        mAgeMax = findViewById(R.id.maxAgeTV);
        mCountry = findViewById(R.id.countryTV);
        mCity = findViewById(R.id.cityTV);
        mReligion = findViewById(R.id.religionTV);
        mMotherTongue = findViewById(R.id.motherTougeTV);
        mmaritalStatus = findViewById(R.id.maritalStatusTV);
        mIncome = findViewById(R.id.incomeTV);


    }

    public void handleView(){

        mTitle.setText("Search Partner");
        mCurrentSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search_type = prefs.getString(CONSTANTS.SEARCH_TYPE);

                if(search_type.equalsIgnoreCase("advance")) {

                    if (mCurrentSearchLayout.getVisibility() == View.VISIBLE) {
                        mCurrentSearchLayout.setVisibility(View.GONE);
                        mCurrentSearchBtn.setText("SHOW CURRENT SEARCH");
                    } else {
                        mCurrentSearchLayout.setVisibility(View.VISIBLE);
                        mCurrentSearchBtn.setText("HIDE CURRENT SEARCH");
                    }

                }
            }
        });

        layoutManager = new LinearLayoutManager(this);
        mSearchResultRecyclerView.setLayoutManager(layoutManager);

        mSearchResultRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int itemLastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                Toast.makeText(ResultPaidSearchPartnerActivity.this, "Last Count : "+itemLastVisiblePosition, Toast.LENGTH_SHORT).show();

                if(itemLastVisiblePosition == adapter.getItemCount() - 1) {

                         if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                           boolean canScrollDownMore = recyclerView.canScrollVertically(1);
                         if (!canScrollDownMore) {

//                         setPaidMatchedListByKeyword(queryList2, pageCount + 10);
                             paginateSearchPartner(pageCount+10);

                         onScrolled(recyclerView, 0, 1);
                     }
                  }
                }
            }
        });

    }

    @Override
    public void saveAndSearchPaidPartner() {

        String search_type = prefs.getString(CONSTANTS.SEARCH_TYPE);

        if(search_type.equalsIgnoreCase("id")){
            mCurrentSearchBtn.setText("CURRENT SEARCH");
            callWebServiceForSubsIDSearch(pageCount);
        }else if(search_type.equalsIgnoreCase("keyword")){
            mCurrentSearchBtn.setText("CURRENT SEARCH");
            callWebServiceForSubsKeywordSearch(pageCount);
        }else if(search_type.equalsIgnoreCase("advance")){
            mCurrentSearchBtn.setText("SHOW CURRENT SEARCH");
            callWebServiceForSubsAdvanceSearch(pageCount);
        }

    }

    public void paginateSearchPartner(int pageCount) {

        String search_type = prefs.getString(CONSTANTS.SEARCH_TYPE);

        if(search_type.equalsIgnoreCase("id")){
            callWebServiceForSubsIDSearch(pageCount);
        }else if(search_type.equalsIgnoreCase("keyword")){
            callWebServiceForSubsKeywordSearch(pageCount);
        }else if(search_type.equalsIgnoreCase("advance")){
            callWebServiceForSubsAdvanceSearch(pageCount);
        }

    }

    private void setPaidSearchedData(List<String> profileList){
        try {
            mAgeMin.setText(profileList.get(0));
            mAgeMax.setText(profileList.get(1));
            mCountry.setText(profileList.get(2));
            mCity.setText(profileList.get(3));
            mReligion.setText(profileList.get(4));
            mMotherTongue.setText(profileList.get(6));
            mmaritalStatus.setText(profileList.get(7));
            mIncome.setText(profileList.get(9));
        }catch (IndexOutOfBoundsException ioe){
            Log.e("TAG", "#Error : "+ioe, ioe);
        }
//        minage,maxage,country,city,religion,caste,mother_tounge,marital_status,course,annual_income
    }
    private void setPaidMatchedListID(List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList, int pageCount){

        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);
        mCurrentSearchLayout.setVisibility(View.GONE);

        adapter = new PaidSearchResultAdapter(ResultPaidSearchPartnerActivity.this, queryList, null, pageCount);
        mSearchResultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
 private void setPaidMatchedListByKeyword(List<Query> queryList, int pageCount){

     String search_type = prefs.getString(CONSTANTS.SEARCH_TYPE);

     if(search_type.equalsIgnoreCase("keyword")){

         mContainerFragLayout.setVisibility(View.GONE);
         mContainerResLayout.setVisibility(View.VISIBLE);
         mCurrentSearchLayout.setVisibility(View.GONE);

     }else if(search_type.equalsIgnoreCase("advance")){

         mContainerFragLayout.setVisibility(View.GONE);
         mContainerResLayout.setVisibility(View.VISIBLE);
         mCurrentSearchLayout.setVisibility(View.GONE);

     }

     adapter = new PaidSearchResultAdapter(ResultPaidSearchPartnerActivity.this, null, queryList, pageCount);
        mSearchResultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    /** API Integration */
//    /** Search By ID */
    public void callWebServiceForSubsIDSearch(int pageCount){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);;
        String searchID = prefs.getString(CONSTANTS.SEARCH_ID) ;

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(ResultPaidSearchPartnerActivity.this);
        Call<PaidSubscriberResponse> call = apiInterface.idSearchPaid1(token, searchID);
        call.enqueue(new Callback<PaidSubscriberResponse>() {
            @Override
            public void onResponse(Call<PaidSubscriberResponse> call, Response<PaidSubscriberResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getQuery().size() > 0){
                            List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList = response.body().getQuery();

                            setPaidMatchedListID(queryList, pageCount);
                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(ResultPaidSearchPartnerActivity.this, "Alert", CONSTANTS.search_ptnr_err_msg);
                        }
                    }else {
//                        AlertDialogSingleClick.getInstance().showDialog(ResultPaidSearchPartnerActivity.this, "Search Partner", CONSTANTS.search_ptnr_err_msg);
                        reTryMethod(1, pageCount);;
                    }
                }
            }

            @Override
            public void onFailure(Call<PaidSubscriberResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(ResultPaidSearchPartnerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
                reTryMethod(1, pageCount);
            }
        });
    }
    /** Search By Keyword */
    public void callWebServiceForSubsKeywordSearch(int pageCount){

//        String token = "42a6259d9ae09e7fde77c74bbf2a9a48";
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);;
        String keyword = prefs.getString(CONSTANTS.SEARCH_KEYWORD) ;

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(ResultPaidSearchPartnerActivity.this);
        Call<SubsAdvanceSearchResponse> call = apiInterface.keywordSearchPaid(token, keyword);
        call.enqueue(new Callback<SubsAdvanceSearchResponse>() {
            @Override
            public void onResponse(Call<SubsAdvanceSearchResponse> call, Response<SubsAdvanceSearchResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getQuery().size() > 0){
                            List<Query> queryList = response.body().getQuery();
//                            System.out.print(profileList);

                            setPaidMatchedListByKeyword(queryList, pageCount);
                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(ResultPaidSearchPartnerActivity.this, "Alert", CONSTANTS.search_ptnr_err_msg);
                        }
                    }else {
//                        AlertDialogSingleClick.getInstance().showDialog(ResultPaidSearchPartnerActivity.this, "Search Partner", "Opps");
                        reTryMethod(2, pageCount);
                    }

                }
            }

            @Override
            public void onFailure(Call<SubsAdvanceSearchResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(ResultPaidSearchPartnerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
                reTryMethod(2, pageCount);
            }
        });
    }
    /** Advance Search */
    public void callWebServiceForSubsAdvanceSearch(int pageCount){

        List<String> profileList =new ArrayList<>();

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        String minage = prefs.getString(CONSTANTS.MIN_AGE) ;
        String maxage = prefs.getString(CONSTANTS.MAX_AGE) ;
        String country = prefs.getString(CONSTANTS.COUNTRY) ;
        String city =  prefs.getString(CONSTANTS.CITY);
        String religion =  prefs.getString(CONSTANTS.RELIGION);
        String caste = prefs.getString(CONSTANTS.CASTE);
        String mother_tounge =  prefs.getString(CONSTANTS.MOTHER_TONGUE);
        String marital_status =  prefs.getString(CONSTANTS.MARITAL_STATUS);
        String course =  prefs.getString(CONSTANTS.COURSE);
        String annual_income =  prefs.getString(CONSTANTS.ANNUAL_INCOME);

        String sPartnerLoc = prefs.getString(CONSTANTS.PARTNER_LOC);
        String sMinHeight = prefs.getString(CONSTANTS.MIN_HEIGHT);
        String sMaxHeight = prefs.getString(CONSTANTS.MAX_HEIGHT);

        profileList.add(minage);profileList.add(maxage);profileList.add(country);
        profileList.add(city);profileList.add(religion);profileList.add(caste);
        profileList.add(mother_tounge);profileList.add(marital_status);
        profileList.add(course);profileList.add(annual_income);
        profileList.add(sPartnerLoc);profileList.add(sMinHeight);profileList.add(sMaxHeight);

        PaidSubsAdvanceSearchRequest searchRequest = new PaidSubsAdvanceSearchRequest();
        searchRequest.token = token;
        searchRequest.minage = minage;
        searchRequest.maxage = maxage;
        searchRequest.country = country;
        searchRequest.city = city;
        searchRequest.religion = religion;
        searchRequest.caste = caste;
        searchRequest.mother_tounge = mother_tounge;
        searchRequest.marital_status = marital_status;
        searchRequest.course = course;
        searchRequest.annual_income = annual_income;

        searchRequest.min_height = sMinHeight;
        searchRequest.max_height = sMaxHeight;

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(ResultPaidSearchPartnerActivity.this);
        Call<SubsAdvanceSearchResponse> call = apiInterface.advanceSearchPaid(searchRequest);
        call.enqueue(new Callback<SubsAdvanceSearchResponse>() {
            @Override
            public void onResponse(Call<SubsAdvanceSearchResponse> call, Response<SubsAdvanceSearchResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getQuery().size() > 0){
                            List<Query> queryList = response.body().getQuery();
                            System.out.print(profileList);

                            setPaidSearchedData(profileList);
                            setPaidMatchedListByKeyword(queryList, pageCount);

                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(ResultPaidSearchPartnerActivity.this, "Alert", CONSTANTS.search_ptnr_err_msg);
                        }
                    }else {
                        AlertDialogSingleClick.getInstance().showDialog(ResultPaidSearchPartnerActivity.this, "Search Partner", "Opps");
                        reTryMethod(3, pageCount);
                    }

                }
            }

            @Override
            public void onFailure(Call<SubsAdvanceSearchResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(ResultPaidSearchPartnerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
                reTryMethod(3, pageCount);
            }
        });

    }

    public void reTryMethod(int pos, int pageCount){

        new AlertDialog.Builder(ResultPaidSearchPartnerActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert")
                .setMessage("Something went wrong!\n Please Try Again!")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(pos == 1){
                            callWebServiceForSubsIDSearch(pageCount);
                        }else if(pos == 2){
                            callWebServiceForSubsKeywordSearch(pageCount);
                        }else if(pos == 3){
                            callWebServiceForSubsAdvanceSearch(pageCount);
                        }
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
