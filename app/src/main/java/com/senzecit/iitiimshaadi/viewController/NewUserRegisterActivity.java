package com.senzecit.iitiimshaadi.viewController;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.CustomArrayAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.customdialog.CustomListAdapterDialog;
import com.senzecit.iitiimshaadi.customdialog.Model;
import com.senzecit.iitiimshaadi.model.api_response_model.new_register.NewRegistrationResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.NewRegistrationRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.UserValidationRequest;
import com.senzecit.iitiimshaadi.model.commons.CountryCodeModel;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CaptchaClass;
import com.senzecit.iitiimshaadi.utils.ModalBottomSheet;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewUserRegisterActivity extends AppCompatActivity implements View.OnClickListener, RxNetworkingForObjectClass.CompletionHandler  {

    Toolbar mToolbar;
    TextView mTitle, mProfileCreatedForTV, mGenderTV, mDateOfBirthTV, mTermsCoondition;
    TextView mUserNameStatus, mEmailStatus;
    ImageView mTermCheck,mTermUnCheck, mRefreshIV;
    Button mUserRegister, mCaptchaBtn;
    RelativeLayout mProfileCreatedRL, mGenderRL, mDOBRL;
    EditText mUserNameET, mEmailET, mFullNameET, mPasswordET, mRePasswordET, mMobileET, mVerifyCaptchaET;
    Spinner mCountryCodeSPN;
    Spinner mDaySPN, mMonthSPN, mYearSPN;
    int mDay, mMonth, mYear;
    RxNetworkingForObjectClass rxNetworkingClass;
    AppPrefs prefs;

    /** NETWORK */
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_user_regiater);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = new AppPrefs(NewUserRegisterActivity.this);

        rxNetworkingClass = RxNetworkingForObjectClass.getInstance();
        rxNetworkingClass.setCompletionHandler(this);

        init();
        handleview();

