package com.senzecit.iitiimshaadi.activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api_integration.APIClient;
import com.senzecit.iitiimshaadi.api_integration.APIInterface;
import com.senzecit.iitiimshaadi.customdialog.CustomListAdapterDialog;
import com.senzecit.iitiimshaadi.customdialog.Model;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.EligibilityResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.FindCollegeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_institution.QuickRegInstitutionResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegInstitutionRequest;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_stream.QuickRegStreamResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegEligibilityRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegFindCollegeRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegStreamRequest;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationQuickActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView mTitle;
    Button mbuttonContinue, mSubmitFindEduBtn;
    ImageView mBoySelect,mBoyUnSelect,mGirlSelect,mGirlUnSelect;
    TextView mEducationTV, mStreamTV, mInstitutionTV;
    RelativeLayout mEducationRL, mStreamRL, mInstitutionRL;
    EditText mUserNameET, mEmailET, mMobileET, mCollegeNameET;
    //Network
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration_quick);

        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);

        init();
        mbuttonContinue.setOnClickListener(this);
        mBoySelect.setOnClickListener(this);
        mBoyUnSelect.setOnClickListener(this);
        mGirlSelect.setOnClickListener(this);
        mGirlUnSelect.setOnClickListener(this);

        mEducationRL.setOnClickListener(this);
        mStreamRL.setOnClickListener(this);
        mInstitutionRL.setOnClickListener(this);
        mSubmitFindEduBtn.setOnClickListener(this);

    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText("Quick Register");

        mbuttonContinue = (Button) findViewById(R.id.continueBtn);

        mBoySelect = (ImageView) findViewById(R.id.boySelect);
        mBoyUnSelect = (ImageView) findViewById(R.id.boyUnSelect);
        mGirlSelect = (ImageView) findViewById(R.id.girlSelect);
        mGirlUnSelect = (ImageView) findViewById(R.id.girlUnSelect);

        mEducationTV = (TextView) findViewById(R.id.educationTV);
        mStreamTV = (TextView) findViewById(R.id.idStreamTV);
        mInstitutionTV = (TextView) findViewById(R.id.idInstitutionTV);

        mEducationRL = (RelativeLayout) findViewById(R.id.id_educationRL);
        mStreamRL = (RelativeLayout) findViewById(R.id.id_streamRL);
        mInstitutionRL = (RelativeLayout) findViewById(R.id.id_institutionRL);

        mUserNameET = (EditText) findViewById(R.id.userNameET);
        mEmailET = (EditText) findViewById(R.id.emailET);
        mMobileET = (EditText) findViewById(R.id.mobileET);
        mCollegeNameET = (EditText) findViewById(R.id.collegeNameET);
        mSubmitFindEduBtn = (Button)findViewById(R.id.idSubmitFindEduBtn);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.continueBtn:
                Navigator.getClassInstance().navigateToActivity(this, NewUserRegisterActivity.class);
//                startActivity(new Intent(this,NewUserRegisterActivity.class));
//                checkEligibilityValidation();
                break;
            case R.id.boyUnSelect:
                mBoySelect.setVisibility(View.VISIBLE);
                mBoyUnSelect.setVisibility(View.GONE);
                mGirlSelect.setVisibility(View.GONE);
                mGirlUnSelect.setVisibility(View.VISIBLE);
                getGender();
                break;
            case R.id.girlUnSelect:
                mGirlSelect.setVisibility(View.VISIBLE);
                mGirlUnSelect.setVisibility(View.GONE);
                mBoySelect.setVisibility(View.GONE);
                mBoyUnSelect.setVisibility(View.VISIBLE);
                getGender();
                break;
            case R.id.id_educationRL:
                showEducation();
                break;
            case R.id.id_streamRL:
                showStream();
                break;
            case R.id.id_institutionRL:
                showCountry();
                break;
            case R.id.idSubmitFindEduBtn:
