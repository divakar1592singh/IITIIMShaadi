package com.senzecit.iitiimshaadi.viewController;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.model.commons.PreAuthWebRequest;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.Validation;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RxNetworkingForObjectClass.CompletionHandler  {

    private static final String TAG = "LoginActivity";
    RxNetworkingForObjectClass rxNetworkingClass;
    EditText mPassword, mUsername;
    TextView mRegisterNew, mForgotPassword;
    Button mLogin;
    Toolbar mToolbar;
    TextView mTitle;
    String sdUsername = "";
    AppPrefs prefs;
    AlertDialog dialog = null;
    PreAuthWebRequest request;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = new AppPrefs(LoginActivity.this);
        request = new PreAuthWebRequest();

        rxNetworkingClass = RxNetworkingForObjectClass.getInstance();
        rxNetworkingClass.setCompletionHandler(this);

        init();
        handleView();

    }

    private void init() {
        mPassword = findViewById(R.id.passwordET);
        mUsername = findViewById(R.id.userNameET);
        mRegisterNew = findViewById(R.id.registerTV);
        mForgotPassword = findViewById(R.id.forgotpasswordTV);
        mLogin = findViewById(R.id.loginBtn);

        mToolbar = findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mTitle.setText("Login");
    }

    public void handleView() {
        mForgotPassword.setOnClickListener(this);
        mRegisterNew.setOnClickListener(this);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkClass.getInstance().checkInternet(LoginActivity.this) == true){
                    checkUserValidation();
                }else {
                    NetworkDialogHelper.getInstance().showDialog(LoginActivity.this);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgotpasswordTV:
                alertDialog();
                break;
            case R.id.registerTV:
                Navigator.getClassInstance().navigateToActivity(this, QuickRegistrationActivity.class);
                break;
        }
    }


    /**
     * VALIDATION SECTION
     */
    public void checkUserValidation() {

        String sUsername = mUsername.getText().toString().trim();
        String sPassword = mPassword.getText().toString().trim();
        if (Validation.handler().isNotEmptyString(LoginActivity.this, sUsername, AppMessage.USERNAME_EMPTY)) {
            if (Validation.handler().isNotEmptyString(LoginActivity.this, sPassword, AppMessage.PASSWORD_EMPTY)) {
                request.username = sUsername;
                request.password = sPassword;
                RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(LoginActivity.this, CONSTANTS.LOGIN_PART_URL, request, CONSTANTS.METHOD_1);
            }
        }
    }

    /**
     * Forgot Password
     */
    private void alertDialog() {

        final Button mConfirm, mLogin;
        EditText mUsernameOfForgotET;

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialog = dialogBuilder.create();
        final View dialogView = inflater.inflate(R.layout.forgot_password_layout, null);

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
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Email/Username is : " + sdUsername);
                if (!sdUsername.isEmpty()) {
                    request.username = sdUsername;
                        RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(LoginActivity.this, CONSTANTS.FORGOT_PASSWORD, request, CONSTANTS.METHOD_2);
                    } else {
                        AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Alert!", AppMessage.USERNAME_EMPTY);
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
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void handle(JSONObject object, String methodName) {

        System.out.println(object);
        try {

            if(methodName.equalsIgnoreCase(CONSTANTS.METHOD_1)) {
                if(object.getJSONObject("message").getInt("response_code") == 200) {
                    String sUsername = mUsername.getText().toString().trim();
                    String sPassword = mPassword.getText().toString().trim();

                    String token = object.getJSONObject("responseData").getString("token");
                    String userName = object.getJSONObject("responseData").getString("username");
                    String userId = String.valueOf(object.getJSONObject("responseData").getString("userid"));
                    String typeOfUser = object.getJSONObject("responseData").getString("type_of_user");
                    String email = object.getJSONObject("responseData").getString("email");
                    String mobile = object.getJSONObject("responseData").getString("mobile_no");
                    String profilePic = object.getJSONObject("responseData").getString("profile_image");
                    String gender = object.getJSONObject("responseData").getString("gender");

                    prefs.putString(CONSTANTPREF.LOGIN_USERNAME, sUsername);
                    prefs.putString(CONSTANTPREF.LOGIN_PASSWORD, sPassword);

                    prefs.putString(CONSTANTS.LOGGED_TOKEN, token);
                    prefs.putString(CONSTANTS.LOGGED_USERNAME, userName);
                    prefs.putString(CONSTANTS.LOGGED_USERID, userId);
                    prefs.putString(CONSTANTS.LOGGED_USER_TYPE, typeOfUser);
                    prefs.putString(CONSTANTS.LOGGED_EMAIL, email);
                    prefs.putString(CONSTANTS.LOGGED_MOB, mobile);
                    prefs.putString(CONSTANTS.LOGGED_USER_PIC, profilePic);
                    prefs.putString(CONSTANTS.GENDER_TYPE, gender);

                    if (typeOfUser.equalsIgnoreCase("paid_subscriber_viewer")) {
                        Navigator.getClassInstance().navigateToActivity(LoginActivity.this, PaidSubscriberDashboardActivity.class);
                    } else if (typeOfUser.equalsIgnoreCase("subscriber_viewer")) {
                        Navigator.getClassInstance().navigateToActivity(LoginActivity.this, SubscriberDashboardActivity.class);
                    } else if (typeOfUser.equalsIgnoreCase("subscriber")) {
                        Navigator.getClassInstance().navigateToActivity(LoginActivity.this, SubscriberDashboardActivity.class);
                    }
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, AppMessage.ALERT, AppMessage.USERPWD_INVALID);
                }
            }else if(methodName.equalsIgnoreCase(CONSTANTS.METHOD_2)){

                if(object.getJSONObject("message").getInt("response_code") == 200) {
                    AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, AppMessage.INFO, "An email with new password is sent to your registered email.");
                    dialog.dismiss();
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, AppMessage.ALERT, AppMessage.USER_VALID);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceError(ANError error, String methodName) {

    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, IntroSliderWebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
