package com.senzecit.iitiimshaadi.viewController;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;


public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 2000;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        prefs = AppController.getInstance().getPrefs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String userType = prefs.getString(Constants.LOGGED_USER_TYPE);

                Intent intent = new Intent(SplashActivity.this, SubscriberDashboardActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

 /*               if(userType.equalsIgnoreCase("paid_subscriber_viewer")) {

                    Intent intent = new Intent(SplashActivity.this, PaidSubscriberDashboardActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else if(userType.equalsIgnoreCase("subscriber_viewer")) {

                   Intent intent = new Intent(SplashActivity.this, SubscriberDashboardActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else if(userType.equalsIgnoreCase("subscriber")) {

                    Intent intent = new Intent(SplashActivity.this, SubscriberDashboardActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else {
//                Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);
//                Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);
                    Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
*/

            }
        },SPLASH_DISPLAY_TIME);
    }
}