//                Toast.makeText(this,"Submit", Toast.LENGTH_SHORT).show();
                checkFindEduValidation();
                break;

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
//		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ((Button) view.findViewById(R.id.button_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final CustomListAdapterDialog clad1 = new CustomListAdapterDialog(RegistrationQuickActivity.this, models);
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
    public String getGender(){
        if(mBoySelect.getVisibility() == View.VISIBLE){
            Toast.makeText(this, "Male", Toast.LENGTH_LONG).show();
        }else if(mGirlSelect.getVisibility() == View.VISIBLE){
            Toast.makeText(this, "Female", Toast.LENGTH_LONG).show();
        }
        return  null;
    }
    public void showEducation(){
        List<String> educationList = new ArrayList<>();
        educationList.add("Indian");
        educationList.add("InterNational");
        showDialog(educationList, mEducationTV);
    }
    public void showStream(){
        List<String> streamList = new ArrayList<>();
        streamList.add("BBA");
        streamList.add("BCA");
        streamList.add("MBA");
        streamList.add("MCA");

        showDialog(streamList, mStreamTV);
    }
    public void showCountry(){
        List<String> countryList = new ArrayList<>();
        countryList.add("India");
        countryList.add("Russia");
        countryList.add("America");
        countryList.add("China");
        showDialog(countryList, mInstitutionTV);
    }

    /** Check Validation Section */
    public void checkEligibilityValidation(){

        String sEducation = mEducationTV.getText().toString().trim();
        String sStream = mStreamTV.getText().toString().trim();
        String sInstitution = mInstitutionTV.getText().toString().trim();

        if(!sEducation.startsWith("Select") && !sStream.startsWith("Select") && !sInstitution.startsWith("Select")){
            AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "All selected");
//            startActivity(new Intent(RegistrationQuickActivity.this,NewUserRegisterActivity.class));
        }else {
            AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "Education/Stream/Institution are not selected");
        }


    }
    public void checkFindEduValidation(){

        String sUsername = mUserNameET.getText().toString().trim();
        String sEmail = mEmailET.getText().toString().trim();
        String sMobile = mMobileET.getText().toString().trim();
        String sCollege = mCollegeNameET.getText().toString().trim();

        if(mBoySelect.getVisibility() == View.VISIBLE || mGirlSelect.getVisibility() == View.VISIBLE){
        if(!sUsername.isEmpty()){
            if(isValidEmail(sEmail)){
                if(isValidMobile(sMobile)){
                        if(!sCollege.isEmpty()){

                            AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "Find College Validation Successfull");

                        }else {
                            mCollegeNameET.requestFocus();
                            AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "College name can't empty");
                        }
                    }else {
                        mMobileET.requestFocus();
                        AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "Mobile no. not valid");
                    }
                }else {
                    mEmailET.requestFocus();
                    AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "Email not valid");
                }
            }else {
                mUserNameET.requestFocus();
                AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "Username can't Empty");
            }
    }else {
        AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "Username can't Empty");
    }

    }

    /** Check API Section */
    public void callWebServiceForStream() {

        String sEducation = mEducationTV.getText().toString().trim();

        QuickRegStreamRequest quickRegStreamRequest = new QuickRegStreamRequest();
        quickRegStreamRequest.gender = getGender();
        quickRegStreamRequest.education = sEducation;

        Call<QuickRegStreamResponse> call = apiInterface.fetchStreamData(quickRegStreamRequest);
        call.enqueue(new Callback<QuickRegStreamResponse>() {
            @Override
            public void onResponse(Call<QuickRegStreamResponse> call, Response<QuickRegStreamResponse> response) {
                if (response.isSuccessful()) {
                   /* if (response.body().getResponseCode() == 200) {

                    Toast.makeText(RegistrationQuickActivity.this, "Comment sended succesfully", Toast.LENGTH_SHORT).show();

                }*/
            }
            }

            @Override
            public void onFailure(Call<QuickRegStreamResponse> call, Throwable t) {
            call.cancel();
            Toast.makeText(RegistrationQuickActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }
        });

    }
    public void callWebServiceForInstitution() {

        String sEducation = mEducationTV.getText().toString().trim();
        String sStream = mStreamTV.getText().toString().trim();

        QuickRegInstitutionRequest quickRegInstitutionRequest = new QuickRegInstitutionRequest();
        quickRegInstitutionRequest.gender = getGender();
        quickRegInstitutionRequest.education = sEducation;
        quickRegInstitutionRequest.stream = sStream;

        Call<QuickRegInstitutionResponse> call = apiInterface.fetchInstitutionData(quickRegInstitutionRequest);
       call.enqueue(new Callback<QuickRegInstitutionResponse>() {
           @Override
           public void onResponse(Call<QuickRegInstitutionResponse> call, Response<QuickRegInstitutionResponse> response) {
               if (response.isSuccessful()) {
                   /*if (response.body().getResponseCode() == 200) {
                       Toast.makeText(this, "Succesfully", Toast.LENGTH_SHORT).show();
                   }*/
               }
           }

           @Override
           public void onFailure(Call<QuickRegInstitutionResponse> call, Throwable t) {
               call.cancel();
               Toast.makeText(RegistrationQuickActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
           }
       });
    }

    public void callWebServiceForQuickRegister() {

        String sEducation = mEducationTV.getText().toString().trim();
        String sStream = mStreamTV.getText().toString().trim();
        String sInstitution = mInstitutionTV.getText().toString().trim();

        QuickRegEligibilityRequest quickRegEligibilityRequest = new QuickRegEligibilityRequest();
        quickRegEligibilityRequest.gender = getGender();
        quickRegEligibilityRequest.education = sEducation;
        quickRegEligibilityRequest.stream = sStream;
        quickRegEligibilityRequest.institution = sInstitution;

        Call<EligibilityResponse> call = apiInterface.quickRegisterUser(quickRegEligibilityRequest);
        call.enqueue(new Callback<EligibilityResponse>() {
            @Override
            public void onResponse(Call<EligibilityResponse> call, Response<EligibilityResponse> response) {
                if (response.isSuccessful()) {
                   /* if (response.body().getResponseCode() == 200) {

                        Toast.makeText(RegistrationQuickActivity.this, "Comment sended succesfully", Toast.LENGTH_SHORT).show();

                    }*/
                }
            }

            @Override
            public void onFailure(Call<EligibilityResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(RegistrationQuickActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void callWebServiceForFindCollege() {

        String sUsername = mUserNameET.getText().toString().trim();
        String sEmail = mEmailET.getText().toString().trim();
        String sMobile = mMobileET.getText().toString().trim();
        String sCollege = mCollegeNameET.getText().toString().trim();

        QuickRegFindCollegeRequest quickRegFindCollegeRequest = new QuickRegFindCollegeRequest();
        quickRegFindCollegeRequest.username = sUsername;
        quickRegFindCollegeRequest.email = sEmail;
        quickRegFindCollegeRequest.mobile = sMobile;
        quickRegFindCollegeRequest.college = sCollege;

        Call<FindCollegeResponse> call = apiInterface.quickRegFindCollege(quickRegFindCollegeRequest);
        call.enqueue(new Callback<FindCollegeResponse>() {
            @Override
            public void onResponse(Call<FindCollegeResponse> call, Response<FindCollegeResponse> response) {
                if (response.isSuccessful()) {
                   /* if (response.body().getResponseCode() == 200) {
                        Toast.makeText(this, "Succesfully", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }

            @Override
            public void onFailure(Call<FindCollegeResponse> call, Throwable t) {
                call.cancel();
               Toast.makeText(RegistrationQuickActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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


}
