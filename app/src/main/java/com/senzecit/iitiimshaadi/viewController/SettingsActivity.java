package com.senzecit.iitiimshaadi.viewController;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.customdialog.CustomListAdapterDialog;
import com.senzecit.iitiimshaadi.customdialog.Model;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.general_setting.GeneralSettingResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.LoginResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.login.ResponseData;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.about_me.AboutMeResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.general_setting.GeneralSettingRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.register_login.LoginRequest;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    ImageView mGeneralSettingIV,mNotificationSettingIV,mDeactiveAccountSettingIV;
    LinearLayout mGeneralSettingLL,mNotificationSettingLL,mDeactiveAccountLL;
    RelativeLayout mSendRL, mAddsRL;
    boolean generalSetting,notificationSetting,deactiveAccount = true;
    TextView mMemberSendTV, mMemberAddsTV;
    EditText mNameET, mPwdET, mConfirmPwdET;
    Button mGeneralSaveBtn, mDeactivateSaveBtn ;

    RadioButton mRB1, mRB2, mRB3, mRB4, mRB5 ;

    /** Network*/
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);

        init();
        handleView();

    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Settings");

        mGeneralSettingIV = (ImageView) findViewById(R.id.generalSettingIV);
        mNotificationSettingIV = (ImageView) findViewById(R.id.notificationSttingIV);
        mDeactiveAccountSettingIV = (ImageView) findViewById(R.id.deactiveAccountIV);

        mGeneralSettingLL = (LinearLayout) findViewById(R.id.generalSettingLL);
        mNotificationSettingLL = (LinearLayout) findViewById(R.id.notificationSettingLL);
        mDeactiveAccountLL = (LinearLayout) findViewById(R.id.deactiveAccountLL);

        mSendRL = (RelativeLayout) findViewById(R.id.idSendRL);
        mAddsRL = (RelativeLayout) findViewById(R.id.idAddsRL);

        mMemberSendTV = (TextView) findViewById(R.id.idMemberSendTV);
        mMemberAddsTV = (TextView) findViewById(R.id.idMemberAddsTV);

        mNameET = (EditText)findViewById(R.id.nameET) ;
        mPwdET = (EditText)findViewById(R.id.passwordET) ;
        mConfirmPwdET = (EditText)findViewById(R.id.confirmPasswordET) ;
        mGeneralSaveBtn = (Button)findViewById(R.id.generalSaveBtn);

        mRB1 = (RadioButton)findViewById(R.id.idRB1);
        mRB2 = (RadioButton)findViewById(R.id.idRB2);
        mRB3 = (RadioButton)findViewById(R.id.idRB3);
        mRB4 = (RadioButton)findViewById(R.id.idRB4);
        mRB5 = (RadioButton)findViewById(R.id.idRB5);
        mDeactivateSaveBtn = (Button)findViewById(R.id.deactiveAccountSaveBtn);

    }

    public void handleView(){

        mGeneralSettingIV.setOnClickListener(this);
        mNotificationSettingIV.setOnClickListener(this);
        mDeactiveAccountSettingIV.setOnClickListener(this);
        mBack.setOnClickListener(this);

        mSendRL.setOnClickListener(this);
        mAddsRL.setOnClickListener(this);

        mMemberSendTV.setOnClickListener(this);
        mMemberAddsTV.setOnClickListener(this);
        mGeneralSaveBtn.setOnClickListener(this);

        mDeactivateSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.generalSettingIV:
                if(generalSetting){
                    mGeneralSettingLL.setVisibility(View.GONE);
                    generalSetting = false;
                }else{
                    mGeneralSettingLL.setVisibility(View.VISIBLE);
                    generalSetting = true;
                }
                break;
            case R.id.notificationSttingIV:
                if(notificationSetting){
                    mNotificationSettingLL.setVisibility(View.GONE);
                    notificationSetting = false;
                }else{
                    mNotificationSettingLL.setVisibility(View.VISIBLE);
                    notificationSetting = true;
                }
                break;
            case R.id.deactiveAccountIV:
                if(deactiveAccount){
                    mDeactiveAccountLL.setVisibility(View.GONE);
                    deactiveAccount = false;
                }else{
                    mDeactiveAccountLL.setVisibility(View.VISIBLE);
                    deactiveAccount = true;
                }
                break;
            case R.id.backIV:
                SettingsActivity.this.finish();
                break;
            case R.id.idSendRL:
                showBooleanData(mMemberSendTV);
                break;
            case R.id.idAddsRL:
                showBooleanData(mMemberAddsTV);
                break;
            case R.id.generalSaveBtn:
                checkUserValidation();
                break;
            case R.id.deactiveAccountSaveBtn:
                checkUserValidation();
                break;

        }
    }

    public void showBooleanData(TextView textView){
        List<String> countryList = new ArrayList<>();
        countryList.add("Yes");
        countryList.add("No");

        showDialog(countryList, textView);
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

        final CustomListAdapterDialog clad1 = new CustomListAdapterDialog(SettingsActivity.this, models);
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

    /** Check Validation Section */
    public void checkUserValidation(){

        String sUsername = mNameET.getText().toString().trim();
        String password = mPwdET.getText().toString().trim();
        String confirmPassword = mConfirmPwdET.getText().toString().trim();

        if(isValidPassword(password, confirmPassword) == true){

          /** CALL API */
            callWebServiceForGenSetting();
            }else {
                AlertDialogSingleClick.getInstance().showDialog(SettingsActivity.this, "Alert!", "Password didn't match!");
            }

    }

    /** Check API Section */
    public void callWebServiceForGenSetting(){

        String sUsername = mNameET.getText().toString().trim();
        String sPassword = mPwdET.getText().toString().trim();

        GeneralSettingRequest request = new GeneralSettingRequest();
        request.token = Constants.Temp_Token;
        request.name = sUsername;
        request.password = sPassword;

        ProgressClass.getProgressInstance().showDialog(SettingsActivity.this);
        Call<GeneralSettingResponse> call = apiInterface.changeGeneralSetting(request);
        call.enqueue(new Callback<GeneralSettingResponse>() {
            @Override
            public void onResponse(Call<GeneralSettingResponse> call, Response<GeneralSettingResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    GeneralSettingResponse genSettingResponse = response.body();
                    if(genSettingResponse.getMessage().getSuccess() != null) {
                        if (genSettingResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

//                            AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Forgot Password", "An email with new password is sent to your registered email.");
                            Toast.makeText(SettingsActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SettingsActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SettingsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralSettingResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SettingsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
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
                    mPwdET.requestFocus();
                    AlertDialogSingleClick.getInstance().showDialog(this, "Alert!", "Password didn't match");
                }
            }else {
                mConfirmPwdET.requestFocus();
                status = false;
                AlertDialogSingleClick.getInstance().showDialog(this, "Alert!", "Re-Type Password can't empty");
            }
        }else {
            mPwdET.setText("");
            mConfirmPwdET.setText("");
            status = false;
            AlertDialogSingleClick.getInstance().showDialog(this, "Alert!", "Password can't empty");
        }

        return status;
    }


    /** DEACTIVATE ACCOUNT */
/*

    public void callWebServiceForDeactivate(){

        String token = "1984afa022ab472e8438f115d0c5ee1b";


        ProgressClass.getProgressInstance().showDialog(SettingsActivity.this);
        Call<AddFolderResponse> call = apiInterface.deactivateAccount();
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    AddFolderResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

//                            AlertDialogSingleClick.getInstance().showDialog(LoginActivity.this, "Forgot Password", "An email with new password is sent to your registered email.");
                            Toast.makeText(SettingsActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SettingsActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SettingsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SettingsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }
*/

    public String getCheckedReason(){
        if(mRB1.isChecked() == true){

            return null;
        }else if(mRB2.isChecked() == true){

            return null;
        }else if(mRB3.isChecked() == true){

            return null;
        }else if(mRB4.isChecked() == true){

            return null;
        }else if(mRB5.isChecked() == true){

            return null;
        }else {
            return  null;
        }
    }

}
