package com.senzecit.iitiimshaadi.viewController;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.PaidSearchResultAdapter;
import com.senzecit.iitiimshaadi.adapter.SearchResultAdapter;
import com.senzecit.iitiimshaadi.fragment.PaidSearchPartnerFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query;

import java.util.List;

public class ResultPaidSearchPartnerActivity extends AppCompatActivity implements PaidSearchPartnerFragment.PaidSearchPartnerFragmentCommunicator {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    FrameLayout mContainerFragLayout, mContainerResLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    PaidSearchPartnerFragment paidSearchPartnerFragment;

    RecyclerView mSearchResultRecyclerView;
    TextView mAgeMin,mAgeMax,mCountry,mCity,mReligion,mMotherTongue,mmaritalStatus,mIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result_paid_search_partner);

        getSupportActionBar().hide();

        paidSearchPartnerFragment = new PaidSearchPartnerFragment();

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.search_partner_FL, paidSearchPartnerFragment);
        mFragmentTransaction.commit();

        paidSearchPartnerFragment.setPaidSearchPartnerFragmentCommunicator(this);

        init();

    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mContainerFragLayout = (FrameLayout) findViewById(R.id.search_partner_FL);
        mContainerResLayout = (FrameLayout) findViewById(R.id.search_result_FL);

        mSearchResultRecyclerView = (RecyclerView) findViewById(R.id.partnerSearchResulttRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mSearchResultRecyclerView.setLayoutManager(layoutManager);

        mAgeMin = findViewById(R.id.minAgeTV);
        mAgeMax = findViewById(R.id.maxAgeTV);
        mCountry = findViewById(R.id.countryTV);
        mCity = findViewById(R.id.cityTV);
        mReligion = findViewById(R.id.religionTV);
        mMotherTongue = findViewById(R.id.motherTougeTV);
        mmaritalStatus = findViewById(R.id.maritalStatusTV);
        mIncome = findViewById(R.id.incomeTV);

    }

  /*  @Override
    public void saveSearchPartner(List<Query> queryList, List<String> profileList) {
//        mFragmentTransaction.remove(searchPartnerFragment);
        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);

        System.out.print(queryList);
        System.out.print(profileList);

        setSearchedData(profileList);
        setMatchedList(queryList);

    }
*/
    public void setSearchedData(List<String> profileList){
        mAgeMin.setText(profileList.get(0));
        mAgeMax.setText(profileList.get(1));
        mCountry.setText(profileList.get(2));
        mCity.setText(profileList.get(3));
        mReligion.setText(profileList.get(4));
        mMotherTongue.setText(profileList.get(6));
        mmaritalStatus.setText(profileList.get(7));
        mIncome.setText(profileList.get(9));
//        minage,maxage,country,city,religion,caste,mother_tounge,marital_status,course,annual_income
    }
    public void setMatchedList(List<Query> queryList){

//        minage,maxage,country,city,religion,caste,mother_tounge,marital_status,course,annual_income

        SearchResultAdapter adapter = new SearchResultAdapter(ResultPaidSearchPartnerActivity.this, queryList);
        mSearchResultRecyclerView.setAdapter(adapter);

    }

    @Override
    public void saveAndSearchPaidPartnerByID(List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList, String userid) {

        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);

        setPaidMatchedList(queryList);
    }

    @Override
    public void saveAndSearchPaidPartnerByKeyword(List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList, String keyword) {

        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);

        setPaidMatchedList(queryList);
    }

    @Override
    public void saveAndSearchPaidPartnerByAdvance(List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList, List<String> profileList) {

        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);

        setPaidSearchedData(profileList);
        setPaidMatchedList(queryList);

    }

    private void setPaidSearchedData(List<String> profileList){
        mAgeMin.setText(profileList.get(0));
        mAgeMax.setText(profileList.get(1));
        mCountry.setText(profileList.get(2));
        mCity.setText(profileList.get(3));
        mReligion.setText(profileList.get(4));
        mMotherTongue.setText(profileList.get(6));
        mmaritalStatus.setText(profileList.get(7));
        mIncome.setText(profileList.get(9));
//        minage,maxage,country,city,religion,caste,mother_tounge,marital_status,course,annual_income
    }
    private void setPaidMatchedList(List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList){

        PaidSearchResultAdapter adapter = new PaidSearchResultAdapter(ResultPaidSearchPartnerActivity.this, queryList);
        mSearchResultRecyclerView.setAdapter(adapter);


    }

}
