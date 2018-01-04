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
import com.senzecit.iitiimshaadi.fragment.SubscriptionFragment;
import com.senzecit.iitiimshaadi.fragment.SubscriptionPlanFragment;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener,SubscriptionFragment.SubscriptionFragmentCommunicator {
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
        setContentView(R.layout.activity_subscription);
        init();
        mBack.setOnClickListener(this);
//        subscriptionPlanFragment();
        subscriptionFragment();
    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Subscription");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                Fragment home= mFragmentManager.findFragmentByTag("subscriptionFragment");
                if(home!=null){
                    if(home.isVisible()){
                        SubscriptionActivity.this.finish();
                    }else{
                        mFragmentManager.popBackStack("subscriptionPlanFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        /*int count = mFragmentManager.getBackStackEntryCount();
        if(count>1){
            Fragment home= mFragmentManager.findFragmentByTag("subscriptionFragment");
            if(home!=null){
                if(home.isVisible()){
                    SubscriptionActivity.this.finish();
                }else{
                    mFragmentManager.popBackStack("subscriptionPlanFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
            super.onBackPressed();
        }else{
//            Toast.makeText(ChatMessagesActivity.this, "Finish", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(SubscriptionActivity.this, PaidSubscriberDashboardActivity.class));
            SubscriptionActivity.this.finish();
        }
        super.onBackPressed();*/

        Fragment home= mFragmentManager.findFragmentByTag("subscriptionFragment");
        if(home!=null){
            if(home.isVisible()){
                SubscriptionActivity.this.finish();
            }else{
                mFragmentManager.popBackStack("subscriptionPlanFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
        super.onBackPressed();
    }

    private void subscriptionPlanFragment(){
       /* SubscriptionPlanFragment subscriptionPlanFragment = new SubscriptionPlanFragment();
        setFragment(subscriptionPlanFragment,"subscriptionPlanFragment");
        mFragmentTransaction.addToBackStack("TAG");
        mTitle.setText("Plans");*/
       startActivity(new Intent(SubscriptionActivity.this, SubscriptionPlanActivity.class));

    }

    private void subscriptionFragment(){
        SubscriptionFragment subscriptionFragment = new SubscriptionFragment();
        setFragment(subscriptionFragment,"subscriptionFragment");
        subscriptionFragment.setSubscriptionFragmentCommunicator(this);
        mFragmentTransaction.addToBackStack("TAG");
    }

    private void setFragment(Fragment fragment, String tagName){
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.main_subscription_FL,fragment,tagName);
        mFragmentTransaction.commit();
    }

    @Override
    public void upgradeSubscription() {
        subscriptionPlanFragment();
    }
}
