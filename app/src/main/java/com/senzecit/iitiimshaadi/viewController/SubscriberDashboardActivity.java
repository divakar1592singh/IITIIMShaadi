package com.senzecit.iitiimshaadi.viewController;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ExpListViewSubsAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.id_verification.IdVerificationResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.email_verification.EmailVerificationRequest;
import com.senzecit.iitiimshaadi.model.commons.PostAuthWebRequest;
import com.senzecit.iitiimshaadi.model.exp_listview.ExpOwnProfileModel;
import com.senzecit.iitiimshaadi.model.exp_listview.ExpPartnerProfileModel;
import com.senzecit.iitiimshaadi.navigation.BaseNavActivity;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.alert.ToastDialogMessage;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import in.gauriinfotech.commons.Commons;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriberDashboardActivity extends BaseNavActivity implements ExpListViewSubsAdapter.OnExpLvSubsItemClickListener, RxNetworkingForObjectClass.CompletionHandler {

    private static final String TAG = SubscriptionActivity.class.getSimpleName();
//    private static final String TAG = SubscriptionActivity.class.getSimpleName();


    ExpandableListView expListView;
    ExpListViewSubsAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    TextView tvDoc1, tvDoc2, tvDoc3, tvDoc4, mBiodataTv;
    Button mDocBtn1, mDocBtn2, mDocBtn3, mDocBtn4 ;

    private static final int READ_FILE_REQUEST_CODE = 101;
    int btnChooserCount = 0;
//    ProgressBar mProgress;
    /** Network*/
    APIInterface apiInterface;
    AppPrefs prefs;
    String typeOf;
    ProgressBar mProgress;
    AlertDialog dialogID = null;
    int lastExpandedPosition = -1;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RxNetworkingForObjectClass rxNetworkingClass;
    PostAuthWebRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_dashboard);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        rxNetworkingClass = RxNetworkingForObjectClass.getInstance();
        rxNetworkingClass.setCompletionHandler(this);
        request = new PostAuthWebRequest();

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();
        prefs.putInt(CONSTANTPREF.CHAT_USER_COUNT, 0);

        init();
        prepareListData();
        handleClick();

