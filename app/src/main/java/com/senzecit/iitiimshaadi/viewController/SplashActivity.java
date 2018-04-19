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

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.model.commons.PreAuthWebRequest;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.Validation;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity implements RxNetworkingForObjectClass.CompletionHandler{

    private static final String TAG = "SplashActivity";
    private static final int SPLASH_DISPLAY_TIME = 2000;

    RxNetworkingForObjectClass networkingClass;
    PreAuthWebRequest request;
    AppPrefs prefs;
    ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        networkingClass = RxNetworkingForObjectClass.getInstance();
        networkingClass.setCompletionHandler(this);
        request = new PreAuthWebRequest();
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

        mProgressbar = findViewById(R.id.indeterminateBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//              Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);
////



              Intent intent = new Intent(SplashActivity.this, SubscriptionPlanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();




             /*   if(NetworkClass.getInstance().checkInternet(SplashActivity.this) == true){

                }else {
                    NetworkDialogHelper.getInstance().showDialog(SplashActivity.this);
                }*/

//***************************

            if(NetworkClass.getInstance().checkInternet(SplashActivity.this) == true){
//                callWebServiceForSignin();
            }else {
                networkDialog();
            }
          }

           
        },SPLASH_DISPLAY_TIME);
    }

    private void callWebServiceForSignin() {
        String sUsername = prefs.getString(CONSTANTPREF.LOGIN_USERNAME);
        String sPassword = prefs.getString(CONSTANTPREF.LOGIN_PASSWORD);

        if (Validation.handler().isNotEmpty(SplashActivity.this, sUsername, AppMessage.USERNAME_EMPTY)) {
            if (Validation.handler().isNotEmpty(SplashActivity.this, sPassword, AppMessage.PASSWORD_EMPTY)) {
                request.username = sUsername;
                request.password = sPassword;
//                mProgressbar.setVisibility(View.VISIBLE);
                RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(SplashActivity.this, CONSTANTS.LOGIN_PART_URL, request, CONSTANTS.METHOD_1, false);
            }
        }else {
            navigatePage();
        }

        
        
    }

    public void navigatePage(){

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

    @Override
    public void handle(JSONObject object, String methodName) {

        System.out.println(object);
//        mProgressbar.setVisibility(View.INVISIBLE);
        try {

            if(methodName.equalsIgnoreCase(CONSTANTS.METHOD_1)) {
                if(object.getJSONObject("message").getInt("response_code") == 200) {

                    String token = object.getJSONObject("responseData").getString("token");
                    String userName = object.getJSONObject("responseData").getString("username");
                    String userId = String.valueOf(object.getJSONObject("responseData").getString("userid"));
                    String typeOfUser = object.getJSONObject("responseData").getString("type_of_user");
                    String email = object.getJSONObject("responseData").getString("email");
                    String mobile = object.getJSONObject("responseData").getString("mobile_no");
                    String profilePic = object.getJSONObject("responseData").getString("profile_image");
                    String gender = object.getJSONObject("responseData").getString("gender");

                    prefs.putString(CONSTANTS.LOGGED_TOKEN, token);
                    prefs.putString(CONSTANTS.LOGGED_USERNAME, userName);
                    prefs.putString(CONSTANTS.LOGGED_USERID, userId);
                    prefs.putString(CONSTANTS.LOGGED_USER_TYPE, typeOfUser);
                    prefs.putString(CONSTANTS.LOGGED_EMAIL, email);
                    prefs.putString(CONSTANTS.LOGGED_MOB, mobile);
                    prefs.putString(CONSTANTS.LOGGED_USER_PIC, profilePic);
                    prefs.putString(CONSTANTS.GENDER_TYPE, gender);

                    if (typeOfUser.equalsIgnoreCase("paid_subscriber_viewer")) {
                        Navigator.getClassInstance().navigateToActivity(SplashActivity.this, PaidSubscriberDashboardActivity.class);
                    } else if (typeOfUser.equalsIgnoreCase("subscriber_viewer")) {
                        Navigator.getClassInstance().navigateToActivity(SplashActivity.this, SubscriberDashboardActivity.class);
                    } else if (typeOfUser.equalsIgnoreCase("subscriber")) {
                        Navigator.getClassInstance().navigateToActivity(SplashActivity.this, SubscriberDashboardActivity.class);
                    }
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(SplashActivity.this, AppMessage.ALERT, AppMessage.USERPWD_INVALID);
                }
            }else if(methodName.equalsIgnoreCase(CONSTANTS.METHOD_2)){

                if(object.getJSONObject("message").getInt("response_code") == 200) {
                    AlertDialogSingleClick.getInstance().showDialog(SplashActivity.this, AppMessage.INFO, "An email with new password is sent to your registered email.");
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(SplashActivity.this, AppMessage.ALERT, AppMessage.USER_VALID);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

            Log.e(TAG, "#Error : "+npe, npe);
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();

        }
    }
}
