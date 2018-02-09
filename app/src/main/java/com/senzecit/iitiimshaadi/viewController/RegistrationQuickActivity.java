package com.senzecit.iitiimshaadi.viewController;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.customdialog.CustomListAdapterDialog;
import com.senzecit.iitiimshaadi.customdialog.Model;
import com.senzecit.iitiimshaadi.model.api_response_model.general_setting.GeneralSettingResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.EligibilityResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.find_college.FindCollegeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_institution.QuickRegInstitutionResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_stream.College;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegInstitutionRequest;
import com.senzecit.iitiimshaadi.model.api_response_model.quick_register.pkg_stream.QuickRegStreamResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegEligibilityRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegFindCollegeRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.QuickRegStreamRequest;
import com.senzecit.iitiimshaadi.model.commons.CourseModel;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;

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

    List<String> streamList;
    List<Integer> idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration_quick);

        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);

        init();
        handleView();

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

    public void handleView(){


        mbuttonContinue.setOnClickListener(this);
        mBoySelect.setOnClickListener(this);
        mBoyUnSelect.setOnClickListener(this);
        mGirlSelect.setOnClickListener(this);
        mGirlUnSelect.setOnClickListener(this);

        mEducationRL.setOnClickListener(this);
        mStreamRL.setOnClickListener(this);
        mInstitutionRL.setOnClickListener(this);
        mSubmitFindEduBtn.setOnClickListener(this);


        idList = new ArrayList<>();
        idList.add(16);
        idList.add(15);
        idList.add(13);
        idList.add(1);
        idList.add(2);
        idList.add(3);
        idList.add(4);
        idList.add(5);
        idList.add(6);
        idList.add(7);
        idList.add(8);
        idList.add(9);
        idList.add(10);
        idList.add(11);
        idList.add(12);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.continueBtn:
//                startActivity(new Intent(this,NewUserRegisterActivity.class));
//                checkEligibilityValidation();
                checkEligibilityValidation();
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
//                showInstution();
                checkCourseValidation();
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
                model.setName("Select " + i);
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
    public int getGender(){
        if(mBoySelect.getVisibility() == View.VISIBLE){
            Toast.makeText(this, "Male", Toast.LENGTH_SHORT).show();
            return 1;
        }else if(mGirlSelect.getVisibility() == View.VISIBLE){
            Toast.makeText(this, "Female", Toast.LENGTH_SHORT).show();
            return 2;
        }
        return  0;
    }
    public void showEducation(){
        List<String> educationList = new ArrayList<>();
        educationList.add("Indian");
        educationList.add("InterNational");
        showDialog(educationList, mEducationTV);
    }
    public void showStream(){

        streamList = new ArrayList<>();
        streamList.add("Actuary");
        streamList.add("Hotel Management");
        streamList.add("Management");
        streamList.add("Engineering/Architecture");
        streamList.add("Medical");
        streamList.add("CA/CS/ICWA/CFA");
        streamList.add("Law");
        streamList.add("Design/Fashion Design");
        streamList.add("Government Officer");
        streamList.add("Social Work (Masters)");
        streamList.add("Media Communication (Masters)");
        streamList.add("Performing and Fine Arts");
        streamList.add("Masters");
        streamList.add("Research (Ph.D/FPM)");
        streamList.add("Others");

        showDialog(streamList, mStreamTV);
    }
    public void showInstution(List<College> collegeList){
        List<String> countryList = new ArrayList<>();
/*
        countryList.add("India");
        countryList.add("Russia");
        countryList.add("America");
        countryList.add("China");
*/

        for (int i = 0; i < collegeList.size(); i++ ){
            countryList.add(collegeList.get(i).getCollege());
        }
        showDialog(countryList, mInstitutionTV);
    }

    /** Check Validation Section */
    public void checkCourseValidation(){

        String sEducation = mEducationTV.getText().toString().trim();
        String sStream = mStreamTV.getText().toString().trim();
        String sInstitution = mInstitutionTV.getText().toString().trim();

        if(!sEducation.startsWith("Select") && !sStream.startsWith("Select")){
            callWebServiceForStream();
        }else {
            AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "Education/Stream/Institution are not selected");
        }

    }
        public void checkEligibilityValidation(){

        String sEducation = mEducationTV.getText().toString().trim();
        String sStream = mStreamTV.getText().toString().trim();
        String sInstitution = mInstitutionTV.getText().toString().trim();

        if(!sEducation.startsWith("Select") && !sStream.startsWith("Select") && !sInstitution.startsWith("Select")){
            Navigator.getClassInstance().navigateToActivity(this, NewUserRegisterActivity.class);
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

//                            AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Alert!", "Find College Validation Successfull");
                            callWebServiceForFindCollege();
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

        String sStream = mStreamTV.getText().toString().trim();

        int courseIndex = streamList.indexOf(sStream);

        int courseId = idList.get(courseIndex);

        QuickRegStreamRequest quickRegStreamRequest = new QuickRegStreamRequest();
//        quickRegStreamRequest.gender = 1;
//        quickRegStreamRequest.courseId = 1;
        quickRegStreamRequest.gender = getGender();
        quickRegStreamRequest.courseId = courseId;

        ProgressClass.getProgressInstance().showDialog(RegistrationQuickActivity.this);
        Call<QuickRegStreamResponse> call = apiInterface.fetchStreamData(quickRegStreamRequest);
        call.enqueue(new Callback<QuickRegStreamResponse>() {
            @Override
            public void onResponse(Call<QuickRegStreamResponse> call, Response<QuickRegStreamResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    QuickRegStreamResponse courseResponse = response.body();
                    if(courseResponse.getMessage().getSuccess() != null) {
                        if (courseResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {
                            Toast.makeText(RegistrationQuickActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            List<College> collegeList = courseResponse.getCollege();
                            showInstution(collegeList);

                        } else {
                            Toast.makeText(RegistrationQuickActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegistrationQuickActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuickRegStreamResponse> call, Throwable t) {
            call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
            Toast.makeText(RegistrationQuickActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }
        });

    }
  /*  public void callWebServiceForInstitution() {

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
                   *//*if (response.body().getResponseCode() == 200) {
                       Toast.makeText(this, "Succesfully", Toast.LENGTH_SHORT).show();
                   }*//*
               }
           }

           @Override
           public void onFailure(Call<QuickRegInstitutionResponse> call, Throwable t) {
               call.cancel();
               Toast.makeText(RegistrationQuickActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
           }
       });
    }*/
/*
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
                   */
/* if (response.body().getResponseCode() == 200) {

                        Toast.makeText(RegistrationQuickActivity.this, "Comment sended succesfully", Toast.LENGTH_SHORT).show();

                    }*//*

                }
            }

            @Override
            public void onFailure(Call<EligibilityResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(RegistrationQuickActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
*/

    public void callWebServiceForFindCollege() {

        String sUsername = mUserNameET.getText().toString().trim();
        String sEmail = mEmailET.getText().toString().trim();
        String sMobile = mMobileET.getText().toString().trim();
        String sCollege = mCollegeNameET.getText().toString().trim();

        QuickRegFindCollegeRequest quickRegFindCollegeRequest = new QuickRegFindCollegeRequest();
        quickRegFindCollegeRequest.name = sUsername;
        quickRegFindCollegeRequest.email = sEmail;
        quickRegFindCollegeRequest.mobile = sMobile;
        quickRegFindCollegeRequest.college = sCollege;

        ProgressClass.getProgressInstance().showDialog(RegistrationQuickActivity.this);
        Call<FindCollegeResponse> call = apiInterface.quickRegFindCollege(quickRegFindCollegeRequest);
        call.enqueue(new Callback<FindCollegeResponse>() {
            @Override
            public void onResponse(Call<FindCollegeResponse> call, Response<FindCollegeResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                   /* if (response.body().getResponseCode() == 200) {
                        Toast.makeText(this, "Succesfully", Toast.LENGTH_SHORT).show();
                    }*/
                    AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Find College", "Success");

                }
            }

            @Override
            public void onFailure(Call<FindCollegeResponse> call, Throwable t) {
                call.cancel();
                AlertDialogSingleClick.getInstance().showDialog(RegistrationQuickActivity.this, "Find College", "Oops");
                ProgressClass.getProgressInstance().stopProgress();
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
