package com.senzecit.iitiimshaadi.viewController;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.login.LoginResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.ResponseData;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.LoginRequest;
import com.senzecit.iitiimshaadi.payment.MakePaymentActivity;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.ConstantsPref;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private static final int SPLASH_DISPLAY_TIME = 2000;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        prefs = AppController.getInstance().getPrefs();
//
//        prefs.putString(Constants.LOGGED_TOKEN, "3b633a6ef617b476336b829da6e9d5c7");
//        prefs.putString(Constants.LOGGED_USER_TYPE, "subscriber_viewer");
//        prefs.putString(Constants.LOGGED_USERID, "30413");

/*
        prefs.putString(Constants.LOGGED_TOKEN, "e0e3d00067f8c0ed7e2f93739c4dbe6c");
        prefs.putString(Constants.LOGGED_EMAIL, "divakar1591@gmail.com");
        prefs.putString(Constants.LOGGED_MOB, "98765432210");
*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);
                Intent intent = new Intent(SplashActivity.this, PaidSubscriberDashboardActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
//***************************
         /*       try{
                    String userType = prefs.getString(Constants.LOGGED_USER_TYPE);
                    if (userType.equalsIgnoreCase("paid_subscriber_viewer")) {

                        Intent intent = new Intent(SplashActivity.this, PaidSubscriberDashboardActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else if (userType.equalsIgnoreCase("subscriber_viewer")) {

                        Intent intent = new Intent(SplashActivity.this, SubscriberDashboardActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else if (userType.equalsIgnoreCase("subscriber")) {

                        Intent intent = new Intent(SplashActivity.this, SubscriberDashboardActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
//                Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);
//                Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);
                        Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }catch (NullPointerException npe){

                    Log.e(TAG, "#Error : "+npe, npe);
                    Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
*/
            }
        },SPLASH_DISPLAY_TIME);
    }

    public void callWebServiceForSignin(){

        String sUsername = prefs.getString(ConstantsPref.LOGIN_USERNAME);
        String sPassword = prefs.getString(ConstantsPref.LOGIN_PASSWORD);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = sUsername;
        loginRequest.password = sPassword;

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(SplashActivity.this);
        Call<LoginResponse> call = apiInterface.loginInUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {

                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getResponseData() != null){
                            Toast.makeText(SplashActivity.this, "Succesfully", Toast.LENGTH_SHORT).show();
                            ResponseData responseData = response.body().getResponseData();
                            setPrefData(responseData);
                        }
                    }else {
                        Toast.makeText(SplashActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SplashActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    public void setPrefData(ResponseData response){

        String token = response.getToken();
        String userName = response.getUsername();
        String userId = String.valueOf(response.getUserid());
        String typeOfUser = response.getTypeOfUser();
        String profilePic = response.getProfileImage();

        prefs.putString(Constants.LOGGED_TOKEN, token);
        prefs.putString(Constants.LOGGED_USERNAME, userName);
        prefs.putString(Constants.LOGGED_USERID, userId);
        prefs.putString(Constants.LOGGED_USER_TYPE, typeOfUser);
        prefs.putString(Constants.LOGGED_USER_PIC, profilePic);

        navigateUserToScreen();
    }

    public void navigateUserToScreen(){

        try{
            String userType = prefs.getString(Constants.LOGGED_USER_TYPE);
            if (userType.equalsIgnoreCase("paid_subscriber_viewer")) {

                Intent intent = new Intent(SplashActivity.this, PaidSubscriberDashboardActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else if (userType.equalsIgnoreCase("subscriber_viewer")) {

                Intent intent = new Intent(SplashActivity.this, SubscriberDashboardActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else if (userType.equalsIgnoreCase("subscriber")) {

                Intent intent = new Intent(SplashActivity.this, SubscriberDashboardActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
//                Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);
//                Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);
                Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }catch (NullPointerException npe){

            Log.e(TAG, "#Error : "+npe, npe);
            Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }

}
