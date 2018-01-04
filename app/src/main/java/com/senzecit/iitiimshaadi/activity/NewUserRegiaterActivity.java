package com.senzecit.iitiimshaadi.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.senzecit.iitiimshaadi.api_integration.APIClient;
import com.senzecit.iitiimshaadi.api_integration.APIInterface;
import com.senzecit.iitiimshaadi.customdialog.CustomListAdapterDialog;
import com.senzecit.iitiimshaadi.customdialog.Model;
import com.senzecit.iitiimshaadi.model.api_response_model.new_register.NewRegistrationResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.NewRegistrationRequest;
import com.senzecit.iitiimshaadi.model.commons.CountryCodeModel;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewUserRegiaterActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView mTitle, mProfileCreatedForTV, mGenderTV, mDateOfBirthTV;
    ImageView mTermCheck,mTermUnCheck, mRefreshIV;
    Button mUserRegister, mCaptchaBtn;
    RelativeLayout mProfileCreatedRL, mGenderRL, mDOBRL;
    EditText mUserNameET, mEmailET, mFullNameET, mPasswordET, mRePasswordET, mMobileET, mVerifyCaptchaET;
    Spinner mCountryCodeSPN;
    int mDay, mMonth, mYear;

    /** NETWORK */
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_user_regiater);

        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        init();

        mProfileCreatedRL.setOnClickListener(this) ;
        mGenderRL.setOnClickListener(this);
        mDOBRL.setOnClickListener(this) ;

        mTermCheck.setOnClickListener(this);
        mTermUnCheck.setOnClickListener(this);
        mUserRegister.setOnClickListener(this);
        showCountryCode();
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
                Navigator.getClassInstance().navigateToActivity(this, SubscriberDashboardActivity.class);
//                startActivity(new Intent(NewUserRegiaterActivity.this,SubscriberDashboardActivity.class));
//                checkNewUserValidation();
                break;
            case R.id.idProfileCreatedFor:
                showProfileCreatedFor();
                break;
            case R.id.idGenderRL:
                showGender();
                break;
            case R.id.idDobRL:
                showDateOfBirth();
                break;
            case R.id.idRefreshIV:
                Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
                break;

        }
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
               mDateOfBirthTV.setText(day+"/"+month+"/"+year);
           }
       }, mYear, mMonth, mDay);
       datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.updateDate(mYear, mMonth, mDay);
       datePickerDialog.show();

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
//		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ((Button) view.findViewById(R.id.button_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final CustomListAdapterDialog clad1 = new CustomListAdapterDialog(NewUserRegiaterActivity.this, models);
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

//				showDialog(130,50);
            }
        });
    }

    /** Handle view to Set and Get*/
    public void showCountryCode(){

        List<CountryCodeModel> countryCodeList = new ArrayList<>();

        try {
            JSONObject jsonObject1 = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray1 = jsonObject1.getJSONArray("country");
            for(int i = 0; i<jsonArray1.length(); i++){
                JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                String name = jsonObject2.getString("name");
                String dial_code = jsonObject2.getString("dial_code");
                String code = jsonObject2.getString("code");

                CountryCodeModel countryCodeModel = new CountryCodeModel(name, dial_code, code);
                countryCodeList.add(countryCodeModel);

            }

//            CountrySpinnerAdapter countrySpinnerAdapter = new CountrySpinnerAdapter(this, R.layout.layout_country_code, countryCodeList);
//            mCountryCodeSPN.setAdapter(countrySpinnerAdapter);

            CustomArrayAdapter myAdapter = new CustomArrayAdapter(this, R.layout.layout_country_code, countryCodeList);
            myAdapter.setDropDownViewResource(R.layout.layout_country_code);
            mCountryCodeSPN.setAdapter(myAdapter);

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
                                if(!sDateOfBirth.startsWith("Select")){
                                    if(isValidMobile(sMobile)){
                                        if(serverSideCaptcha.equals(userEnterCaptcha)){
                                            if(mTermCheck.getVisibility() == View.VISIBLE){

                                                /** CALL API */

                                                new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "New User Validation Successfull");

                                            }else {
                                                new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "Please accept Terms & Conditions");
                                            }
                                        }else {
                                            mVerifyCaptchaET.requestFocus();
                                            mVerifyCaptchaET.setText("");
                                            new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "Captcha didn't match");
                                        }
                                    }else {
                                        mMobileET.requestFocus();
                                        new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "Mobile no. not valid");
                                    }
                                }else {
                                    new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "Date of Birth not selected");
                                }
                            }else {
                                new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "Gender not selected");
                            }
                    }else {
                            mFullNameET.requestFocus();
                    new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "FullName Can't Empty");
                }
                    }else {
                    new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "Profile Created For not selected");
                }
                }
            }else {
                mEmailET.requestFocus();
                new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "Email not valid");
            }
        }else {
            mUserNameET.requestFocus();
            new AlertDialogSingleClick().showDialog(NewUserRegiaterActivity.this, "Alert!", "Username Can't Empty");
        }

    }

    /** Check API Section */
    public void callWebServiceForNewRegistration() {

        String sUsername = mUserNameET.getText().toString().trim();
        String sEmail = mEmailET.getText().toString().trim();
        String sPassword = mPasswordET.getText().toString().trim();
        String sProfile = mProfileCreatedForTV.getText().toString().trim();
        String sFullName = mFullNameET.getText().toString().trim();
        String sGender = mGenderTV.getText().toString().trim();
        String sDateOfBirth =  mDateOfBirthTV.getText().toString().trim();
        String sMobile = mMobileET.getText().toString().trim();

        NewRegistrationRequest newRegistrationRequest = new NewRegistrationRequest();
        newRegistrationRequest.username = sUsername;
        newRegistrationRequest.email = sEmail;
        newRegistrationRequest.password = sPassword;
        newRegistrationRequest.profileCreatedFor = sProfile;
        newRegistrationRequest.fullName = sFullName;
        newRegistrationRequest.gender = sGender;
        newRegistrationRequest.dateOfBirth = sDateOfBirth;
        newRegistrationRequest.mobile = sMobile;

        Call<NewRegistrationResponse> call = apiInterface.newUserRegistration(newRegistrationRequest);
        call.enqueue(new Callback<NewRegistrationResponse>() {
            @Override
            public void onResponse(Call<NewRegistrationResponse> call, Response<NewRegistrationResponse> response) {
                if (response.isSuccessful()) {
                    /*if (response.body().getResponseCode() == 200) {
                        Toast.makeText(this, "Succesfully", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }

            @Override
            public void onFailure(Call<NewRegistrationResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(NewUserRegiaterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    public boolean isValidPassword(String password, String confirmPassword){
        boolean status = false;

        if(!password.isEmpty()){
            if(!confirmPassword.isEmpty()){
                if(password.equals(confirmPassword)){
//                    new AlertDialogSingleClick().showDialog(this, "Alert!", "Password Succesfull");
                status = true;
                }
                else {
                    status = false;
                    mPasswordET.requestFocus();
                    new AlertDialogSingleClick().showDialog(this, "Alert!", "Password didn't match");
                }
            }else {
                mRePasswordET.requestFocus();
                status = false;
                new AlertDialogSingleClick().showDialog(this, "Alert!", "Re-Type Password can't empty");
            }
        }else {
            mPasswordET.setText("");
            mRePasswordET.setText("");
            status = false;
            new AlertDialogSingleClick().showDialog(this, "Alert!", "Password can't empty");
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

}
