package com.senzecit.iitiimshaadi.viewController;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

public class PremierServicesActivity extends AppCompatActivity {

    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_premier_services);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        prefs = AppPrefs.getInstance(this);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
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
