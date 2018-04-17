package com.senzecit.iitiimshaadi.viewController;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.SearchResultAdapter;
import com.senzecit.iitiimshaadi.fragment.SearchPartnerFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.User;

import java.util.List;

public class SearchPartnerActivity extends AppCompatActivity implements  SearchPartnerFragment.SearchPartnerFragmentCommunicator, View.OnClickListener {

    private static final String TAG = "ResultSearcPartner";
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    FrameLayout mContainerFragLayout, mContainerResLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SearchPartnerFragment searchPartnerFragment;

    RecyclerView mSearchResultRecyclerView;
    TextView mAgeMin,mAgeMax,mCountry,mCity,mReligion,mMotherTongue,mmaritalStatus,mIncome;

    TextView mTotalCountTV, mMinRecordTV, mMaxRecord, mEndRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result_search_partner);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        getSupportActionBar().hide();

        searchPartnerFragment = new SearchPartnerFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.search_partner_FL, searchPartnerFragment);
        mFragmentTransaction.commit();

        searchPartnerFragment.setSearchPartnerFragmentCommunicator(this);

        init();
        handleView();

    }

    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mContainerFragLayout = findViewById(R.id.search_partner_FL);
        mContainerResLayout = findViewById(R.id.search_result_FL);

        mSearchResultRecyclerView = findViewById(R.id.partnerSearchResulttRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mSearchResultRecyclerView.setLayoutManager(layoutManager);

        //
        mTotalCountTV = findViewById(R.id.idTotalCountTV);
        mMinRecordTV = findViewById(R.id.idMinRecord);
        mMaxRecord = findViewById(R.id.idMaxRecord);
        mEndRecord = findViewById(R.id.idEndRecord);


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
        mBack.setOnClickListener(this);
    }

    @Override
    public void saveSearchPartner(List<User> queryList, List<String> profileList, int totalUserCount)
    {

        mBack.setOnClickListener(this);
        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);

        System.out.print(queryList);
        System.out.print(profileList);

        setSearchedData(profileList);
        setMatchedList(queryList);


        try {
           /* mTotalCountTV.setText(String.valueOf(totalUserCount/10));
            if(totalUserCount <10){
                mMinRecordTV.setText(String.valueOf(totalUserCount));
            }else {
                mMinRecordTV.setText("10");
            }
            mMaxRecord.setText(String.valueOf(totalUserCount));
            if(totalUserCount <10){
                mEndRecord.setText(String.valueOf(totalUserCount));
            }else {
                mEndRecord.setText("10");
            }*/
           int pageCount = (totalUserCount/10)+1;
           int minCount = 0;
           int maxCount = 0;
            if(totalUserCount <10){
                minCount = totalUserCount;
            }else {
                minCount = 10;
            }

          /*  if(totalUserCount <10){
                mEndRecord.setText(String.valueOf(totalUserCount));
            }else {
                mEndRecord.setText("10");
            }
            */
           String s = "Page 1 of "+pageCount+", showing "+minCount+" records out of "+totalUserCount+" total, starting on record 1, ending on "+minCount;

            mTotalCountTV.setText(s);
        }catch(NullPointerException npe){
            Log.e(TAG, " #Error : "+npe, npe);
        }catch(NumberFormatException nfe){
            Log.e(TAG, " #Error : "+nfe, nfe);
        }

    }

    private void setSearchedData(List<String> profileList){
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
            Log.e(TAG, " #Error : "+ioe, ioe);
        }

    }
    private void setMatchedList(List<User> queryList){

        SearchResultAdapter adapter = new SearchResultAdapter(SearchPartnerActivity.this, queryList);
        mSearchResultRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.backIV:

                if(mContainerFragLayout.getVisibility() == View.GONE){
                    mContainerFragLayout.setVisibility(View.VISIBLE);
                    mContainerResLayout.setVisibility(View.GONE);

                }else if(mContainerFragLayout.getVisibility() == View.VISIBLE) {
                    SearchPartnerActivity.this.finish();
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
            SearchPartnerActivity.this.finish();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }


}
