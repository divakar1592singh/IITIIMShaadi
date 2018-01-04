package com.senzecit.iitiimshaadi.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.fragment.ResultSearchPartnerFragment;
import com.senzecit.iitiimshaadi.fragment.SearchPartnerFragment;
import com.senzecit.iitiimshaadi.fragment.SearchResultDetailFragment;

public class SearchPartnerActivity extends AppCompatActivity implements View.OnClickListener,
        SearchPartnerFragment.SearchPartnerFragmentCommunicator,ResultSearchPartnerFragment.ResultSearchPartnerFragmentCommunicator {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    FrameLayout mFrameLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search_partner);
        init();
        searchPartnerFragment();
        mBack.setOnClickListener(this);
    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mFrameLayout = (FrameLayout) findViewById(R.id.search_partner_FL);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                Fragment home= mFragmentManager.findFragmentByTag("searchPartnerFragment");
                if(home!=null){
                    if(home.isVisible()){
                        startActivity(new Intent(SearchPartnerActivity.this, SubscriberDashboardActivity.class));
                    }else{
                        mFragmentManager.popBackStack("resultSearchPartnerFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment home= mFragmentManager.findFragmentByTag("searchPartnerFragment");
        if(home!=null){
            if(home.isVisible()){
                SearchPartnerActivity.this.finish();
            }else{
                mFragmentManager.popBackStack("resultSearchPartnerFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
        super.onBackPressed();
    }

    private void searchPartnerFragment(){
        SearchPartnerFragment searchPartnerFragment = new SearchPartnerFragment();
        setFragment(searchPartnerFragment,"searchPartnerFragment");
        searchPartnerFragment.setSearchPartnerFragmentCommunicator(this);
        mTitle.setText("Search Partner");
        mFragmentTransaction.addToBackStack("TAG");
    }
    private void setFragment(Fragment fragment, String tagName){
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.search_partner_FL,fragment,tagName);
        mFragmentTransaction.commit();
    }

    @Override
    public void saveSearchPartner() {
        searchResultFragment();
    }

    private void searchResultFragment(){
        ResultSearchPartnerFragment resultSearchPartnerFragment = new ResultSearchPartnerFragment();
        setFragment(resultSearchPartnerFragment,"resultSearchPartnerFragment");
        resultSearchPartnerFragment.setResultSearchPartnerFragmentCommunicator(this);
        mTitle.setText("Search Partner");
        mFragmentTransaction.addToBackStack("TAG");
    }

    private void detailSearchResult(){
        SearchResultDetailFragment searchResultDetailFragment = new SearchResultDetailFragment();
        setFragment(searchResultDetailFragment,"searchResultDetailFragment");
        mTitle.setText("Search Partner");
        mFragmentTransaction.addToBackStack("TAG");
    }
    @Override
    public void details() {
        detailSearchResult();
    }

    @Override
    public void partnerPref() {

    }

    @Override
    public void albums() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        SearchPartnerActivity.this.finish();
    }
}
