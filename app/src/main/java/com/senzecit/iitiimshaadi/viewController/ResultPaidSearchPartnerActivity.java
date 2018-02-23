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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.PaidSearchResultAdapter;
import com.senzecit.iitiimshaadi.adapter.SearchResultAdapter;
import com.senzecit.iitiimshaadi.fragment.PaidSearchPartnerFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query;

import java.util.ArrayList;
import java.util.List;

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

    RecyclerView mSearchResultRecyclerView;
    LinearLayoutManager layoutManager;
    PaidSearchResultAdapter adapter;
    int pageCount = 10;

    List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList1;
    List<Query> queryList2;

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

    @Override
    protected void onStart() {
        super.onStart();
        handleView();
    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mContainerFragLayout = (FrameLayout) findViewById(R.id.search_partner_FL);
        mContainerResLayout = (FrameLayout) findViewById(R.id.search_result_FL);

        mCurrentSearchLayout = (LinearLayout)findViewById(R.id.idCurrentSearchLayout);

        mSearchResultRecyclerView = (RecyclerView) findViewById(R.id.partnerSearchResulttRV);
        layoutManager = new LinearLayoutManager(this);
        mSearchResultRecyclerView.setLayoutManager(layoutManager);

        mAgeMin = findViewById(R.id.minAgeTV);
        mAgeMax = findViewById(R.id.maxAgeTV);
        mCountry = findViewById(R.id.countryTV);
        mCity = findViewById(R.id.cityTV);
        mReligion = findViewById(R.id.religionTV);
        mMotherTongue = findViewById(R.id.motherTougeTV);
        mmaritalStatus = findViewById(R.id.maritalStatusTV);
        mIncome = findViewById(R.id.incomeTV);

        mSearchResultRecyclerView.setHasFixedSize(false);
    }

    public void handleView(){

        queryList1 = new ArrayList<>();
        queryList2 = new ArrayList<>();

        mSearchResultRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int itemLastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                Toast.makeText(ResultPaidSearchPartnerActivity.this, "Last Count : "+itemLastVisiblePosition, Toast.LENGTH_SHORT).show();

                if(itemLastVisiblePosition == adapter.getItemCount() - 1) {

                   /* if (queryList1.size() > 0) {

                        if (!loading && itemLastVisiblePosition <= queryList1.size()) {
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                boolean canScrollDownMore = recyclerView.canScrollVertically(1);
                                if (!canScrollDownMore) {

//                                fetchData((itemLastVisiblePosition + 1));

                                    setPaidMatchedListByKeyword(queryList2, pageCount + 10);

                                    onScrolled(recyclerView, 0, 1);
                                }
                            }
                        }
                    }

                }else*/
                   Toast.makeText(ResultPaidSearchPartnerActivity.this, "Count : "+queryList2.size(), Toast.LENGTH_SHORT).show();
                    if (queryList2.size() > 0) {

                        if (!loading && itemLastVisiblePosition <= queryList2.size()) {
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                boolean canScrollDownMore = recyclerView.canScrollVertically(1);
                                if (!canScrollDownMore) {

//                                fetchData((itemLastVisiblePosition + 1));

                                    setPaidMatchedListByKeyword(queryList2, pageCount + 10);

                                    onScrolled(recyclerView, 0, 1);
                                }
                            }
                        }

                    }
                }
            }
        });
    }

    @Override
    public void saveAndSearchPaidPartnerByID(List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList, String userid) {

        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);
        mCurrentSearchLayout.setVisibility(View.GONE);

        setPaidMatchedList(queryList);
    }

    @Override
    public void saveAndSearchPaidPartnerByKeyword(List<Query> queryList, String keyword) {

        queryList2.addAll(queryList);
        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);
        mCurrentSearchLayout.setVisibility(View.GONE);

        setPaidMatchedListByKeyword(queryList, pageCount);
    }

    @Override
    public void saveAndSearchPaidPartnerByAdvance(List<Query> queryList, List<String> profileList) {

        mContainerFragLayout.setVisibility(View.GONE);
        mContainerResLayout.setVisibility(View.VISIBLE);
        mCurrentSearchLayout.setVisibility(View.VISIBLE);

        setPaidSearchedData(profileList);

        setPaidMatchedListByKeyword(queryList, pageCount);


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

        adapter = new PaidSearchResultAdapter(ResultPaidSearchPartnerActivity.this, queryList, null, 10);
        mSearchResultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
 private void setPaidMatchedListByKeyword(List<Query> queryList, int pageCount){

        adapter = new PaidSearchResultAdapter(ResultPaidSearchPartnerActivity.this, null, queryList, pageCount);
        mSearchResultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
