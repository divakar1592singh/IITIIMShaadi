package com.senzecit.iitiimshaadi.viewController;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.PaidSearchResultAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.fragment.PaidSearchPartnerFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.PaidSubscriberResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.SubsAdvanceSearchResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.User;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
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

public class PaidSearchPartnerActivity extends AppCompatActivity implements PaidSearchPartnerFragment.PaidSearchPartnerFragmentCommunicator, View.OnClickListener {

    private static final String TAG = "PaidSearchPartner";
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
    NestedScrollView mResultScroll;
    Object lastSelectedId = -1;
    Button[] buttons;
    String selectedPage = "0";

    TextView mAgeMin,mAgeMax,mCountry,mCity,mReligion,mMotherTongue,mmaritalStatus,mIncome;
    TextView tvSearchDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result_paid_search_partner);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mCurrentSearchBtn = findViewById(R.id.idCurrentSearchBtn);
        mContainerFragLayout = findViewById(R.id.search_partner_FL);
        mContainerResLayout = findViewById(R.id.search_result_FL);

        mCurrentSearchLayout = findViewById(R.id.idCurrentSearchLayout);

        mSearchResultRecyclerView = findViewById(R.id.partnerSearchResulttRV);
        mHorizontalLayout = findViewById(R.id.idHBarLayout);
        mResultScroll = findViewById(R.id.idResultScroll);

        mAgeMin = findViewById(R.id.minAgeTV);
        mAgeMax = findViewById(R.id.maxAgeTV);
        mCountry = findViewById(R.id.countryTV);
        mCity = findViewById(R.id.cityTV);
        mReligion = findViewById(R.id.religionTV);
        mMotherTongue = findViewById(R.id.motherTougeTV);
        mmaritalStatus = findViewById(R.id.maritalStatusTV);
        mIncome = findViewById(R.id.incomeTV);

        tvSearchDesc = (TextView)findViewById(R.id.idSearchDescTv);

    }

    public void handleView(){

        mTitle.setText("Search Partner");
        mBack.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(this);
        mSearchResultRecyclerView.setLayoutManager(layoutManager);

        ViewCompat.setNestedScrollingEnabled(mSearchResultRecyclerView, false);

    }

    @Override
    public void saveAndSearchPaidPartner() {

        try {
            mHorizontalLayout.removeAllViews();
        }catch (NullPointerException npe){
            Log.e(TAG, " #Err : "+npe, npe);
        }

        String search_type = prefs.getString(CONSTANTS.SEARCH_TYPE);

        if(search_type.equalsIgnoreCase("id")){
            mCurrentSearchBtn.setText("CURRENT SEARCH");
            mHorizontalLayout.setVisibility(View.INVISIBLE);
            callWebServiceForSubsIDSearch();
        }else if(search_type.equalsIgnoreCase("keyword")){
            mCurrentSearchBtn.setText("CURRENT SEARCH");
            mHorizontalLayout.setVisibility(View.VISIBLE);
            callWebServiceForSubsKeywordSearch("1");
        }else if(search_type.equalsIgnoreCase("advance")){
            mCurrentSearchBtn.setText("SHOW CURRENT SEARCH");
            mHorizontalLayout.setVisibility(View.VISIBLE);
            callWebServiceForSubsAdvanceSearch("1");
        }

    }

    private void setPaidSearchedData(List<String> profileList, int totalUserCount){
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

        try {
            int pageCount = (totalUserCount/10);
            int reminder = totalUserCount%10;
            if(reminder > 0){
                pageCount = pageCount + 1;
            }
            int minCount = 0;
            int maxCount = 0;
            if(totalUserCount <10){
                minCount = totalUserCount;
            }else {
                minCount = 10;
            }

            String s = "Page 1 of "+pageCount+", showing "+minCount+" records out of "+totalUserCount+" total, starting on record 1, ending on "+minCount;

            tvSearchDesc.setText(s);
        }catch(NullPointerException npe){
            Log.e(TAG, " #Error : "+npe, npe);
        }catch(NumberFormatException nfe){
            Log.e(TAG, " #Error : "+nfe, nfe);
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
         mCurrentSearchLayout.setVisibility(View.VISIBLE);

     }

     adapter = new PaidSearchResultAdapter(PaidSearchPartnerActivity.this, null, queryList);
     mSearchResultRecyclerView.setAdapter(adapter);
     adapter.notifyDataSetChanged();

     LinearLayoutManager layoutManager = (LinearLayoutManager) mSearchResultRecyclerView.getLayoutManager();
     layoutManager.scrollToPositionWithOffset(0, 0);
     mResultScroll.fullScroll(View.FOCUS_UP);

//        int viewHeight = queryList.size() * 200;
//        mSearchResultRecyclerView.getLayoutParams().height = viewHeight;
    }

    /** API Integration */
    /** Search By ID */
    public void callWebServiceForSubsIDSearch(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
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
                    if(response.body().getMessage().getSuccess().equalsIgnoreCase("success")){
                        if(response.body().getQuery().size() > 0){
                            List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList = response.body().getQuery();

                            setPaidMatchedListID(queryList);
                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Alert", CONSTANTS.search_ptnr_err_msg);
                        }
                    }else {
//                        AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Search Partner", CONSTANTS.search_ptnr_err_msg);
//                        reTryMethod(1);;
                    }
                }
            }

            @Override
            public void onFailure(Call<PaidSubscriberResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(PaidSearchPartnerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
//                reTryMethod(1);
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSearchPartnerActivity.this);
        }

    }
    /** Search By Keyword */
    public void callWebServiceForSubsKeywordSearch(String curPage){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String keyword = prefs.getString(CONSTANTS.SEARCH_KEYWORD) ;
        String page = curPage ;

        if(NetworkClass.getInstance().checkInternet(PaidSearchPartnerActivity.this) == true){

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(PaidSearchPartnerActivity.this);
        Call<SubsAdvanceSearchResponse> call = apiInterface.keywordSearchPaid(token, page, keyword);
        call.enqueue(new Callback<SubsAdvanceSearchResponse>() {
            @Override
            public void onResponse(Call<SubsAdvanceSearchResponse> call, Response<SubsAdvanceSearchResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().equalsIgnoreCase("success")){
                        if(response.body().getUsers().size() > 0){

                            int pageCount = response.body().getTotalCount();
                            setupPageView(pageCount, selectedPage);

                            List<User> queryList = response.body().getUsers();
//                            System.out.print(profileList);

                            setPaidMatchedListByKeyword(queryList);
                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Alert", CONSTANTS.search_ptnr_err_msg);
                        }
                    }else {
//                        AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Search Partner", "Opps");
                        reTryMethod(2, curPage);
                    }

                }
            }

            @Override
            public void onFailure(Call<SubsAdvanceSearchResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(PaidSearchPartnerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
                reTryMethod(2, curPage);
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSearchPartnerActivity.this);
        }
    }
    /** Advance Search */
    public void callWebServiceForSubsAdvanceSearch(String curPage){

        List<String> profileList =new ArrayList<>();

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String minage = prefs.getString(CONSTANTS.MIN_AGE) ;
        String maxage = prefs.getString(CONSTANTS.MAX_AGE) ;
        String country = prefs.getString(CONSTANTS.COUNTRY_ID) ;

        String city =  removeLastChar(prefs.getString(CONSTANTS.CITY_ID));
        String[] cityArr = new String[1];
        cityArr = city.split(",");

        String religion =  returnEmpty(prefs.getString(CONSTANTS.RELIGION));

        String caste = removeLastChar(prefs.getString(CONSTANTS.CASTE));
        String[] casteArr = new String[1];
        casteArr = caste.split(",");

        String mother_tounge =  removeLastChar(prefs.getString(CONSTANTS.MOTHER_TONGUE));
        String[] mother_toungeArr = new String[1];
        mother_toungeArr = mother_tounge.split(",");

        String marital_status =  removeLastChar(prefs.getString(CONSTANTS.MARITAL_STATUS));
        String[] marital_statusArr = new String[1];
        marital_statusArr = marital_status.split(",");

        String course =  removeLastChar(prefs.getString(CONSTANTS.COURSE));
        String[] courseArr = new String[1];
        courseArr = course.split(",");

        String annual_income =  removeLastChar(prefs.getString(CONSTANTS.ANNUAL_INCOME));
        String[] annual_incomeArr = new String[1];
        annual_incomeArr = annual_income.split(",");

        String sPartnerLoc = removeLastChar(prefs.getString(CONSTANTS.PARTNER_LOC));
        String[] sPartnerLocArr = new String[1];
        sPartnerLocArr = sPartnerLoc.split(",");

        String sMinHeight = prefs.getString(CONSTANTS.MIN_HEIGHT);
        String sMaxHeight = prefs.getString(CONSTANTS.MAX_HEIGHT);

        String sCountryName = prefs.getString(CONSTANTS.COUNTRY);
        String sCityName = prefs.getString(CONSTANTS.CITY);


        profileList.add(minage);profileList.add(maxage);profileList.add(sCountryName);
        profileList.add(sCityName);profileList.add(religion);profileList.add(caste);
        profileList.add(mother_tounge);profileList.add(marital_status);
        profileList.add(course);profileList.add(annual_income);
        profileList.add(sPartnerLoc);profileList.add(sMinHeight);profileList.add(sMaxHeight);

        String page = curPage;

        if(NetworkClass.getInstance().checkInternet(PaidSearchPartnerActivity.this) == true){
                if ((Integer.parseInt(maxage) - Integer.parseInt(minage)) <= 5) {

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(PaidSearchPartnerActivity.this);

        Call<SubsAdvanceSearchResponse> call = apiInterface.advanceSearchPaid(token, page, minage, maxage, country, cityArr,sPartnerLocArr,religion,casteArr,mother_toungeArr,marital_statusArr,sMinHeight,sMaxHeight,courseArr,annual_incomeArr);


        call.enqueue(new Callback<SubsAdvanceSearchResponse>() {
            @Override
            public void onResponse(Call<SubsAdvanceSearchResponse> call, Response<SubsAdvanceSearchResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getMessage().getSuccess().equalsIgnoreCase("success")) {
                            if (response.body().getUsers().size() > 0) {

                                int pageCount = response.body().getTotalCount();
                                setupPageView(pageCount, selectedPage);

                                List<User> queryList = response.body().getUsers();
                                int totalUserCount = queryList.size();
                                System.out.print(profileList);

                                setPaidSearchedData(profileList, pageCount);
                                setPaidMatchedListByKeyword(queryList);

                            } else {
                                AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Alert", CONSTANTS.search_ptnr_err_msg);
                            }
                        } else {
                            AlertDialogSingleClick.getInstance().showDialog(PaidSearchPartnerActivity.this, "Search Partner", "Opps");
                            reTryMethod(3, curPage);
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
                reTryMethod(3, curPage);
            }
        });

                } else {
                    AlertDialogSingleClick.getInstance().showDialog(this, "Alert", AppMessage.AGE_DIFF_5_INFO);
                }
        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSearchPartnerActivity.this);
        }


    }

    private void setupPageView(int pages, String selectedPage){

        if(prefs.getString(CONSTANTPREF.PAGE_COUNT).equalsIgnoreCase("1")){
        int val = 0;
        int selectedVal = 0;
        try {
            selectedVal = Integer.parseInt(selectedPage);
        }catch (NumberFormatException nfe){
            Log.e("Paid Search Partner", "#Errror : "+nfe, nfe);
        }
        val = pages/10;
        int reminderVal = pages%10;
        if(reminderVal > 0){
            val  = val+ 1;
        }
        Button[] buttons = new Button[val];
        Space[] views = new Space[val];
        for(int i = 0; i< val; i++) {
            buttons[i] = new Button(PaidSearchPartnerActivity.this);
            buttons[i].setLayoutParams(new LinearLayout.LayoutParams(64, 64));
            buttons[i].setTag(i + 1);
            buttons[i].setText("" + (i + 1));
            buttons[i].setTextColor(getResources().getColor(R.color.colorWhite));
            buttons[i].setTextSize(12);
            if (i == selectedVal) {
                buttons[i].setBackgroundResource(R.drawable.round_view_green_border);

            } else {

                buttons[i].setBackgroundResource(R.drawable.round_view_yellow_border);
            }

            views[i] = new Space(PaidSearchPartnerActivity.this);
            views[i].setLayoutParams(new LinearLayout.LayoutParams(16, 64));

            mHorizontalLayout.addView(buttons[i]);
            mHorizontalLayout.addView(views[i]);

            //CLICKED

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < mHorizontalLayout.getChildCount(); i++) {
                        if (mHorizontalLayout.getChildAt(i) instanceof Button) {
                            mHorizontalLayout.getChildAt(i).
                                    setBackgroundResource(R.drawable.round_view_yellow_border);
                        }
                    }

                    String id = v.getTag().toString();
                    Button button2 = v.findViewWithTag(v.getTag());
                    button2.setBackgroundResource(R.drawable.round_view_green_border);
                    if (prefs.getString(CONSTANTPREF.SEARCH_TYPE).equalsIgnoreCase(CONSTANTS.SEARCH_BY_ID)) {
                        callWebServiceForSubsIDSearch();
                    } else if (prefs.getString(CONSTANTPREF.SEARCH_TYPE).equalsIgnoreCase(CONSTANTS.SEARCH_BY_KEYWORD)) {
                        callWebServiceForSubsKeywordSearch(id);
                    } else if (prefs.getString(CONSTANTPREF.SEARCH_TYPE).equalsIgnoreCase(CONSTANTS.ADVANCE_SEARCH)) {

                        callWebServiceForSubsAdvanceSearch(id);
                    }

                }
            });
        }


            prefs.putString(CONSTANTPREF.PAGE_COUNT, "2");
        }

    }

    public void reTryMethod(int pos, String page){

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
                            callWebServiceForSubsKeywordSearch(page);
                        }else if(pos == 3){
                            callWebServiceForSubsAdvanceSearch(page);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:

                if(mContainerFragLayout.getVisibility() == View.GONE){
                    mContainerFragLayout.setVisibility(View.VISIBLE);
                    mContainerResLayout.setVisibility(View.GONE);

                }else if(mContainerFragLayout.getVisibility() == View.VISIBLE) {
                    onBackNavigation();
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        if(mContainerFragLayout.getVisibility() == View.GONE){
            mContainerFragLayout.setVisibility(View.VISIBLE);
            mContainerResLayout.setVisibility(View.GONE);

        }else if(mContainerFragLayout.getVisibility() == View.VISIBLE) {
            onBackNavigation();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }


    public static String removeLastChar(String s) {
        String rawString = "";
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--") || s.equalsIgnoreCase("-")) {
            return s = "";
        }else if(s.endsWith(",")){

            return  rawString = s.substring(0, s.length()-1);
        }
        return s;
    }

    public static String removeWhiteSpace(String s) {
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--")) {
            return s = "[]";
        }
        return s.replaceAll("\\s+","");
//        st = st.replaceAll("\\s+","")
    }


    public static String returnEmpty(String s) {
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--")) {
            return s = "";
        }
        return s;
//        st = st.replaceAll("\\s+","")
    }

    public void onBackNavigation(){

        try{
            String userType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
            if (userType.equalsIgnoreCase("paid_subscriber_viewer")) {

                Intent intent = new Intent(this, PaidSubscriberDashboardActivity.class);
                startActivity(intent);
            } else if (userType.equalsIgnoreCase("subscriber_viewer")) {

                Intent intent = new Intent(this, SubscriberDashboardActivity.class);
                startActivity(intent);
            } else if (userType.equalsIgnoreCase("subscriber")) {

                Intent intent = new Intent(this, SubscriberDashboardActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, IntroSliderWebActivity.class);
                startActivity(intent);
            }
        }catch (NullPointerException npe){

            Log.e("TAG", "#Error : "+npe, npe);
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();

        }
    }

}
