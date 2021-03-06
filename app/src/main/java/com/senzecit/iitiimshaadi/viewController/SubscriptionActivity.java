package com.senzecit.iitiimshaadi.viewController;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.fragment.SubscriptionFragment;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener,SubscriptionFragment.SubscriptionFragmentCommunicator {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    FrameLayout mFrameLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_subscription);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        prefs = AppPrefs.getInstance(this);
        init();
        mBack.setOnClickListener(this);
//        subscriptionPlanFragment();
        subscriptionFragment();
    }

    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Subscription");

        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                startActivity(new Intent(SubscriptionActivity.this, SubscriberDashboardActivity.class));
                finishActivity(0);
//                startActivity(new Intent(SubscriptionActivity.this, SubscriptionActivity.class));
                finishActivity(0);
//                SubscriptionActivity.this.finish();
//               /* Fragment home= mFragmentManager.findFragmentByTag("subscriptionFragment");
//                if(home!=null){
//                    if(home.isVisible()){
//                        SubscriptionActivity.this.finish();
//                    }else{
//                        mFragmentManager.popBackStack("subscriptionPlanFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    }
//                }*/
                super.onBackPressed();
                break;
        }
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackNavigation();
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
