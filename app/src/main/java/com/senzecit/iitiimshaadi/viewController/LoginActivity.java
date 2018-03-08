package com.senzecit.iitiimshaadi.viewController;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.forgot_password.ForgotPasswordResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.LoginResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.ResponseData;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.ForgotPasswordRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.LoginRequest;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.ConstantsPref;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private  static final String TAG = "LoginActivity";
    EditText mPassword,mUsername;
    TextView mRegisterNew,mForgotPassword;
    Button mLogin;
    Toolbar mToolbar;
    TextView mTitle;
    String sdUsername = "";
    AppPrefs prefs;

    /** Network*/
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = new AppPrefs(LoginActivity.this);

        init();
        handleView();

    }

    private void init(){
        mPassword = (EditText) findViewById(R.id.passwordET);
        mUsername = (EditText) findViewById(R.id.userNameET);
        mRegisterNew = (TextView) findViewById(R.id.registerTV);
        mForgotPassword = (TextView) findViewById(R.id.forgotpasswordTV);
        mLogin = (Button) findViewById(R.id.loginBtn);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText("Login");
    }

    public void handleView(){
        mForgotPassword.setOnClickListener(this);
        mRegisterNew.setOnClickListener(this);
        mLogin.setOnClickListener(this::checkUserValidation);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgotpasswordTV:
                alertDialog();
                break;
            case R.id.registerTV:
                Navigator.getClassInstance().navigateToActivity(this, QuickRegistrationActivity.class);
//                startActivity(new Intent(LoginActivity.this,QuickRegistrationActivity.class));
                break;
            case R.id.loginBtn:
//                Navigator.getClassInstance().navigateToActivity(this, PaidSubscriberDashboardActivity.class);
//                startActivity(new Intent(LoginActivity.this,PaidSubscriberDashboardActivity.class));
//                checkUserValidation();
                break;
        }
    }


    /** VALIDATION SECTION */
    public void checkUserValidation(View view){

        String sUsername = mUsername.getText().toString().trim();
        String sPassword = mPassword.getText().toString().trim();

        mPassword.getText().toString().trim();
        if(!sUsername.isEmpty()){
            if(!sPassword.isEmpty()){

//                new AlertDialogSingleClick().showDialog(LoginActivity.this, "Alert!", "Login validation passed");
                callWebServiceForSignin();
            }else {
                AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Alert!", "Password can't Empty");
            }
        }else {
            AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Alert!", "Username/Email can't Empty");
        }


    }

    /** Check API Section */
    public void callWebServiceForSignin(){

        String sUsername = mUsername.getText().toString().trim();
        String sPassword = mPassword.getText().toString().trim();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = sUsername;
        loginRequest.password = sPassword;

        ProgressClass.getProgressInstance().showDialog(LoginActivity.this);
        Call<LoginResponse> call = apiInterface.loginInUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                try {
                    if (response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")) {
                        if (response.body().getResponseData() != null) {
                            Toast.makeText(LoginActivity.this, "Succesfully", Toast.LENGTH_SHORT).show();
                            ResponseData responseData = response.body().getResponseData();
                            setPrefData(responseData);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                    }
                }catch (NullPointerException npe){
                    Log.e(TAG, " #Error"+npe, npe);
                    AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Alert", "Invalid username or password, try again");
                }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    public void setPrefData(ResponseData response){

        String sUsername = mUsername.getText().toString().trim();
        String sPassword = mPassword.getText().toString().trim();

        String token = response.getToken();
        String userName = response.getUsername();
        String userId = String.valueOf(response.getUserid());
        String typeOfUser = response.getTypeOfUser();
        String profilePic = response.getProfileImage();

        prefs.putString(ConstantsPref.LOGIN_USERNAME, sUsername);
        prefs.putString(ConstantsPref.LOGIN_PASSWORD, sPassword);

        prefs.putString(CONSTANTS.LOGGED_TOKEN, token);
        prefs.putString(CONSTANTS.LOGGED_USERNAME, userName);
        prefs.putString(CONSTANTS.LOGGED_USERID, userId);
        prefs.putString(CONSTANTS.LOGGED_USER_TYPE, typeOfUser);
        prefs.putString(CONSTANTS.LOGGED_USER_PIC, profilePic);

        navigateUserToScreen(typeOfUser);
    }

    public void navigateUserToScreen(String typeOfUser){
        if(typeOfUser.equalsIgnoreCase("paid_subscriber_viewer")){
            Navigator.getClassInstance().navigateToActivity(LoginActivity.this, PaidSubscriberDashboardActivity.class);
        }else if(typeOfUser.equalsIgnoreCase("subscriber_viewer")){
            Navigator.getClassInstance().navigateToActivity(LoginActivity.this, SubscriberDashboardActivity.class);
        }else if(typeOfUser.equalsIgnoreCase("subscriber")){
            Navigator.getClassInstance().navigateToActivity(LoginActivity.this, SubscriberDashboardActivity.class);
        }
    }

    /** Forgot Password*/
    private void alertDialog(){

        final Button mConfirm,mLogin;
        EditText mUsernameOfForgotET;

        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        final View dialogView = inflater.inflate(R.layout.forgot_password_layout,null);

        mConfirm = dialogView.findViewById(R.id.confirmBtn);
        mLogin = dialogView.findViewById(R.id.loginFPLBtn);
        mUsernameOfForgotET = dialogView.findViewById(R.id.userNameForgotET);

        mUsernameOfForgotET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                System.out.println("Email/Username is : "+editable.toString());
                sdUsername = editable.toString();
            }
        });
//        final String finalSUsername = sdUsername;
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog.dismiss();
                System.out.println("Email/Username is : "+sdUsername);
                if(!sdUsername.isEmpty()){
                    if(isValidEmail(sdUsername)){

                        callWebServiceForForgotPassword(sdUsername);
                        dialog.dismiss();
//                        new AlertDialogSingleClick().showDialog(LoginActivity.this, "Alert!", "Email validation succesfull");
                    }else {
                        AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Alert!", "Email not valid");
                    }
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Alert!", "Email can't Empty");
                }
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                checkUserValidation();
                dialog.dismiss();
            }
        });


        dialog.setView(dialogView);
        dialog.show();
    }

    public void callWebServiceForForgotPassword(String username){

        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.username = username;

        ProgressClass.getProgressInstance().showDialog(LoginActivity.this);
        Call<ForgotPasswordResponse> call = apiInterface.forgotPasswordOfUser(forgotPasswordRequest);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    ForgotPasswordResponse forgotPasswordResponse = response.body();
                    if(forgotPasswordResponse.getMessage().getSuccess() != null) {
                        if (forgotPasswordResponse.getMessage().getSuccess().toString().equalsIgnoreCase("An email with new password is sent to your registered email.")) {

                            AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Forgot Password", "An email with new password is sent to your registered email.");
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LoginActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
            }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /** Helping Method Section */
    public final static boolean isValidEmail(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}
