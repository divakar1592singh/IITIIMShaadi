package com.senzecit.iitiimshaadi.viewController;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private static final int SPLASH_DISPLAY_TIME = 2000;
    AppPrefs prefs;
    ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        prefs = AppController.getInstance().getPrefs();
//        prefs.putString(CONSTANTS.LOGGED_TOKEN, "gffg");
//
//        prefs.putString(CONSTANTS.LOGGED_TOKEN, "85a53c74c747cd3e551cb7d1fd223fcf");
//        prefs.putString(CONSTANTS.LOGGED_TOKEN, null);

//        prefs.putString(CONSTANTS.LOGGED_USER_TYPE, "subscriber");
//        prefs.putString(CONSTANTS.LOGGED_TOKEN, "1ebb5c58bab7f813bff33fe5405b8d9e"); // swati


//        prefs.putString(CONSTANTS.LOGGED_USERID, "23593");
//        prefs.putString(CONSTANTS.OTHER_USERID, "30413");


//        prefs.putString(CONSTANTS.LOGGED_USERID, "30413");
//        prefs.putString(CONSTANTS.OTHER_USERID, "23593");


//        prefs.putString(CONSTANTS.LOGGED_USERNAME, "diwakar");
//        prefs.putString(CONSTANTS.LOGGED_USERID, "9798");
//
//        prefs.putString(CONSTANTS.LOGGED_USER_TYPE, "subscriber");
//        prefs.putString(CONSTANTS.LOGGED_EMAIL, "diwakar@senzecit.com");
//        prefs.putString(CONSTANTS.LOGGED_USERNAME, "diwakar");
//        prefs.putString(CONSTANTS.LOGGED_MOB, "8860807707");
//        prefs.putString(CONSTANTS.LOGGED_USER_PIC, "1521821548CROP_1523981481474.jpg");


//        prefs.putString(CONSTANTS.GENDER_TYPE, "female");
/*
        prefs.putString(CONSTANTS.LOGGED_TOKEN, "e0e3d00067f8c0ed7e2f93739c4dbe6c");
        prefs.putString(CONSTANTS.LOGGED_EMAIL, "divakar1591@gmail.com");
        prefs.putString(CONSTANTS.LOGGED_MOB, "98765432210");
*/

        mProgressbar = (ProgressBar)findViewById(R.id.indeterminateBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//              Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);
//
//              Intent intent = new Intent(SplashActivity.this, FriendsActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();




             /*   if(NetworkClass.getInstance().checkInternet(SplashActivity.this) == true){

                }else {
                    NetworkDialogHelper.getInstance().showDialog(SplashActivity.this);
                }*/

//***************************

/*https://stackoverflow.com/questions/
39190058/when-settext-on-edittext-textwatcher-ontextchanged-not-called*/



//                callWebServiceForSignin();

            if(NetworkClass.getInstance().checkInternet(SplashActivity.this) == true){
                navigatyPage();
            }else {
                networkDialog();
            }


            }
        },SPLASH_DISPLAY_TIME);
    }

    public void navigatyPage(){

        try{
            String userType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
            if (userType.equalsIgnoreCase("paid_subscriber_viewer")) {

                Intent intent = new Intent(SplashActivity.this, PaidSubscriberDashboardActivity.class);

//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                        finish();
            } else if (userType.equalsIgnoreCase("subscriber_viewer")) {

                Intent intent = new Intent(SplashActivity.this, SubscriberDashboardActivity.class);

//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                        finish();
            } else if (userType.equalsIgnoreCase("subscriber")) {

                Intent intent = new Intent(SplashActivity.this, SubscriberDashboardActivity.class);

//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                        finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);

//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                        finish();
            }
        }catch (NullPointerException npe){

            Log.e(TAG, "#Error : "+npe, npe);
            Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);

            startActivity(intent);
            finish();
        }

    }

    public void networkDialog(){
        new AlertDialog.Builder(SplashActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("No Network")
                .setMessage("Check Your Internet")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        finishActivity(0);
                        dialog.dismiss();
                    }

                })
                .show();
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}