//        reTryMethod();
    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText("New User Register");

        mProfileCreatedRL = (RelativeLayout)findViewById(R.id.idProfileCreatedFor) ;
        mGenderRL = (RelativeLayout)findViewById(R.id.idGenderRL) ;
        mDOBRL = (RelativeLayout)findViewById(R.id.idDobRL) ;

        mProfileCreatedForTV = (TextView) findViewById(R.id.profileCreateTV);
        mGenderTV = (TextView) findViewById(R.id.genderTV);
        mDateOfBirthTV = (TextView) findViewById(R.id.dateOfBirthTV);

        mTermCheck = (ImageView) findViewById(R.id.termCheckIV);
        mTermUnCheck = (ImageView) findViewById(R.id.termUnCheckIV);
        mUserRegister = (Button) findViewById(R.id.userRegisterBtn);

        mUserNameET = (EditText) findViewById(R.id.userNameET);
        mEmailET = (EditText) findViewById(R.id.emailAddressET);
        mFullNameET = (EditText) findViewById(R.id.fullNameET);
        mPasswordET = (EditText) findViewById(R.id.passwordET);
        mRePasswordET = (EditText) findViewById(R.id.re_passwordET);
        mCountryCodeSPN = (Spinner)findViewById(R.id.idCountryCodeSPN);
        mMobileET = (EditText) findViewById(R.id.mobileNoET);
        mVerifyCaptchaET = (EditText) findViewById(R.id.verifyCaptchaET);
        mCaptchaBtn = (Button)findViewById(R.id.captchaBtn);
        mRefreshIV = (ImageView) findViewById(R.id.idRefreshIV);
        mTermsCoondition = (TextView)findViewById(R.id.idTermsCondition) ;

        mDaySPN = (Spinner)findViewById(R.id.idDaySpnr) ;
        mMonthSPN = (Spinner)findViewById(R.id.idMonthSpnr) ;
        mYearSPN = (Spinner)findViewById(R.id.idYearSpnr) ;

        mUserNameStatus = (TextView)findViewById(R.id.idUsernameStatus);
        mEmailStatus = (TextView)findViewById(R.id.idEmailStatus);


    }

    public void handleview(){

        mProfileCreatedRL.setOnClickListener(this) ;
        mGenderRL.setOnClickListener(this);
        mDOBRL.setOnClickListener(this) ;

        mTermCheck.setOnClickListener(this);
        mTermUnCheck.setOnClickListener(this);
        mUserRegister.setOnClickListener(this);
        mRefreshIV.setOnClickListener(this);
        mTermsCoondition.setOnClickListener(this::funcTermCondition);
        showCountryCode();
        showCaptcha();
        showValueInSpinner();
        textEventHandler();

    }

    public void textEventHandler(){

        mUserNameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    UserValidationRequest validationRequest = new UserValidationRequest();
                    validationRequest.str = mUserNameET.getText().toString().trim();
                    validationRequest.type = 1;

                    if(!TextUtils.isEmpty(mUserNameET.getText().toString().trim()))
                    RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(NewUserRegisterActivity.this, CONSTANTS.CHECK_DUPLICATE, validationRequest, "Username");

                }
            }
        });
        mEmailET.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    UserValidationRequest validationRequest = new UserValidationRequest();
                    validationRequest.str = mEmailET.getText().toString().trim();
                    validationRequest.type = 2;

                    if (!TextUtils.isEmpty(mEmailET.getText().toString().trim())){
                        if (isValidEmail(mEmailET.getText().toString().trim())) {
                            RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(NewUserRegisterActivity.this, CONSTANTS.CHECK_DUPLICATE, validationRequest, "Email");
                        }else {
                            mEmailStatus.setVisibility(View.VISIBLE);
                            mEmailStatus.setText("Email Not Valid");
                            mEmailStatus.setBackgroundColor(getResources().getColor(R.color.colorRed));

                        }
                    }

                }
            }
        });

        mUserNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(mUserNameET.getText().toString().trim())){
                    mUserNameStatus.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(mEmailET.getText().toString().trim())){
                    mEmailStatus.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.termCheckIV:
                mTermCheck.setVisibility(View.INVISIBLE);
                mTermUnCheck.setVisibility(View.VISIBLE);
                break;
            case R.id.termUnCheckIV:
                mTermCheck.setVisibility(View.VISIBLE);
                mTermUnCheck.setVisibility(View.INVISIBLE);
                break;
            case R.id.userRegisterBtn:
//                Navigator.getClassInstance().navigateToActivity(this, SubscriberDashboardActivity.class);
//                startActivity(new Intent(NewUserRegisterActivity.this,SubscriberDashboardActivity.class));

//                validateDateOfBirth();
//                callWebServiceForNewRegistration();
                checkNewUserValidation();

                break;
            case R.id.idProfileCreatedFor:
                showProfileCreatedFor();
                break;
            case R.id.idGenderRL:
                showGender();
                break;
            case R.id.idDobRL:
//                showDateOfBirth();
                break;
            case R.id.idRefreshIV:
//                Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
                showCaptcha();
                break;

        }
    }

    public void showCaptcha(){

        CaptchaClass captcha = new CaptchaClass();
        String str = captcha.generateCaptcha();
        System.out.println(str);

        mCaptchaBtn.setText(str);

    }

    public void showProfileCreatedFor(){
        List<String> list = new ArrayList<>();
        list.add("Myself");
        list.add("Son");
        list.add("Daughter");
        list.add("Brother");
        list.add("Sister");
        list.add("Friend");
        list.add("Others");

        showDialog(list, mProfileCreatedForTV);
    }

    public void showGender(){
        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");

        showDialog(list, mGenderTV);
    }

    public void showDateOfBirth(){

        Calendar cal1 = Calendar.getInstance();
        mYear = cal1.get(Calendar.YEAR);
        mMonth = cal1.get(Calendar.MONTH);
        mDay = cal1.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                System.out.println(year);
                mDateOfBirthTV.setText(day+"/"+(month+1)+"/"+year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.updateDate(mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public boolean validateDateOfBirth(){

        String sDay = mDaySPN.getSelectedItem().toString();
        int posMonth = mMonthSPN.getSelectedItemPosition();
//        String sMonth = monthList.get(posMonth);
        String sYear = mYearSPN.getSelectedItem().toString();

        boolean statusDay = !sDay.equalsIgnoreCase("Day")?true:false;
        boolean statusMonth = posMonth != 0?true:false;
        boolean statusYear = !sYear.equalsIgnoreCase("Year")?true:false;

        if(statusDay == true &&statusMonth == true && statusYear == true ){

            if(NetworkClass.getInstance().checkInternet(NewUserRegisterActivity.this) == true){
//                checkNewUserValidation();
                return true;
            }else {
//                NetworkDialogHelper.getInstance().showDialog(NewUserRegisterActivity.this);
                return false;
            }

        }else {
//            AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert", "Check 'Day/Month/Year' is selected!");
            return false;
        }


    }

    public Vector<Dialog> dialogs = new Vector<Dialog>();
    private void showDialog(List<String> dataList, final TextView textView) {
        int d_width = 100;
        int d_height = 50;
        final ArrayList<Model> models = new ArrayList<Model>();
        if(dataList.size()>0){
            for (int i = 0; i < dataList.size(); i++) {
                Model model = new Model();
                model.setName(dataList.get(i));
                models.add(model);
            }
        }else {
            for (int i = 0; i < 20; i++) {
                Model model = new Model();
                model.setName("senzecit " + i);
                models.add(model);
            }
        }

        final Dialog dialog = new Dialog(this, R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        View view = getLayoutInflater().inflate(R.layout.toast_layout, null);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.custom_list);
//    final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ((Button) view.findViewById(R.id.button_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final CustomListAdapterDialog clad1 = new CustomListAdapterDialog(NewUserRegisterActivity.this, models);
        recyclerView.setAdapter(clad1);

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialogs.add(dialog);
        dialog.show();

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Window window = dialog.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setLayout(width - d_width, height - d_height);

        clad1.setOnItemClickListener(new CustomListAdapterDialog.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String id) {
                int positionInList = position % models.size();
                textView.setText(models.get(positionInList).getName());
                dialog.dismiss();

//          showDialog(130,50);
            }
        });
    }

    /** Handle view to Set and Get*/
    public void showCountryCode(){

        int position = 0;
        List<CountryCodeModel> countryCodeList = new ArrayList<>();

        try {
            JSONObject jsonObject1 = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray1 = jsonObject1.getJSONArray("country");
            for(int i = 0; i<jsonArray1.length(); i++){
                JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                String name = jsonObject2.getString("name");
                String dial_code = jsonObject2.getString("dial_code");
                String code = jsonObject2.getString("code");

                if(dial_code.equalsIgnoreCase("+91")){
                    position = i;
                }

                CountryCodeModel countryCodeModel = new CountryCodeModel(name, dial_code, code);
                countryCodeList.add(countryCodeModel);

            }

//            CountrySpinnerAdapter countrySpinnerAdapter = new CountrySpinnerAdapter(this, R.layout.layout_country_code, countryCodeList);
//            mCountryCodeSPN.setAdapter(countrySpinnerAdapter);
            Collections.sort(countryCodeList, new Comparator<CountryCodeModel>() {
                @Override
                public int compare(CountryCodeModel lhs, CountryCodeModel rhs) {
                    return lhs.getDial_code().compareTo(rhs.getDial_code());
                }
            });

            CustomArrayAdapter myAdapter = new CustomArrayAdapter(this, R.layout.layout_country_code, countryCodeList);
            myAdapter.setDropDownViewResource(R.layout.layout_country_code);
            mCountryCodeSPN.setAdapter(myAdapter);

            for(int j = 0; j<countryCodeList.size(); j++){

                if(countryCodeList.get(j).getDial_code().equalsIgnoreCase("+91")){
                    position = j;
                }
            }
            mCountryCodeSPN.setSelection(position);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /** Check Validation Section */
    public void checkNewUserValidation(){

        String sUsername = mUserNameET.getText().toString().trim();
        String sEmail = mEmailET.getText().toString().trim();
        String password = mPasswordET.getText().toString().trim();
        String confirmPassword = mRePasswordET.getText().toString().trim();

        String sProfile = mProfileCreatedForTV.getText().toString().trim();
        String fullName = mFullNameET.getText().toString().trim();
        String sGender = mGenderTV.getText().toString().trim();
        String sDateOfBirth =  mDateOfBirthTV.getText().toString().trim();
        String sMobile = mMobileET.getText().toString().trim();

        String userEnterCaptcha = mVerifyCaptchaET.getText().toString().trim();
        String serverSideCaptcha = mCaptchaBtn.getText().toString().trim();
//        String sCollege = mCollegeNameET.getText().toString().trim();

        if(!sUsername.isEmpty()){
            if(isValidEmail(sEmail)){
                if(isValidPassword(password, confirmPassword) == true){
                    if(!sProfile.startsWith("Select")){
                        if(!fullName.isEmpty()){
                            if(!sGender.startsWith("Select")){
                                if(validateDateOfBirth() == true){
                                    if(isValidMobile(sMobile)){
                                        if(serverSideCaptcha.equals(userEnterCaptcha)){
                                            if(mTermCheck.getVisibility() == View.VISIBLE){

                                                /** CALL API */
                                                callWebServiceForNewRegistration();

                                            }else {
                                                AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert!", "Please accept Terms & Conditions");
                                            }
                                        }else {
                                            mVerifyCaptchaET.requestFocus();
                                            mVerifyCaptchaET.setText("");
                                            AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert!", "Captcha didn't match");
                                        }
                                    }else {
                                        mMobileET.requestFocus();
                                        AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert!", "Mobile No. not valid");
                                    }
                                }else {
                                    AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert!", "Check 'Day/Month/Year' is selected!");
                                }
                            }else {
                                AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert!", "Gender not selected");
                            }
                        }else {
                            mFullNameET.requestFocus();
                            AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert!", "FullName can't be empty");
                        }
                    }else {
                        AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert!", "Profile Created For not selected");
                    }
                }
            }else {
                mEmailET.requestFocus();
                AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert!", "Email not valid");
            }
        }else {
            mUserNameET.requestFocus();
            AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert!", "Username can't be Empty");
        }

    }

    /** Check API Section */
    public void callWebServiceForNewRegistration() {

        if(NetworkClass.getInstance().checkInternet(NewUserRegisterActivity.this) == true){

        String sUsername = mUserNameET.getText().toString().trim();
        String sEmail = mEmailET.getText().toString().trim();
        String sPassword = mPasswordET.getText().toString().trim();
        String sProfile = mProfileCreatedForTV.getText().toString().trim();
        String sFullName = mFullNameET.getText().toString().trim();
        String sGender = mGenderTV.getText().toString().trim();
//        String sDateOfBirth =  mDateOfBirthTV.getText().toString().trim();
        String sMobile = mMobileET.getText().toString().trim();

        String sDay = mDaySPN.getSelectedItem().toString();
        int posMonth = mMonthSPN.getSelectedItemPosition();
        String sYear = mYearSPN.getSelectedItem().toString();

        String sDateOfBirth = sYear+"-"+posMonth+"-"+sDay;
        NewRegistrationRequest newRegistrationRequest = new NewRegistrationRequest();
        newRegistrationRequest.username = sUsername;
        newRegistrationRequest.email = sEmail;
        newRegistrationRequest.password = sPassword;
        newRegistrationRequest.profile_created_for = sProfile;
        newRegistrationRequest.full_name = sFullName;
        newRegistrationRequest.gender = sGender;
        newRegistrationRequest.date_of_birth = sDateOfBirth;
        newRegistrationRequest.mob_no = sMobile;

        ProgressClass.getProgressInstance().showDialog(NewUserRegisterActivity.this);
        Call<NewRegistrationResponse> call = apiInterface.newUserRegistration(newRegistrationRequest);
        call.enqueue(new Callback<NewRegistrationResponse>() {
            @Override
            public void onResponse(Call<NewRegistrationResponse> call, Response<NewRegistrationResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();

                try {
                    if (response.isSuccessful()) {

                        if (response.body().getMessage().getSuccess().toString().equalsIgnoreCase("User is registered successfully !")) {
                            if (response.body().getResponseData() != null) {
                                Toast.makeText(NewUserRegisterActivity.this, "Registration Succesfull", Toast.LENGTH_SHORT).show();
                                com.senzecit.iitiimshaadi.model.api_response_model.new_register.ResponseData responseData = response.body().getResponseData();

                                setPrefData(responseData);
                            }
                        } else {
                            Toast.makeText(NewUserRegisterActivity.this, "Check Username/Email", Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (NullPointerException npe){
                    Log.e("TAG", "#Error : "+npe, npe);
                    ProgressClass.getProgressInstance().stopProgress();

                    String title = "Alert";
                    String msg = "Oops. Please Try Again! \nHelp : Try different Username or Email.\n";
                    AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, title, msg);
//                    reTryMethod();
                }
            }

            @Override
            public void onFailure(Call<NewRegistrationResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                reTryMethod();
//                Toast.makeText(NewUserRegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                AlertDialogSingleClick.getInstance().showDialog(NewUserRegisterActivity.this, "Alert", "Something went wrong! \n Try again!");
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(NewUserRegisterActivity.this);
        }


    }

    public void setPrefData(com.senzecit.iitiimshaadi.model.api_response_model.new_register.ResponseData response){
        String token = response.getToken();
        String userName = response.getUsername();
        String userId = String.valueOf(response.getUserid());
        String typeOfUser = response.getTypeOfUser();
        String email = response.getEmail();
        String mob = response.getMobile();


        prefs.putString(CONSTANTS.LOGGED_TOKEN, token);
        prefs.putString(CONSTANTS.LOGGED_USERNAME, userName);
        prefs.putString(CONSTANTS.LOGGED_USERID, userId);
        prefs.putString(CONSTANTS.LOGGED_USER_TYPE, typeOfUser);
        prefs.putString(CONSTANTS.LOGGED_EMAIL, email);
        prefs.putString(CONSTANTS.LOGGED_MOB, mob);

        prefs.getString(CONSTANTS.LOGGED_TOKEN);

        navigateUserToScreen(typeOfUser);
    }

    public void navigateUserToScreen(String typeOfUser){
//        if(typeOfUser.equalsIgnoreCase("subscriber")){
        Navigator.getClassInstance().navigateToActivity(NewUserRegisterActivity.this, SubscriberDashboardActivity.class);
//        }
    }

    /** Helping Method Section */
    public final static boolean isValidEmail(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    public boolean isValidPassword(String password, String confirmPassword){
        boolean status = false;

        if(!password.isEmpty()){
            if(!confirmPassword.isEmpty()){
                if(password.equals(confirmPassword)){
//                    AlertDialogSingleClick.getInstance().showDialog(this, "Alert!", "Password Succesfull");
                    status = true;
                }
                else {
                    status = false;
                    mPasswordET.requestFocus();
                    AlertDialogSingleClick.getInstance().showDialog(this, "Alert!", "Password didn't match");
                }
            }else {
                mRePasswordET.requestFocus();
                status = false;
                AlertDialogSingleClick.getInstance().showDialog(this, "Alert!", "Re-Type Password can't be empty");
            }
        }else {
            mPasswordET.setText("");
            mRePasswordET.setText("");
            status = false;
            AlertDialogSingleClick.getInstance().showDialog(this, "Alert!", "Password can't be empty");
        }

        return status;
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getResources().getAssets().open("country_code/DialingCode.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void showValueInSpinner(){

//DAY
        int day = 0;
        List<String> dayList = new ArrayList<String>();
        for (int i = 0; i<32; i++){
            if(i == 0){
                dayList.add("Day");
            }else {
                day = day + 1;
                dayList.add(String.valueOf(day));
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.row_spinner_layout, R.id.idSpnTV, dayList);
        mDaySPN.setAdapter(dataAdapter);

//MONTH
        List<String> monthList = new ArrayList<String>();
        /*for (int i = 0; i<13; i++) {
            if (i == 0) {
                monthList.add("Month");
            } else{
                monthList.add(String.valueOf(i + 1));
        }
        }*/
        monthList.add("Month");
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, R.layout.row_spinner_layout, R.id.idSpnTV, monthList);
        mMonthSPN.setAdapter(dataAdapter1);

//DAY
        int year = 1956;
        List<String> yearList = new ArrayList<String>();
        for (int i = 0; i<43; i++){
            if (i == 0) {
                yearList.add("Year");
            } else {
                year = year + 1;
                yearList.add(String.valueOf(year));
            }
        }
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, R.layout.row_spinner_layout, R.id.idSpnTV, yearList);
        mYearSPN.setAdapter(dataAdapter2);

    }
    public void funcTermCondition(View view){

        ModalBottomSheet modalBottomSheet = new ModalBottomSheet();
        modalBottomSheet.show(getSupportFragmentManager(), "Terms and Conditions");

    }

    public void reTryMethod(){

        String title = "Alert";
        String msg = "Oops. Please Try Again! \nHelp : Try different Username or Email.\n";

        final Dialog dialog = new Dialog(NewUserRegisterActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_two_click);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTxt = (TextView) dialog.findViewById(R.id.txt_file_path);
        titleTxt.setText(title);
        TextView msgTxt = (TextView) dialog.findViewById(R.id.idMsg);
        msgTxt.setText(msg);

        Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
//                dialog.cancel();
                callWebServiceForNewRegistration();
                dialog.dismiss();
            }
        });

        dialog.show();
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

        Intent intent = new Intent(NewUserRegisterActivity.this, QuickRegistrationActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public void handle(JSONObject object, String methodName) {

        if(methodName.equalsIgnoreCase("Username")){

            System.out.println(object);
            try {
                int counter = object.getInt("counter");
                boolean status = counter == 1 ? true : false;
                if(status == true){
                    mUserNameStatus.setVisibility(View.VISIBLE);
                    mUserNameStatus.setText("Username Not Available");
                    mUserNameStatus.setBackgroundColor(getResources().getColor(R.color.colorRed));

                }else if( status == false) {
                    mUserNameStatus.setVisibility(View.VISIBLE);
                    mUserNameStatus.setText("Username Available");
                    mUserNameStatus.setBackgroundColor(getResources().getColor(R.color.colorGreen));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(methodName.equalsIgnoreCase("Email")){

            System.out.println(object);
            try {
                int counter = object.getInt("counter");
                boolean status = counter == 1 ? true : false;
                if(status == true){
                    mEmailStatus.setVisibility(View.VISIBLE);
                    mEmailStatus.setText("Email Not Available");
                    mEmailStatus.setBackgroundColor(getResources().getColor(R.color.colorRed));

                }else if( status == false) {
                    mEmailStatus.setVisibility(View.VISIBLE);
                    mEmailStatus.setText("Email Available");
                    mEmailStatus.setBackgroundColor(getResources().getColor(R.color.colorGreen));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

