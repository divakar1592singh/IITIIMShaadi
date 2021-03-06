package com.senzecit.iitiimshaadi.viewController;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

import com.androidnetworking.error.ANError;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.CustomArrayAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.customdialog.CustomListAdapterDialog;
import com.senzecit.iitiimshaadi.customdialog.Model;
import com.senzecit.iitiimshaadi.model.commons.PreAuthWebRequest;
import com.senzecit.iitiimshaadi.model.commons.CountryCodeModel;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CaptchaClass;
import com.senzecit.iitiimshaadi.utils.ModalBottomSheet;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ToastDialogMessage;
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
    PreAuthWebRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_user_regiater);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = new AppPrefs(NewUserRegisterActivity.this);

        rxNetworkingClass = RxNetworkingForObjectClass.getInstance();
        rxNetworkingClass.setCompletionHandler(this);
        request = new PreAuthWebRequest();

        init();
        handleview();

    }

    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mTitle.setText("New User Register");

        mProfileCreatedRL = findViewById(R.id.idProfileCreatedFor);
        mGenderRL = findViewById(R.id.idGenderRL);
        mDOBRL = findViewById(R.id.idDobRL);

        mProfileCreatedForTV = findViewById(R.id.profileCreateTV);
        mGenderTV = findViewById(R.id.genderTV);
        mDateOfBirthTV = findViewById(R.id.dateOfBirthTV);

        mTermCheck = findViewById(R.id.termCheckIV);
        mTermUnCheck = findViewById(R.id.termUnCheckIV);
        mUserRegister = findViewById(R.id.userRegisterBtn);

        mUserNameET = findViewById(R.id.userNameET);
        mEmailET = findViewById(R.id.emailAddressET);
        mFullNameET = findViewById(R.id.fullNameET);
        mPasswordET = findViewById(R.id.passwordET);
        mRePasswordET = findViewById(R.id.re_passwordET);
        mCountryCodeSPN = findViewById(R.id.idCountryCodeSPN);
        mMobileET = findViewById(R.id.mobileNoET);
        mVerifyCaptchaET = findViewById(R.id.verifyCaptchaET);
        mCaptchaBtn = findViewById(R.id.captchaBtn);
        mRefreshIV = findViewById(R.id.idRefreshIV);
        mTermsCoondition = findViewById(R.id.idTermsCondition);

        mDaySPN = findViewById(R.id.idDaySpnr);
        mMonthSPN = findViewById(R.id.idMonthSpnr);
        mYearSPN = findViewById(R.id.idYearSpnr);

        mUserNameStatus = findViewById(R.id.idUsernameStatus);
        mEmailStatus = findViewById(R.id.idEmailStatus);


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

                    request.str = mUserNameET.getText().toString().trim();
                    request.type = 1;

                    if(!TextUtils.isEmpty(mUserNameET.getText().toString().trim()))
                    RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(NewUserRegisterActivity.this, CONSTANTS.CHECK_DUPLICATE, request, CONSTANTS.METHOD_1);

                }
            }
        });
        mEmailET.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    request.str = mEmailET.getText().toString().trim();
                    request.type = 2;

                    if (!TextUtils.isEmpty(mEmailET.getText().toString().trim())){
                        if (isValidEmail(mEmailET.getText().toString().trim())) {
                            RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(NewUserRegisterActivity.this, CONSTANTS.CHECK_DUPLICATE, request, CONSTANTS.METHOD_2);
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

        boolean statusDay = !sDay.equalsIgnoreCase("Day");
        boolean statusMonth = posMonth != 0;
        boolean statusYear = !sYear.equalsIgnoreCase("Year");

        if(statusDay == true &&statusMonth == true && statusYear == true ){

            //                checkNewUserValidation();
//                NetworkDialogHelper.getInstance().showDialog(NewUserRegisterActivity.this);
            return NetworkClass.getInstance().checkInternet(NewUserRegisterActivity.this) == true;

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
        final RecyclerView recyclerView = view.findViewById(R.id.custom_list);
//    final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        view.findViewById(R.id.button_done).setOnClickListener(new View.OnClickListener() {
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

        String sDay = mDaySPN.getSelectedItem().toString();
        int posMonth = mMonthSPN.getSelectedItemPosition();
        String sYear = mYearSPN.getSelectedItem().toString();
        String sDateOfBirth = sYear+"-"+posMonth+"-"+sDay;

        String sMobile = mMobileET.getText().toString().trim();
        String userEnterCaptcha = mVerifyCaptchaET.getText().toString().trim();
        String serverSideCaptcha = mCaptchaBtn.getText().toString().trim();

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

                                                request.username = sUsername;
                                                request.email = sEmail;
                                                request.password = confirmPassword;
                                                request.profile_created_for = sProfile;
                                                request.full_name = fullName;
                                                request.gender = sGender;
                                                request.date_of_birth = sDateOfBirth;
                                                request.mob_no = sMobile;

                                                RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(NewUserRegisterActivity.this, CONSTANTS.USER_REG, request, CONSTANTS.METHOD_3);


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

        TextView titleTxt = dialog.findViewById(R.id.txt_file_path);
        titleTxt.setText(title);
        TextView msgTxt = dialog.findViewById(R.id.idMsg);
        msgTxt.setText(msg);

        Button dialogBtn_cancel = dialog.findViewById(R.id.btn_cancel);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
//                dialog.cancel();
                checkNewUserValidation();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void handle(JSONObject object, String methodName) {

        System.out.println(object);
        try {
            if (methodName.equalsIgnoreCase(CONSTANTS.METHOD_1)) {

                System.out.println(object);
                int counter = object.getInt("counter");
                boolean status = counter == 1;
                if (status == true) {
                    mUserNameStatus.setVisibility(View.VISIBLE);
                    mUserNameStatus.setText("Username Not Available");
                    mUserNameStatus.setBackgroundColor(getResources().getColor(R.color.colorRed));

                } else if (status == false) {
                    mUserNameStatus.setVisibility(View.VISIBLE);
                    mUserNameStatus.setText("Username Available");
                    mUserNameStatus.setBackgroundColor(getResources().getColor(R.color.colorGreen));

                }

            } else if (methodName.equalsIgnoreCase(CONSTANTS.METHOD_2)) {

                int counter = object.getInt("counter");
                boolean status = counter == 1;
                if (status == true) {
                    mEmailStatus.setVisibility(View.VISIBLE);
                    mEmailStatus.setText("Email Not Available");
                    mEmailStatus.setBackgroundColor(getResources().getColor(R.color.colorRed));

                } else if (status == false) {
                    mEmailStatus.setVisibility(View.VISIBLE);
                    mEmailStatus.setText("Email Available");
                    mEmailStatus.setBackgroundColor(getResources().getColor(R.color.colorGreen));

                }

            } else if (methodName.equalsIgnoreCase(CONSTANTS.METHOD_3)) {
                System.out.println(object);
                if (object.getJSONObject("message").getInt("response_code") == 200) {
                    ToastDialogMessage.getInstance().showToast(NewUserRegisterActivity.this, "User is registered successfully !");

                    String sUsername = mUserNameET.getText().toString().trim();
                    String sPassword = mRePasswordET.getText().toString().trim();

                    String token = object.getJSONObject("responseData").getString("token");
                    String userName = object.getJSONObject("responseData").getString("username");
                    String userId = String.valueOf(object.getJSONObject("responseData").getString("userid"));
                    String typeOfUser = object.getJSONObject("responseData").getString("type_of_user");
                    String email = object.getJSONObject("responseData").getString("email");
                    String mobile = object.getJSONObject("responseData").getString("mobile_no");
                    String gender = object.getJSONObject("responseData").getString("gender");

                    prefs.putString(CONSTANTPREF.LOGIN_USERNAME, sUsername);
                    prefs.putString(CONSTANTPREF.LOGIN_PASSWORD, sPassword);

                    prefs.putString(CONSTANTS.LOGGED_TOKEN, token);
                    prefs.putString(CONSTANTS.LOGGED_USERNAME, userName);
                    prefs.putString(CONSTANTS.LOGGED_USERID, userId);
                    prefs.putString(CONSTANTS.LOGGED_USER_TYPE, typeOfUser);
                    prefs.putString(CONSTANTS.LOGGED_EMAIL, email);
                    prefs.putString(CONSTANTS.LOGGED_MOB, mobile);
                    prefs.putString(CONSTANTS.GENDER_TYPE, gender);

                    Navigator.getClassInstance().navigateToActivity(NewUserRegisterActivity.this, SubscriberDashboardActivity.class);
                    finishActivity(0);
                } else {
                    reTryMethod();
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

        Intent intent = new Intent(NewUserRegisterActivity.this, QuickRegistrationActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }



}

