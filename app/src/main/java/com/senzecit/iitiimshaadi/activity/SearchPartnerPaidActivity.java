package com.senzecit.iitiimshaadi.activity;

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
import com.senzecit.iitiimshaadi.fragment.PaidSearchPartnerFragment;
import com.senzecit.iitiimshaadi.fragment.ResultPaidSearchPartnerFragment;
import com.senzecit.iitiimshaadi.fragment.PaidSearchResultDetailFragment;


public class SearchPartnerPaidActivity extends AppCompatActivity implements View.OnClickListener,
        PaidSearchPartnerFragment.PaidSearchPartnerFragmentCommunicator,ResultPaidSearchPartnerFragment.ResultPaidSearchPartnerFragmentCommunicator{

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
        setContentView(R.layout.activity_search_partner_paid);
        init();
        paidSearchPartnerFragment();

        mBack.setOnClickListener(this);
    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mFrameLayout = (FrameLayout) findViewById(R.id.search_partner_paid_FL);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                Fragment home= mFragmentManager.findFragmentByTag("searchPartnerFragment");
                if(home!=null){
                    if(home.isVisible()){
                        SearchPartnerPaidActivity.this.finish();
                    }else{
                        mFragmentManager.popBackStack("resultPaidSearchPartnerFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
                SearchPartnerPaidActivity.this.finish();
            }else{
                mFragmentManager.popBackStack("resultPaidSearchPartnerFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
        super.onBackPressed();
    }

    private void paidSearchPartnerFragment(){
        PaidSearchPartnerFragment searchPartnerFragment = new PaidSearchPartnerFragment();
        setFragment(searchPartnerFragment,"searchPartnerFragment");
        mTitle.setText("Search Partner");
        searchPartnerFragment.setPaidSearchPartnerFragmentCommunicator(this);
        mFragmentTransaction.addToBackStack("TAG");

    }

    private void setFragment(Fragment fragment, String tagName){
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.search_partner_paid_FL,fragment,tagName);
        mFragmentTransaction.commit();
    }

    @Override
    public void saveAndSearchPartner() {
        resultSearchPartner();
    }

    private void resultSearchPartner(){
        ResultPaidSearchPartnerFragment resultPaidSearchPartnerFragment = new ResultPaidSearchPartnerFragment();
        setFragment(resultPaidSearchPartnerFragment,"resultPaidSearchPartnerFragment");
        resultPaidSearchPartnerFragment.setResultPaidSearchPartnerFragmentCommunicator(this);
        mFragmentTransaction.addToBackStack("TAG");
    }

    @Override
    public void details() {
        searchResultDetails();
    }

    @Override
    public void partnerPref() {

    }

    @Override
    public void albums() {

    }

    private void searchResultDetails(){
        PaidSearchResultDetailFragment searchResultDetailFragment = new PaidSearchResultDetailFragment();
        setFragment(searchResultDetailFragment,"searchResultDetailFragment");
        mTitle.setText("Search Partner Details");
//        searchPartnerFragment.setPaidSearchPartnerFragmentCommunicator(this);
        mFragmentTransaction.addToBackStack("TAG");
    }
}
