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
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.SearchResultAdapter;
import com.senzecit.iitiimshaadi.fragment.SearchPartnerFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.User;

import java.util.List;

import io.reactivex.Observable;

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

    public void handleView(){
        mTitle.setText("Search Partner");
        mBack.setOnClickListener(this);
    }

    @Override
    public void saveSearchPartner(List<User> queryList, List<String> profileList) {

        mBack.setOnClickListener(this);
        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);

        System.out.print(queryList);
        System.out.print(profileList);

        setSearchedData(profileList);
        setMatchedList(queryList);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:

//                Observable.just(mContainerFragLayout)
//                        .filter(layout -> layout.getVisibility() == View.VISIBLE)
//                        .subscribe(layout -> {
//                            // Do something with it
//                            System.out.println("Visible");
//                            Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
//                        }, throwable1 -> {
//                            //
//                            System.out.println("Not Visible");
//                            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
//                        });

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
