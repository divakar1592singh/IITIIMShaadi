package com.senzecit.iitiimshaadi.viewController;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.customdialog.CustomListAdapterDialog;
import com.senzecit.iitiimshaadi.customdialog.Model;
import com.senzecit.iitiimshaadi.model.commons.PreAuthWebRequest;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class QuickRegistrationActivity extends AppCompatActivity implements View.OnClickListener, RxNetworkingForObjectClass.CompletionHandler  {

    Toolbar mToolbar;
    TextView mTitle;
    Button mbuttonContinue, mSubmitFindEduBtn;
    ImageView mBoySelect,mBoyUnSelect,mGirlSelect,mGirlUnSelect;
    TextView mEducationTV, mStreamTV, mInstitutionTV;
    RelativeLayout mEducationRL, mInstitutionRL;
    LinearLayout mStreamRL ;
    EditText mUserNameET, mEmailET, mMobileET, mCollegeNameET;
    List<String> streamList;
    List<Integer> idList;
    RxNetworkingForObjectClass rxNetworkingClass;
    PreAuthWebRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration_quick);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        rxNetworkingClass = RxNetworkingForObjectClass.getInstance();
        rxNetworkingClass.setCompletionHandler(this);
        request = new PreAuthWebRequest();

        init();
        handleView();

    }

    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mTitle.setText("Quick Register");

        mbuttonContinue = findViewById(R.id.continueBtn);

        mBoySelect = findViewById(R.id.boySelect);
        mBoyUnSelect = findViewById(R.id.boyUnSelect);
        mGirlSelect = findViewById(R.id.girlSelect);
        mGirlUnSelect = findViewById(R.id.girlUnSelect);

        mEducationTV = findViewById(R.id.educationTV);
        mStreamTV = findViewById(R.id.idStreamTV);
        mInstitutionTV = findViewById(R.id.idInstitutionTV);

        mEducationRL = findViewById(R.id.id_educationRL);
        mStreamRL = findViewById(R.id.id_streamRL);
        mInstitutionRL = findViewById(R.id.id_institutionRL);

        mUserNameET = findViewById(R.id.userNameET);
        mEmailET = findViewById(R.id.emailET);
        mMobileET = findViewById(R.id.mobileET);
        mCollegeNameET = findViewById(R.id.collegeNameET);
        mSubmitFindEduBtn = findViewById(R.id.idSubmitFindEduBtn);


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

        mEducationTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String type = s.toString();
                if(type.equalsIgnoreCase("Indian")){
                    mStreamRL.setEnabled(true);
                    mStreamRL.setVisibility(View.VISIBLE);
                }else if(type.equalsIgnoreCase("International")){
                    mStreamRL.setEnabled(false);
                    mStreamRL.setVisibility(View.GONE);
                }
            }
        });

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
        final RecyclerView recyclerView = view.findViewById(R.id.custom_list);
//		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        view.findViewById(R.id.button_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final CustomListAdapterDialog clad1 = new CustomListAdapterDialog(QuickRegistrationActivity.this, models);
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
            return 1;
        }else if(mGirlSelect.getVisibility() == View.VISIBLE){
            return 2;
        }
        return  0;
    }
    public void showEducation(){
        List<String> educationList = new ArrayList<>();
        educationList.add("Indian");
        educationList.add("International");
        showDialog(educationList, mEducationTV);
    }
    public void showStream(){
        String[] ar = getResources().getStringArray(R.array.education_pre_auth);
        streamList = new ArrayList<String>(Arrays.asList(ar));
        showDialog(streamList, mStreamTV);
    }

    /** Check Validation Section */
    public void checkCourseValidation(){

        String sEducation = mEducationTV.getText().toString().trim();
        String sStream = mStreamTV.getText().toString().trim();
        String sInstitution = mInstitutionTV.getText().toString().trim();

        if(!sEducation.startsWith("Select")) {
            if(sEducation.equalsIgnoreCase("Indian")){
            if (!sStream.startsWith("Select")) {
                String sStream1 = mStreamTV.getText().toString().trim();
                int courseIndex = streamList.indexOf(sStream1);
                int courseId = idList.get(courseIndex);
                request.gender = String.valueOf(getGender());
                request.courseId = courseId;
                RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(QuickRegistrationActivity.this, CONSTANTS.CHECK_ELIGIBILITY, request, CONSTANTS.METHOD_1);
            } else {
                AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Alert!", "\nCheck 'Education or Stream/Course' selected");
            }}else if(sEducation.equalsIgnoreCase("International")){
                request.gender = String.valueOf(getGender());
                RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(QuickRegistrationActivity.this, CONSTANTS.CHECK_ELIGIBILITY, request, CONSTANTS.METHOD_1);
            }


        }else {
            AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Alert!", CONSTANTS.edu_error_msg);
        }

    }
    public void checkEligibilityValidation(){

        String sEducation = mEducationTV.getText().toString().trim();
        String sStream = mStreamTV.getText().toString().trim();
        String sInstitution = mInstitutionTV.getText().toString().trim();

        if(!sEducation.startsWith("Select") && !sInstitution.startsWith("Select")){
            Navigator.getClassInstance().navigateToActivity(this, NewUserRegisterActivity.class);
        }else {
            AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Alert!", "Education/Stream/Institution are not selected");
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

                       request.name = sUsername;
                       request.email = sEmail;
                       request.mobile = sMobile;
                       request.college = sCollege;

                       RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(QuickRegistrationActivity.this, CONSTANTS.FIND_COLLEGE, request, CONSTANTS.METHOD_2);

                        }else {
                          mCollegeNameET.requestFocus();
                          AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Alert!", "College name can't empty");
                        }
                    }else {
                        mMobileET.requestFocus();
                        AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Alert!", "Mobile no. not valid");
                    }
                }else {
                    mEmailET.requestFocus();
                    AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Alert!", "Email not valid");
                }
            }else {
                mUserNameET.requestFocus();
                AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Alert!", "Username can't Empty");
            }
    }else {
        AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Alert!", "Username can't Empty");
    }

    }

    @Override
    public void handle(JSONObject object, String methodName) {
        System.out.println(object);

        try {
            if(methodName.equalsIgnoreCase(CONSTANTS.METHOD_1)) {
                List<String> collegeList = new ArrayList<>();
                if (object.getJSONObject("message").getString("success").equalsIgnoreCase("success")) {

                    JSONArray jArray1 = object.getJSONArray("college");
                    for (int i = 0; i < jArray1.length(); i++){
                        String sCollege = jArray1.getJSONObject(i).optString("college");
                        collegeList.add(sCollege);
                    }
                    showDialog(collegeList, mInstitutionTV);
                }
            }else if(methodName.equalsIgnoreCase(CONSTANTS.METHOD_2)) {
                if(object.getJSONObject("message").getInt("response_code") == 200) {
                    AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Find College", "Submitted Successfully");
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(QuickRegistrationActivity.this, "Alert", AppMessage.TRY_AGAIN_INFO);
                }
            }

            } catch (JSONException e) {
                e.printStackTrace();
        }
    }

    @Override
    public void onServiceError(ANError error, String methodName) {

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

        Intent intent = new Intent(QuickRegistrationActivity.this, IntroSliderWebActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

}
