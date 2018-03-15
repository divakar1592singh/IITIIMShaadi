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

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.PaidSearchResultAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.fragment.PaidSearchPartnerFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.PaidSubscriberResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.SubsAdvanceSearchResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.User;
import com.senzecit.iitiimshaadi.model.api_rquest_model.search_partner_subs.PaidSubsAdvanceSearchRequest;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaidSearchPartnerActivity extends AppCompatActivity implements PaidSearchPartnerFragment.PaidSearchPartnerFragmentCommunicator {

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
    LinearLayout mHorizontalLayout;

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
        mHorizontalLayout = (LinearLayout)findViewById(R.id.idHBarLayout);

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

      /*  mSearchResultRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int itemLastVisiblePosition = layoutManager.findLastVisibleItemPosition();
//                Toast.makeText(PaidSearchPartnerActivity.this, "Last Count : "+itemLastVisiblePosition, Toast.LENGTH_SHORT).show();

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
        });*/

    }

    @Override
    public void saveAndSearchPaidPartner() {

        String search_type = prefs.getString(CONSTANTS.SEARCH_TYPE);

        if(search_type.equalsIgnoreCase("id")){
            mCurrentSearchBtn.setText("CURRENT SEARCH");
            callWebServiceForSubsIDSearch();
        }else if(search_type.equalsIgnoreCase("keyword")){
            mCurrentSearchBtn.setText("CURRENT SEARCH");
            callWebServiceForSubsKeywordSearch();
        }else if(search_type.equalsIgnoreCase("advance")){
            mCurrentSearchBtn.setText("SHOW CURRENT SEARCH");
            callWebServiceForSubsAdvanceSearch();
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
    }

    private void setPaidMatchedListID(List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList){

        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);
        mCurrentSearchLayout.setVisibility(View.GONE);

        adapter = new PaidSearchResultAdapter(PaidSearchPartnerActivity.this, queryList, null);
        mSearchResultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
 private void setPaidMatchedListByKeyword(List<User> queryList){

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

     adapter = new PaidSearchResultAdapter(PaidSearchPartnerActivity.this, null, queryList);
        mSearchResultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    /** API Integration */
    /** Search By ID */
    public void callWebServiceForSubsIDSearch(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);;
        String searchID = prefs.getString(CONSTANTS.SEARCH_ID) ;

        if(NetworkClass.getInstance().checkInternet(PaidSearchPartnerActivity.this) == true){

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(PaidSearchPartnerActivity.this);
        Call<PaidSubscriberResponse> call = apiInterface.idSearchPaid(token, searchID);
        call.enqueue(new Callback<PaidSubscriberResponse>() {
            @Override
            public void onResponse(Call<PaidSubscriberResponse> call, Response<PaidSubscriberResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getQuery().size() > 0){
                            List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList = response.body().getQuery();

                            setPaidMatchedListID(queryList);
                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Alert", CONSTANTS.search_ptnr_err_msg);
                        }
                    }else {
//                        AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Search Partner", CONSTANTS.search_ptnr_err_msg);
                        reTryMethod(1);;
                    }
                }
            }

            @Override
            public void onFailure(Call<PaidSubscriberResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(PaidSearchPartnerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
                reTryMethod(1);
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSearchPartnerActivity.this);
        }

    }
    /** Search By Keyword */
    public void callWebServiceForSubsKeywordSearch(){

//        String token = "42a6259d9ae09e7fde77c74bbf2a9a48";
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);;
        String keyword = prefs.getString(CONSTANTS.SEARCH_KEYWORD) ;

        if(NetworkClass.getInstance().checkInternet(PaidSearchPartnerActivity.this) == true){

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(PaidSearchPartnerActivity.this);
        Call<SubsAdvanceSearchResponse> call = apiInterface.keywordSearchPaid(token, keyword);
        call.enqueue(new Callback<SubsAdvanceSearchResponse>() {
            @Override
            public void onResponse(Call<SubsAdvanceSearchResponse> call, Response<SubsAdvanceSearchResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getUsers().size() > 0){
                            List<User> queryList = response.body().getUsers();
//                            System.out.print(profileList);

                            setPaidMatchedListByKeyword(queryList);
                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Alert", CONSTANTS.search_ptnr_err_msg);
                        }
                    }else {
//                        AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Search Partner", "Opps");
                        reTryMethod(2);
                    }

                }
            }

            @Override
            public void onFailure(Call<SubsAdvanceSearchResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(PaidSearchPartnerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
                reTryMethod(2);
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSearchPartnerActivity.this);
        }
    }
    /** Advance Search */
    public void callWebServiceForSubsAdvanceSearch(){

        List<String> profileList =new ArrayList<>();

        String token = "9c8e5cf4fad369c0ed33d166ddf0b0a2";
//        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);


        String minage = prefs.getString(CONSTANTS.MIN_AGE) ;
        String maxage = prefs.getString(CONSTANTS.MAX_AGE) ;
        String country = prefs.getString(CONSTANTS.COUNTRY) ;
        String city =  prefs.getString(CONSTANTS.CITY);
        String[] cityArr = new String[1];
        cityArr[0] = city;
        String religion =  prefs.getString(CONSTANTS.RELIGION);
        String caste = prefs.getString(CONSTANTS.CASTE);
        String[] casteArr = new String[1];
        casteArr[0] = caste;

        String mother_tounge =  prefs.getString(CONSTANTS.MOTHER_TONGUE);
        String[] mother_toungeArr = new String[1];
        mother_toungeArr[0] = mother_tounge;

        String marital_status =  prefs.getString(CONSTANTS.MARITAL_STATUS);
        String[] marital_statusArr = new String[1];
        marital_statusArr[0] = marital_status;

        String course =  prefs.getString(CONSTANTS.COURSE);
        String[] courseArr = new String[1];
        courseArr[0] = course;

        String annual_income =  prefs.getString(CONSTANTS.ANNUAL_INCOME);
        String[] annual_incomeArr = new String[1];
        annual_incomeArr[0] = annual_income;

        String sPartnerLoc = prefs.getString(CONSTANTS.PARTNER_LOC);
        String[] sPartnerLocArr = new String[1];
        sPartnerLocArr[0] = sPartnerLoc;

        String sMinHeight = prefs.getString(CONSTANTS.MIN_HEIGHT);
        String sMaxHeight = prefs.getString(CONSTANTS.MAX_HEIGHT);

        profileList.add(minage);profileList.add(maxage);profileList.add(country);
        profileList.add(city);profileList.add(religion);profileList.add(caste);
        profileList.add(mother_tounge);profileList.add(marital_status);
        profileList.add(course);profileList.add(annual_income);
        profileList.add(sPartnerLoc);profileList.add(sMinHeight);profileList.add(sMaxHeight);

 /*       PaidSubsAdvanceSearchRequest searchRequest = new PaidSubsAdvanceSearchRequest();
        searchRequest.token = token;
        searchRequest.minage = minage;
        searchRequest.maxage = maxage;
        searchRequest.country = country;

        searchRequest.city = cityArr;
        searchRequest.religion = religion;
        searchRequest.caste = casteArr;
        searchRequest.mother_tounge = mother_toungeArr;
        searchRequest.marital_status = marital_statusArr;
        searchRequest.course = courseArr;
        searchRequest.annual_income = annual_incomeArr;

        searchRequest.location = sPartnerLocArr;
//        searchRequest.details = ;

        searchRequest.min_height = sMinHeight;
        searchRequest.max_height = sMaxHeight;
*/
        String page = "1";


        if(NetworkClass.getInstance().checkInternet(PaidSearchPartnerActivity.this) == true){

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(PaidSearchPartnerActivity.this);

        Call<SubsAdvanceSearchResponse> call = apiInterface.advanceSearchPaid(token, page, minage, maxage, country, cityArr,sPartnerLocArr,religion,casteArr,mother_toungeArr,marital_statusArr,sMinHeight,sMaxHeight,courseArr,annual_incomeArr);
        call.enqueue(new Callback<SubsAdvanceSearchResponse>() {
            @Override
            public void onResponse(Call<SubsAdvanceSearchResponse> call, Response<SubsAdvanceSearchResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")) {
                            if (response.body().getUsers().size() > 0) {

                                int pageCount = response.body().getTotalCount();
                                setupPageView(pageCount);

                                List<User> queryList = response.body().getUsers();
                                System.out.print(profileList);

                                setPaidSearchedData(profileList);
                                setPaidMatchedListByKeyword(queryList);

                            } else {
                                AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Alert", CONSTANTS.search_ptnr_err_msg);
                            }
                        } else {
                            AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Search Partner", "Opps");
                            reTryMethod(3);
                        }
                    }catch(NullPointerException npe){
                        Log.e("TAG", " #Error : "+npe, npe);
                    }
                }
            }

            @Override
            public void onFailure(Call<SubsAdvanceSearchResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(PaidSearchPartnerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
                reTryMethod(3);
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSearchPartnerActivity.this);
        }


    }

    private void setupPageView(int page){
        int pages = 0;

        TextView[] textViews = new TextView[10];
        for(int i = 0; i< 10; i++){
            textViews[i] = new TextView(PaidSearchPartnerActivity.this);
            textViews[i].setLayoutParams(new LinearLayout.LayoutParams(48, 48));
            textViews[i].setText(""+(i+1));
            textViews[i].setPadding(4,4,4,4);
            mHorizontalLayout.addView(textViews[i]);
        }

    }

    public void reTryMethod(int pos){

        new AlertDialog.Builder(PaidSearchPartnerActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert")
                .setMessage("Something went wrong!\n Please Try Again!")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(pos == 1){
                            callWebServiceForSubsIDSearch();
                        }else if(pos == 2){
                            callWebServiceForSubsKeywordSearch();
                        }else if(pos == 3){
                            callWebServiceForSubsAdvanceSearch();
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
