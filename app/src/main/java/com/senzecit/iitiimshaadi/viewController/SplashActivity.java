package com.senzecit.iitiimshaadi.viewController;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.chat.SocketSingleChatActivity;
import com.senzecit.iitiimshaadi.model.api_response_model.login.LoginResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.ResponseData;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.LoginRequest;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
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
//        prefs.putString(CONSTANTS.LOGGED_TOKEN, "6b93c8d29b0d49e460db1a897e087aca");
        prefs.putString(CONSTANTS.LOGGED_TOKEN, "a05e22d25590c21124f72e189947b03e"); // swati



/*
        prefs.putString(CONSTANTS.LOGGED_USERID, "23593");
        prefs.putString(CONSTANTS.OTHER_USERID, "30413");
*/

/*
        prefs.putString(CONSTANTS.LOGGED_USERID, "30413");
        prefs.putString(CONSTANTS.OTHER_USERID, "23593");

        prefs.putString(CONSTANTS.LOGGED_USER_TYPE, "subscriber_viewer");
        prefs.putString(CONSTANTS.LOGGED_EMAIL, "diwakar@senzecit.com");
        prefs.putString(CONSTANTS.LOGGED_USERNAME, "diwakar");
        prefs.putString(CONSTANTS.LOGGED_MOB, "8860807707");
        prefs.putString(CONSTANTS.LOGGED_USER_PIC, "1520594768CROP_1520594760668.jpg");
*/


//        prefs.putString(CONSTANTS.LOGGED_USERID, "30413");
/*
        prefs.putString(CONSTANTS.LOGGED_TOKEN, "e0e3d00067f8c0ed7e2f93739c4dbe6c");
        prefs.putString(CONSTANTS.LOGGED_EMAIL, "divakar1591@gmail.com");
        prefs.putString(CONSTANTS.LOGGED_MOB, "98765432210");
*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


//              Intent intent = new Intent(SplashActivity.this, IntroSliderWebActivity.class);

               Intent intent = new Intent(SplashActivity.this, PaidSearchPartnerActivity.class);
//                Intent intent = new Intent(SplashActivity.this, PaidSearchPartnerActivity.class);Intent intent = new Intent(SplashActivity.this, PaidSearchPartnerActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


             /*   if(NetworkClass.getInstance().checkInternet(SplashActivity.this) == true){

                }else {
                    NetworkDialogHelper.getInstance().showDialog(SplashActivity.this);
                }*/

//***************************

/*                try{
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

//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }*/
            }
        },SPLASH_DISPLAY_TIME);
    }

    public void callWebServiceForSignin(){

        String sUsername = prefs.getString(CONSTANTPREF.LOGIN_USERNAME);
        String sPassword = prefs.getString(CONSTANTPREF.LOGIN_PASSWORD);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = sUsername;
        loginRequest.password = sPassword;

        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
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

        prefs.putString(CONSTANTS.LOGGED_TOKEN, token);
        prefs.putString(CONSTANTS.LOGGED_USERNAME, userName);
        prefs.putString(CONSTANTS.LOGGED_USERID, userId);
        prefs.putString(CONSTANTS.LOGGED_USER_TYPE, typeOfUser);
        prefs.putString(CONSTANTS.LOGGED_USER_PIC, profilePic);

        navigateUserToScreen();
    }

    public void navigateUserToScreen(){

        try{
            String userType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
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

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}
