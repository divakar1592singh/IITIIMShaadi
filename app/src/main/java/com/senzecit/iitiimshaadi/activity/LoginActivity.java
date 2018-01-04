package com.senzecit.iitiimshaadi.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.signin.internal.SignInRequest;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api_integration.APIClient;
import com.senzecit.iitiimshaadi.api_integration.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.forgot_password.ForgotPasswordResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.LoginResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.ForgotPasswordRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.LoginRequest;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mPassword,mUsername;
    TextView mRegisterNew,mForgotPassword;
    Button mLogin;
    Toolbar mToolbar;
    TextView mTitle;
    String sdUsername = "";

    /** Network*/
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);

        init();
        mForgotPassword.setOnClickListener(this);
        mRegisterNew.setOnClickListener(this);
        mLogin.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgotpasswordTV:
                alertDialog();
                break;
            case R.id.registerTV:
                Navigator.getClassInstance().navigateToActivity(this, RegistrationQuickActivity.class);
//                startActivity(new Intent(LoginActivity.this,RegistrationQuickActivity.class));
                break;
            case R.id.loginBtn:
                Navigator.getClassInstance().navigateToActivity(this, PaidSubscriberDashboardActivity.class);
//                startActivity(new Intent(LoginActivity.this,PaidSubscriberDashboardActivity.class));
//                checkUserValidation();
                break;
        }
    }

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
                        new AlertDialogSingleClick().showDialog(LoginActivity.this, "Alert!", "Email validation succesfull");
                    }else {
                        new AlertDialogSingleClick().showDialog(LoginActivity.this, "Alert!", "Email not valid");
                    }
                }else {
                new AlertDialogSingleClick().showDialog(LoginActivity.this, "Alert!", "Email can't Empty");
            }
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.show();
    }

    /** VALIDATION SECTION */
    public void checkUserValidation(){

        String sUsername = mUsername.getText().toString().trim();
        String sPassword = mPassword.getText().toString().trim();

        mPassword.getText().toString().trim();
        if(!sUsername.isEmpty()){
            if(!sPassword.isEmpty()){

                new AlertDialogSingleClick().showDialog(LoginActivity.this, "Alert!", "Login validation passed");
            }else {
                new AlertDialogSingleClick().showDialog(LoginActivity.this, "Alert!", "Password can't Empty");
            }
        }else {
        new AlertDialogSingleClick().showDialog(LoginActivity.this, "Alert!", "Username/Email can't Empty");
        }


    }

    /** Check API Section */
    public void callWebServiceForSignin(){

        String sUsername = mUsername.getText().toString().trim();
        String sPassword = mPassword.getText().toString().trim();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = sUsername;
        loginRequest.password = sPassword;

        Call<LoginResponse> call = apiInterface.loginInUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                   /* if (response.body().getResponseCode() == 200) {

                    Toast.makeText(RegistrationQuickActivity.this, "Comment sended succesfully", Toast.LENGTH_SHORT).show();

                }*/
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void callWebServiceForForgotPassword(String username){

        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.username = username;

        Call<ForgotPasswordResponse> call = apiInterface.forgotPasswordOfUser(forgotPasswordRequest);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful()) {
                   /* if (response.body().getResponseCode() == 200) {

                    Toast.makeText(RegistrationQuickActivity.this, "Comment sended succesfully", Toast.LENGTH_SHORT).show();

                }*/
            }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                call.cancel();
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