//        listAdapter = new ExpListViewSubsAdapter(this,listDataHeader,listDataChild, null);
//        expListView.setAdapter(listAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
                expListView.setSelectionFromTop(groupPosition, 0);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        callWebServiceForSubscribeDashboard();
        prefs.putInt(CONSTANTPREF.PROGRESS_STATUS_FOR_TAB, 1);

    }


    private void init(){
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.idRefreshLayout);
        expListView = (ExpandableListView) findViewById(R.id.expandableLV);
    }
    public void handleClick() {

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                resetModel();
                callWebServiceForSubsDashboardRefresh();
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        List<String> blankList = new ArrayList<String>();

        //Group
        listDataHeader.add("Header1");
        listDataHeader.add("Header2");
        listDataHeader.add("Basics & Lifestyle");
        listDataHeader.add("Religious Background");
        listDataHeader.add("Contact Details");
        listDataHeader.add("Family Details");
        listDataHeader.add("Education & Career");
        listDataHeader.add("About Me");
        /** SEARCH PARTNER*/
        // Adding child data
        listDataHeader.add("Partner Preferences");

        listDataHeader.add("Basics & LifestylePT");
        listDataHeader.add("Religious and Country PreferencesPT");
        listDataHeader.add("Education & CareerPT");
        listDataHeader.add("Choice of GroomPT");

        // Adding child data
        List<String> basicsLifestyle = new ArrayList<String>();
        basicsLifestyle.add("Name");
        basicsLifestyle.add("Profile Created for");
        basicsLifestyle.add("Age");
        basicsLifestyle.add("Diet");
        basicsLifestyle.add("Date Of Birth");
        basicsLifestyle.add("Marital Status");
        basicsLifestyle.add("Drink");
        basicsLifestyle.add("Smoke");
        basicsLifestyle.add("Health Issue");
        basicsLifestyle.add("Height");
        basicsLifestyle.add("Interests");
        basicsLifestyle.add("Save Changes");


//        Religious Background
        List<String> religiousBackgroung = new ArrayList<String>();
        religiousBackgroung.add("Religion");
        religiousBackgroung.add("Caste");
        religiousBackgroung.add("Mother Tongue");
        religiousBackgroung.add("Save Changes");

//        Contact Detail
        List<String> contactDetail = new ArrayList<String>();
        contactDetail.add("Phone Number");
        contactDetail.add("Alternate Number");
        contactDetail.add("Permanent Address");
        contactDetail.add("Permanent Country");
        contactDetail.add("Permanent State");
        contactDetail.add("Permanent City");
        contactDetail.add("Zip Code");
        contactDetail.add("Current Address");
        contactDetail.add("Current Country");
        contactDetail.add("Current State");
        contactDetail.add("Current City");
        contactDetail.add("Zip Code");
        contactDetail.add("Save Changes");

        List<String> familyDetails = new ArrayList<String>();
        familyDetails.add("Father's Name");
        familyDetails.add("Father's Occupation");
        familyDetails.add("Mother's Name");
        familyDetails.add("Mother's Occupation");
        familyDetails.add("Details on Sisters");
        familyDetails.add("Details on Brothers");
        familyDetails.add("Save Changes");

        List<String> educationCareer = new ArrayList<String>();
        educationCareer.add("Schooling");
        educationCareer.add("Schooling Year");
        educationCareer.add("Graduation");
        educationCareer.add("Graduation College");
        educationCareer.add("Graduation Year");
        educationCareer.add("Post Graduation");
        educationCareer.add("Post Graduation College");
        educationCareer.add("Post Graduation Year");
        educationCareer.add("Highest Education");
        educationCareer.add("Working With");
        educationCareer.add("Working As");
        educationCareer.add("Job Location");
        educationCareer.add("Annual Income");
        educationCareer.add("Linkedin Url");
        educationCareer.add("Save Changes");

        List<String> aboutMe = new ArrayList<String>();
        aboutMe.add("Write something about you");
        aboutMe.add("Save Changes");

        /** PARTNER */
        // Adding child data
        List<String> basicsLifestylePT = new ArrayList<String>();
        basicsLifestylePT.add("Minimum Age");
        basicsLifestylePT.add("Maximum Age");
        basicsLifestylePT.add("Min Height");
        basicsLifestylePT.add("Max Height");
        basicsLifestylePT.add("Marital Status");
        basicsLifestylePT.add("Save Changes");

        List<String> religiousBackgroungPT = new ArrayList<String>();
        religiousBackgroungPT.add("Preferred Religion");
        religiousBackgroungPT.add("Preferred Caste");
        religiousBackgroungPT.add("Preferred Country");
        religiousBackgroungPT.add("Save Changes");


        List<String> educationCareerPT = new ArrayList<String>();
        educationCareerPT.add("Preferred Education");
        educationCareerPT.add("Save Changes");

        List<String> aboutMePT = new ArrayList<String>();
        aboutMePT.add("Choice of Groom");
        aboutMePT.add("Save Changes");

        listDataChild.put(listDataHeader.get(0), blankList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), blankList);
        listDataChild.put(listDataHeader.get(2), basicsLifestyle); // Header, Child data
        listDataChild.put(listDataHeader.get(3), religiousBackgroung);
        listDataChild.put(listDataHeader.get(4), contactDetail);
        listDataChild.put(listDataHeader.get(5), familyDetails);
        listDataChild.put(listDataHeader.get(6), educationCareer);
        listDataChild.put(listDataHeader.get(7), aboutMe);

        listDataChild.put(listDataHeader.get(8), blankList);

        listDataChild.put(listDataHeader.get(9), basicsLifestylePT);
        listDataChild.put(listDataHeader.get(10), religiousBackgroungPT);
        listDataChild.put(listDataHeader.get(11), educationCareerPT);
        listDataChild.put(listDataHeader.get(12), aboutMePT);

    }

    private void alertDialogEmail(){

        final TextView mMessage;
        final Button mCloseBtn;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.popup_email_layout,null);

        mMessage = dialogView.findViewById(R.id.tvEmail);
        mCloseBtn = dialogView.findViewById(R.id.idCloseBtn);

        mMessage.setText("Press 'Resend' to send verification mail");

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebServiceForEmailVerification();
                dialog.dismiss();
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }
    private void alertDialogMobile(){

        final Button mConfirm,mResend;
        final ImageView mCloseIV;
        final EditText mOtpET;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.popup_mobile_layout,null);

        mOtpET = dialogView.findViewById(R.id.idOtpTV);
        mConfirm = dialogView.findViewById(R.id.confirmBtn);
        mResend = dialogView.findViewById(R.id.resendFPLBtn);
        mCloseIV = dialogView.findViewById(R.id.idCloseIV);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otp = mOtpET.getText().toString().trim();
                if (TextUtils.isEmpty(otp)){
                     AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP", "OTP is empty");
                }else {
                    callWebServiceForOTPVerification(otp, dialog);
                }

            }
        });

        mResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebServiceForResendOTP();
            }
        });

        mCloseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.show();
    }
    private void alertDialogDocuments(){

        Button mClose ;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.popup_doc_layout,null);

        tvDoc1 = dialogView.findViewById(R.id.idDocTv1);
        tvDoc2 = dialogView.findViewById(R.id.idDocTv2);
        tvDoc3 = dialogView.findViewById(R.id.idDocTv3);
        tvDoc4 = dialogView.findViewById(R.id.idDocTv4);

        mDocBtn1 = dialogView.findViewById(R.id.idDocBtn1);
        mDocBtn2 = dialogView.findViewById(R.id.idDocBtn2);
        mDocBtn3 = dialogView.findViewById(R.id.idDocBtn3);
        mDocBtn4 = dialogView.findViewById(R.id.idDocBtn4);


        mClose = dialogView.findViewById(R.id.confirmBtn);

        mDocBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 1;
                typeOf = UserDefinedKeyword.bioData.toString();
                showStorage();
            }
        });
        mDocBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 2;
                typeOf = UserDefinedKeyword.higher_document.toString();
                showStorage();
            }
        });
        mDocBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 3;
                typeOf = UserDefinedKeyword.under_graduate.toString();
                showStorage();
            }
        });
        mDocBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 4;
                typeOf = UserDefinedKeyword.post_graduate.toString();
                showStorage();
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mConfirm.setBackground(getResources().getDrawable(R.drawable.button_shape_select_forgot));
                mConfirm.setTextColor(getResources().getColor(R.color.colorWhite));*/
                dialog.dismiss();
            }
        });


        dialog.setView(dialogView);
        dialog.show();
    }
    private void alertDialogIDProof(){

        final Button mBrowse, mCancel;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogID = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.popup_id_proof_layout,null);

        mBiodataTv= dialogView.findViewById(R.id.tvIDProof);
        mBrowse = dialogView.findViewById(R.id.idBiodataUpload);
        mCancel = dialogView.findViewById(R.id.idCancelBtn);

        mBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 5;
                typeOf = UserDefinedKeyword.id_proof.toString();
                showStorage();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogID.dismiss();
            }
        });


        dialogID.setView(dialogView);
        dialogID.show();
    }
    //    FILE
    private  void showStorage()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent = Intent.createChooser(intent, "Choose a file");
        startActivityForResult(intent, READ_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if(requestCode == READ_FILE_REQUEST_CODE){
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();

                    Log.i(TAG, "Uri: " + uri.toString());
                    try {
//                        uploadCvFile(uri);
                        configureButtonToUpload(uri);

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled recording
                    Toast.makeText(getApplicationContext(),"User cancelled selection", Toast.LENGTH_SHORT)
                            .show();
                } else {
                }
            }
        }
    }

    public void configureButtonToUpload(Uri uri)throws URISyntaxException{

        String fullPath = Commons.getPath(uri, this);
//        String fullPath = "";

        File file = new File(fullPath);
        if (btnChooserCount == 1){
            tvDoc1.setText(getFileName(file.getPath()));
            showUploadAlert("Biodata", file);
        }else if (btnChooserCount == 2){
            tvDoc2.setText(getFileName(file.getPath()));
            showUploadAlert("Higher Ed.",file);
        }else if (btnChooserCount == 3){
            tvDoc3.setText(getFileName(file.getPath()));
            showUploadAlert("Under Grad.", file);
        }else if (btnChooserCount == 4){
            tvDoc4.setText(getFileName(file.getPath()));
            showUploadAlert("Post Grad.",file);
        }else if (btnChooserCount == 5){
            mBiodataTv.setText(getFileName(file.getPath()));
            showUploadAlert("ID", file);
        }
    }

    public void showUploadAlert(String title, File filePath){
        new AlertDialog.Builder(SubscriberDashboardActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title+" Upload")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            callWebServiceForFileUpload(filePath);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private String getFileName(String filePath){
        return filePath.substring(filePath.lastIndexOf("/")+1);
    }

    /** API INTEGRATION */
    private void callWebServiceForSubscribeDashboard(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        request.token = token;

        RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(SubscriberDashboardActivity.this, CONSTANTS.SUBS_DASHBOARD_PATH, request, CONSTANTS.METHOD_1);

    }

    private void callWebServiceForSubsDashboardRefresh(){

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity.this) == true){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        request.token = token;
        RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(SubscriberDashboardActivity.this, CONSTANTS.SUBS_DASHBOARD_PATH, request, CONSTANTS.METHOD_1, false);

    }else {
        mSwipeRefreshLayout.setRefreshing(false);
            networkDialog();
    }

    }

    /** Email */
    public void callWebServiceForEmailVerification(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        EmailVerificationRequest emailVerirequest = new EmailVerificationRequest();
        emailVerirequest.token = token;


        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
        Call<AddFolderResponse> call = apiInterface.emailVerification(emailVerirequest);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                try {
                    if (response.isSuccessful()) {

                        if (response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

                            callWebServiceForSubscribeDashboard();
                            showAlertMsg("Alert", "Verfication email sended. Check your mail and follow instruction");
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "Email Alert", "Verfication email sended. Check your mail and follow instruction");
                        }
                    }
                }catch (NullPointerException npe){
                    Log.e(TAG, "#Error : "+npe, npe);
                    showSnackBar();
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SubscriberDashboardActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity.this);
        }
    }
    /** MOBILE */
    public void callWebServiceForResendOTP(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<AddFolderResponse> call = apiInterface.resendOTP(token);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                String msg1 = "We have sent you a new OTP. In case, you don’t receive it, please send \\\"Verify\\\" message to our mobile number 07042947312.";
                if (response.isSuccessful()) {
                    AddFolderResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase(msg1)) {
//                     if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("OTP is sent on your registered mobile number.")) {

                            callWebServiceForSubscribeDashboard();
//                            Toast.makeText(SubscriberDashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", serverResponse.getMessage().getSuccess());
                            showAlertMsg("Info", msg1);

                        }else {
                            showAlertMsg("Info", msg1);
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", msg1);
                        }
                    }else {
                        Toast.makeText(SubscriberDashboardActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SubscriberDashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity.this);
        }
    }
    public void callWebServiceForOTPVerification(String otp, AlertDialog dialog){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<AddFolderResponse> call = apiInterface.verifiyOTP(token, otp);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    AddFolderResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getResponseCode() == 200) {

                            dialog.dismiss();
                            callWebServiceForSubscribeDashboard();
                            showAlertMsg("Alert", serverResponse.getMessage().getSuccess() );
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", serverResponse.getMessage().getSuccess());

                        } else {
                            ToastDialogMessage.getInstance().showToast(SubscriberDashboardActivity.this, serverResponse.getMessage().getSuccess());
//     Toast.makeText(SubscriberDashboardActivity.this, CONSTANTS.unknown_err, Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SubscriberDashboardActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SubscriberDashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity.this);
        }
    }
    /** File Upload */
    public void callWebServiceForFileUpload(final File file)throws URISyntaxException {

        System.out.print(file);

        Call<IdVerificationResponse> callUpload = null;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity.this) == true){

        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(typeOf, file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        callUpload = callManipulationMethod(fileToUpload, filename, token);

        callUpload.enqueue(new Callback<IdVerificationResponse>() {
            @Override
            public void onResponse(Call<IdVerificationResponse> call, Response<IdVerificationResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {

                    if(typeOf.equalsIgnoreCase(UserDefinedKeyword.id_proof.toString())){

                        dialogID.dismiss();
                    }
                    showAlertMsg("Info", response.body().getMessage().getSuccess());
//                    AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "Info", "" + response.body().getMessage().getSuccess());
                } else {

                        showAlertMsg("Alert", CONSTANTS.unknown_err);
//                    AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "Info", "Confuse");
                }
            }
            @Override
            public void onFailure(Call<IdVerificationResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                showAlertMsg("Alert", CONSTANTS.unknown_err);
           }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity.this);
        }
    }

    public Call<IdVerificationResponse> callManipulationMethod(MultipartBody.Part fileToUpload, RequestBody filename, String token)
        {

            if(typeOf.equalsIgnoreCase(UserDefinedKeyword.id_proof.toString())){
                return apiInterface.idVerification(fileToUpload, filename, token);
            }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.bioData.toString())){
                return apiInterface.biodataUpload(fileToUpload, filename, token);
            }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.higher_document.toString())){
                return apiInterface.highestEduUpload(fileToUpload, filename, token);
            }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.under_graduate.toString())){
                return apiInterface.underGradCertUpload(fileToUpload, filename, token);
            }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.post_graduate.toString())){
                return apiInterface.postGradCertUpload(fileToUpload, filename, token);
            }else {
                return null;
            }

        }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit!")
                .setMessage("Are you sure?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        SubscriberDashboardActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    public void verifiyEmail() {
        alertDialogEmail();
    }

    @Override
    public void verifiyMob() {
        alertDialogMobile();
    }

    @Override
    public void verifiyDoc() {
        alertDialogDocuments();
    }

    @Override
    public void verifiyIDProof() {
        alertDialogIDProof();
    }

    @Override
    public void onProfileSubmission(String urlPath, PostAuthWebRequest request, String methodName) {

        RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(SubscriberDashboardActivity.this, urlPath, request, methodName);
    }

    @Override
    public void refreshPage() {

    }

    public void resetModel(){

        ExpOwnProfileModel.getInstance().setName("");
        ExpOwnProfileModel.getInstance().setProfile("");
        ExpOwnProfileModel.getInstance().setAge("");
        ExpOwnProfileModel.getInstance().setDiet("");
        ExpOwnProfileModel.getInstance().setDate_Of_Birth("");

        ExpOwnProfileModel.getInstance().setMarital_Status("");
        ExpOwnProfileModel.getInstance().setDrink("");
        ExpOwnProfileModel.getInstance().setSmoke("");
        ExpOwnProfileModel.getInstance().setHealth_Issue("");
        ExpOwnProfileModel.getInstance().setHeight("");
        ExpOwnProfileModel.getInstance().setInterests("");

        ExpOwnProfileModel.getInstance().setReligion("");
        ExpOwnProfileModel.getInstance().setCaste("");
        ExpOwnProfileModel.getInstance().setMother_Tongue("");

        ExpOwnProfileModel.getInstance().setPhone_Number("");
        ExpOwnProfileModel.getInstance().setAlternate_Number("");
        ExpOwnProfileModel.getInstance().setPermanent_Address("");
        ExpOwnProfileModel.getInstance().setPermanent_Country("");
        ExpOwnProfileModel.getInstance().setPermanent_State("");
        ExpOwnProfileModel.getInstance().setPermanent_City("");
        ExpOwnProfileModel.getInstance().setZip_Code_Perm("");
        ExpOwnProfileModel.getInstance().setCurrent_Address("");
        ExpOwnProfileModel.getInstance().setCurrent_Country("");
        ExpOwnProfileModel.getInstance().setCurrent_State("");
        ExpOwnProfileModel.getInstance().setCurrent_City("");
        ExpOwnProfileModel.getInstance().setZip_Code_Cur("");

        ExpOwnProfileModel.getInstance().setFather_Name("");
        ExpOwnProfileModel.getInstance().setFather_Occupation("");
        ExpOwnProfileModel.getInstance().setMother_Name("");
        ExpOwnProfileModel.getInstance().setMother_Occupation("");
        ExpOwnProfileModel.getInstance().setDetails_Sisters("");
        ExpOwnProfileModel.getInstance().setDetails_Brothers("");

        ExpOwnProfileModel.getInstance().setSchooling("");
        ExpOwnProfileModel.getInstance().setSchooling_Year("");
        ExpOwnProfileModel.getInstance().setGraduation("");
        ExpOwnProfileModel.getInstance().setGraduation_College("");
        ExpOwnProfileModel.getInstance().setGraduation_Year("");
        ExpOwnProfileModel.getInstance().setPost_Graduation("");
        ExpOwnProfileModel.getInstance().setPost_Graduation_College("");
        ExpOwnProfileModel.getInstance().setPost_Graduation_Year("");
        ExpOwnProfileModel.getInstance().setHighest_Education("");
        ExpOwnProfileModel.getInstance().setWorking_With("");
        ExpOwnProfileModel.getInstance().setWorking_As("");
        ExpOwnProfileModel.getInstance().setWork_Location("");
        ExpOwnProfileModel.getInstance().setAnnual_Income("");
        ExpOwnProfileModel.getInstance().setLinkdIn_Url("");

        ExpOwnProfileModel.getInstance().setAbout_you("");

        ExpPartnerProfileModel.getInstance().setMinimum_Age("");
        ExpPartnerProfileModel.getInstance().setMaximum_Age("");
        ExpPartnerProfileModel.getInstance().setMin_Height("");
        ExpPartnerProfileModel.getInstance().setMax_Height("");
        ExpPartnerProfileModel.getInstance().setMarital_Status("");

        ExpPartnerProfileModel.getInstance().setPreferred_Religion("");
        ExpPartnerProfileModel.getInstance().setPreferred_Caste("");
        ExpPartnerProfileModel.getInstance().setPreferred_Country("");

        ExpPartnerProfileModel.getInstance().setPreferred_Education("");

        ExpPartnerProfileModel.getInstance().setChoice_of_Groom("");


    }


    //        EXTRA
    public void showSnackBar(){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "Something went wrong! Retry", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }

    public void showAlertMsg(String title, String msg){
        AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, title, msg);
    }

    public void reTryMethod(){

        String title = "Alert";
        String msg = "Oops. Please Try Again! \n";

        final Dialog dialog = new Dialog(SubscriberDashboardActivity.this);
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
                finish();
                finishActivity(0);
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWebServiceForSubscribeDashboard();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void networkDialog(){
        new AlertDialog.Builder(SubscriberDashboardActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("No Network")
                .setMessage("Check Your Internet")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        finishActivity(0);
                        dialog.dismiss();
                    }

                })
                .show();
    }

    @Override
    public void handle(JSONObject object, String methodName) {

        System.out.println(object);
//        if(mSwipeRefreshLayout.isRefreshing() == true)
            mSwipeRefreshLayout.setRefreshing(false);

        try {

            if(methodName.equalsIgnoreCase(CONSTANTS.METHOD_1)) {
                if (object.getJSONObject("message").getInt("response_code") == 200) {

                    listAdapter = new ExpListViewSubsAdapter(this, listDataHeader, listDataChild, object);
                    expListView.setAdapter(listAdapter);

                    prefs.putInt(CONSTANTPREF.CHAT_USER_COUNT, object.optInt("chatUserCount"));

                } else {
                    reTryMethod();
                }
            }else if(methodName.equalsIgnoreCase(CONSTANTS.METHOD_2)){
                System.out.println(object);
                if (object.getJSONObject("message").getInt("response_code") == 200) {
//                    callWebServiceForSubsDashboardRefresh();
                    AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, AppMessage.INFO, AppMessage.DATA_SUBMIT_INFO);
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, AppMessage.ALERT, AppMessage.SOME_ERROR_INFO);
                }
                System.out.println(object);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}