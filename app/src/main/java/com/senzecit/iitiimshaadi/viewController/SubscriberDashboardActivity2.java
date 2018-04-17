package com.senzecit.iitiimshaadi.viewController;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ExpListViewSubsAdapter;
import com.senzecit.iitiimshaadi.adapter.ExpListViewSubsPartnerAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.my_profile.MyProfileResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.id_verification.IdVerificationResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.email_verification.EmailVerificationRequest;
import com.senzecit.iitiimshaadi.navigation.BaseNavActivity;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import in.gauriinfotech.commons.Commons;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriberDashboardActivity2 extends BaseNavActivity {

    private static final String TAG = SubscriptionActivity.class.getSimpleName();

    ExpListViewSubsAdapter listAdapter;
    ExpListViewSubsPartnerAdapter partnerlistAdapter;
    ExpandableListView expListView, expListViewPartner;
    List<String> listDataHeader, listDataHeaderPartner;
    HashMap<String, List<String>> listDataChild, listDataChildPartner;
    TextView mEmailVerifyTV, mMobVerifyTV, mDocumentsVerifyTV, mProofVerifyTV, mUsrNameTV, mUsrIdTV, mProfilepercTV;
    ImageView mAlbumIV;
    CircleImageView mProfileCIV;
    ScrollView mScrollView;

    //
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
    LinearLayout albumLayout;
    AlertDialog dialogID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_dashboard);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

        init();

        prepareListData();
        prepareListDataPartner();

        listAdapter = new ExpListViewSubsAdapter(this,listDataHeader,listDataChild, null);
        expListView.setAdapter(listAdapter);

        partnerlistAdapter = new ExpListViewSubsPartnerAdapter(this,listDataHeaderPartner,listDataChildPartner, null);
        expListViewPartner.setAdapter(partnerlistAdapter);

        setInitListViewHeight(expListView);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                setListViewHeight(expandableListView, i);
                return false;
            }
        });
        //Partner
        setInitListViewHeight(expListViewPartner);
        expListViewPartner.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView, i);
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        handleClick();
//        callWebServiceForSubscribeDashboard();
        callWebServiceMyProfile();
        mScrollView.smoothScrollTo(0, 0);
        prefs.putInt(CONSTANTPREF.PROGRESS_STATUS_FOR_TAB, 1);
    }

    private void init(){

        albumLayout = findViewById(R.id.idAlbumLayout);

        mScrollView = findViewById(R.id.idScrlView);

        mProfileCIV = findViewById(R.id.idProfileCIV);
        mUsrNameTV = findViewById(R.id.idUserNameTV);
        mUsrIdTV = findViewById(R.id.idUserId);
        mProgress = findViewById(R.id.idprogress);
        mProfilepercTV = findViewById(R.id.idProfilePercTV);

        mAlbumIV = findViewById(R.id.idAlbum);

        mEmailVerifyTV = findViewById(R.id.idEmailVerify);
        mMobVerifyTV = findViewById(R.id.idMobVerify);
        mDocumentsVerifyTV = findViewById(R.id.idDocumentsVerify);
        mProofVerifyTV = findViewById(R.id.idProofVerify);

        expListView = findViewById(R.id.expandableLV);
        expListViewPartner = findViewById(R.id.partnerPrefExpLV);
    }
    public void handleClick() {

        albumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubscriberDashboardActivity2.this, AlbumActivity.class));
            }
        });
        mProfileCIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.getClassInstance().navigateToActivity(SubscriberDashboardActivity2.this, ProfileActivity.class);
            }
        });

        mEmailVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogEmail();
            }
        });
        mMobVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogMobile();
            }
        });
        mDocumentsVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogDocuments();
            }
        });
        mProofVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogIDProof();
            }
        });

        setProfileData();

    }

    public  void  setProfileData(){

        String userId = prefs.getString(CONSTANTS.LOGGED_USERID);
        String profileUri = CONSTANTS.IMAGE_AVATAR_URL+userId+"/"+prefs.getString(CONSTANTS.LOGGED_USER_PIC);
        String userName = prefs.getString(CONSTANTS.LOGGED_USERNAME);

        if(!TextUtils.isEmpty(profileUri)){
            Glide.with(SubscriberDashboardActivity2.this).load(profileUri).error(R.drawable.profile_img1).into(mProfileCIV);
        }

        mUsrNameTV.setText(new StringBuilder("@").append(userName));
        mUsrIdTV.setText(new StringBuilder("@").append(userId));

        try{
            String userType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
            if(userType.equalsIgnoreCase("subscriber_viewer")) {

                setVerificationStatus(true, true, true, true, true);

            }else if(userType.equalsIgnoreCase("subscriber")){

                setVerificationStatus(false, false, false, false, false);
                callApiForDocStatus();
            }

        }catch (NullPointerException npe){
            Log.e(TAG, " #Error : "+npe, npe);
        }

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //Group
        listDataHeader.add("Basics & Lifestyle");
        listDataHeader.add("Religious Background");
        listDataHeader.add("Contact Details");
        listDataHeader.add("Family Details");
        listDataHeader.add("Education & Career");
        listDataHeader.add("About Me");
//        listDataHeader.add("Acquaintances");
//        listDataHeader.add("Video");

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
        educationCareer.add("Work Location");
        educationCareer.add("Annual Income");
        educationCareer.add("LinkdIn Url");
        educationCareer.add("Save Changes");

        List<String> aboutMe = new ArrayList<String>();
        aboutMe.add("Write something about you");
        aboutMe.add("Save Changes");

/*
        List<String> acquiantances = new ArrayList<String>();
        acquiantances.add("1.Acquaintance Name");
        acquiantances.add("1.Acquaintance Remark");
        acquiantances.add("2.Acquaintance Name");
        acquiantances.add("2.Acquaintance Remark");
        acquiantances.add("3.Acquaintance Name");
        acquiantances.add("3.Acquaintance Remark");
        acquiantances.add("4.Acquaintance Name");
        acquiantances.add("4.Acquaintance Remark");
        acquiantances.add("5.Acquaintance Name");
        acquiantances.add("5.Acquaintance Remark");
        acquiantances.add("Save Changes");
*/

//        List<String> video = new ArrayList<String>();
//        video.add("My Video");

        listDataChild.put(listDataHeader.get(0), basicsLifestyle); // Header, Child data
        listDataChild.put(listDataHeader.get(1), religiousBackgroung);
        listDataChild.put(listDataHeader.get(2), contactDetail);
        listDataChild.put(listDataHeader.get(3), familyDetails);
        listDataChild.put(listDataHeader.get(4), educationCareer);
        listDataChild.put(listDataHeader.get(5), aboutMe);
//        listDataChild.put(listDataHeader.get(6), acquiantances);
//        listDataChild.put(listDataHeader.get(7), video);
    }

    private void prepareListDataPartner() {
        listDataHeaderPartner = new ArrayList<String>();
        listDataChildPartner = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeaderPartner.add("Basics & Lifestyle");
        listDataHeaderPartner.add("Religious and Country Preference");
        listDataHeaderPartner.add("Education & Career");
        listDataHeaderPartner.add("Groom");

//        listDataHeaderPartner.add("Video");

        // Adding child data
        List<String> basicsLifestyle = new ArrayList<String>();
        basicsLifestyle.add("Minimum Age");
        basicsLifestyle.add("Maximum Age");
        basicsLifestyle.add("Min Height");
        basicsLifestyle.add("Max Height");
        basicsLifestyle.add("Marital Status");
        basicsLifestyle.add("Save Changes");

        List<String> religiousBackgroung = new ArrayList<String>();
        religiousBackgroung.add("Preferred Religion");
        religiousBackgroung.add("Preferred Caste");
        religiousBackgroung.add("Preferred Country");
        religiousBackgroung.add("Save Changes");


        List<String> educationCareer = new ArrayList<String>();
        educationCareer.add("Preferred Education");
        educationCareer.add("Save Changes");

        List<String> aboutMe = new ArrayList<String>();
        aboutMe.add("Choice of Groom");
        aboutMe.add("Save Changes");

//        List<String> video = new ArrayList<String>();
//        video.add("Partner Preference Video");

        listDataChildPartner.put(listDataHeaderPartner.get(0), basicsLifestyle); // Header, Child data
        listDataChildPartner.put(listDataHeaderPartner.get(1), religiousBackgroung);
        listDataChildPartner.put(listDataHeaderPartner.get(2), educationCareer);
        listDataChildPartner.put(listDataHeaderPartner.get(3), aboutMe);
//        listDataChildPartner.put(listDataHeaderPartner.get(4), video);


    }

    private void setInitListViewHeight(ExpandableListView listView) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            /*if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }*/
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void setListViewHeight(ExpandableListView listView,int group) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void setCollapseListViewHeight(ExpandableListView listView,int group) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

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

        mMessage.setText("Press 'Resend' to send verificatin mail");

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
                     AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity2.this, "OTP", "OTP is empty");
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

//        String fullPath = Commons.getPath(uri, this);
        String fullPath = "";

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
        new AlertDialog.Builder(SubscriberDashboardActivity2.this)
                .setTitle(title+" Upload")
                .setMessage("are you sure?")
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

                    }
                })
                .show();
    }

    private String getFileName(String filePath){
        return filePath.substring(filePath.lastIndexOf("/")+1);
    }

    public void setSubsDashboardData(JSONObject jsonObject){

        String username = jsonObject.optString("name");
        int profileCompletion = jsonObject.optInt("profile_complition");

        mProgress.setProgress(profileCompletion);
        mProfilepercTV.setText(new StringBuilder(String.valueOf(profileCompletion)).append("%"));
        mUsrNameTV.setText(new StringBuilder("@").append(username));

        callWebServiceMyProfile();

    }
    /** API INTEGRATION */

    /* Subscriber Dashboard*/
    public void callWebServiceForSubscribeDashboard(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity2.this) == true){

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity2.this);
        AndroidNetworking.post("https://iitiimshaadi.com/api/subscriber_dashboard.json")
                .addBodyParameter("token", token)
//                .addBodyParameter("religion", preferred_Religion)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
//                       System.out.println(response);
                        try {

//                            setSubsDashboardData( username,  profileCompletionPerc);
                            JSONObject jsonObject = response.getJSONObject("basicData");
                            setSubsDashboardData( jsonObject);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SubscriberDashboardActivity2.this, CONSTANTS.no_data, Toast.LENGTH_SHORT).show();
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "Alert", CONSTANTS.unknown_err);
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        reTryMethod();
                    }
                });


        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity2.this);
        }


/*        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<SubscriberMainResponse> call = apiInterface.subscribeDashoard(token);
        call.enqueue(new Callback<SubscriberMainResponse>() {
            @Override
            public void onResponse(Call<SubscriberMainResponse> call, Response<SubscriberMainResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    SubscriberMainResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

//                            Toast.makeText(SubscriberDashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", serverResponse.getMessage().getSuccess());

                            SubscriberMainResponse mainResponse = response.body();
                            setSubsDashboardData(mainResponse);
                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", "Confuse");
                        }
                    }else {
                        Toast.makeText(SubscriberDashboardActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SubscriberMainResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SubscriberDashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });*/
    }

    public void callWebServiceForEmailVerification(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        EmailVerificationRequest emailVerirequest = new EmailVerificationRequest();
        emailVerirequest.token = token;


        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity2.this) == true){

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity2.this);
        Call<AddFolderResponse> call = apiInterface.emailVerification(emailVerirequest);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                try {
                    if (response.isSuccessful()) {

                        if (response.body().getMessage().getSuccess().equalsIgnoreCase("success")) {

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
                Toast.makeText(SubscriberDashboardActivity2.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity2.this);
        }
    }

    /** MOBILE */
    public void callWebServiceForResendOTP(){

//        String token = CONSTANTS.Own_Token;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);


        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity2.this) == true){

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity2.this);
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
                        if (serverResponse.getMessage().getSuccess().equalsIgnoreCase(msg1)) {
//                     if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("OTP is sent on your registered mobile number.")) {

//                            Toast.makeText(SubscriberDashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", serverResponse.getMessage().getSuccess());
                            showAlertMsg("Info", msg1);

                        }else {
                            showAlertMsg("Info", msg1);
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", msg1);
                        }
                    }else {
                        Toast.makeText(SubscriberDashboardActivity2.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SubscriberDashboardActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity2.this);
        }
    }

    public void callWebServiceForOTPVerification(String otp, AlertDialog dialog){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity2.this) == true){

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity2.this);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<AddFolderResponse> call = apiInterface.verifiyOTP(token, otp);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    AddFolderResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().equalsIgnoreCase("OTP is verified")) {

                            dialog.dismiss();
                            showAlertMsg("Alert", serverResponse.getMessage().getSuccess() );
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", serverResponse.getMessage().getSuccess());

                        } else {
                            Toast.makeText(SubscriberDashboardActivity2.this, CONSTANTS.unknown_err, Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SubscriberDashboardActivity2.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SubscriberDashboardActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity2.this);
        }
    }
    /** File Upload */
    public void callWebServiceForFileUpload(final File file)throws URISyntaxException {

        System.out.print(file);

        Call<IdVerificationResponse> callUpload = null;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
//        Toast.makeText(SubscriberDashboardActivity.this, "Method : "+typeOf, Toast.LENGTH_LONG).show();

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity2.this) == true){

        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(typeOf, file.getName(), requestBody);
//      MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("id_proof", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity2.this);
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
//                AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "ID", "Oops");
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity2.this);
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
//                Toast.makeText(SubscriberDashboardActivity.this, "Default Called", Toast.LENGTH_SHORT).show();
                return null;
            }

        }

    public void callApiForDocStatus(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity2.this) == true){

            AndroidNetworking.post("https://iitiimshaadi.com/api/status_report.json")
                    .addBodyParameter("token", token)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                            JSONObject verifiedObject = response.getJSONObject("verificationData");

                            String email_verified = verifiedObject.getString("emailStatus");
                            int mob_verified = verifiedObject.getInt("mobileStatus");
                                int biodata_verified = verifiedObject.getInt("biodata_status");
                                int doc_verified = verifiedObject.getInt("document_verified");
                                int idProof_verified = verifiedObject.getInt("identity_proof_verified");

                                boolean email = email_verified.equalsIgnoreCase("Yes");
                                boolean mob = mob_verified == 1;
                                boolean bioData = biodata_verified == 1;
                                boolean doc = doc_verified == 1;
                                boolean idProof = idProof_verified == 1;

                                setVerificationStatus(email, mob, bioData, doc, idProof);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
//                            Toast.makeText(SubscriberDashboardActivity.this, "Verification Failed!", Toast.LENGTH_LONG).show();

                        }
                    });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity2.this);
        }

        }

    public void setVerificationStatus(boolean email, boolean mob, boolean bioData, boolean doc, boolean idProof){


            if(email == true){
                mEmailVerifyTV.setText("Email Verified");
                mEmailVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
                mEmailVerifyTV.setEnabled(false);
            }else if (email == false){
                mEmailVerifyTV.setText("Email Unverified");
                mEmailVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
                mEmailVerifyTV.setEnabled(true);
            }

            if(mob == true){
                mMobVerifyTV.setText("Mobile Verified");
                mMobVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
                mMobVerifyTV.setEnabled(false);
            }else if (mob == false){
                mMobVerifyTV.setText("Mobile Unverified");
                mMobVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
                mMobVerifyTV.setEnabled(true);
            }

            if(doc == true){
                mDocumentsVerifyTV.setText("Doc Verified");
                mDocumentsVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
                mDocumentsVerifyTV.setEnabled(false);
            }else if (doc == false){
                mDocumentsVerifyTV.setText("Doc Unverified");
                mDocumentsVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
                mDocumentsVerifyTV.setEnabled(true);
            }

            if(idProof == true){
                mProofVerifyTV.setText("ID Proof Verified");
                mProofVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
                mProofVerifyTV.setEnabled(false);
            }else if (idProof == false){
                mProofVerifyTV.setText("ID Proof Unverified");
                mProofVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
                mProofVerifyTV.setEnabled(true);
            }

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
        new AlertDialog.Builder(SubscriberDashboardActivity2.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    public void reTryMethod(){

        new AlertDialog.Builder(SubscriberDashboardActivity2.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert")
                .setMessage("Something went wrong!\n Please Try Again!")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        callWebServiceForSubscribeDashboard();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /* Subscriber Dashboard*/

    private void callWebServiceMyProfile(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        final List<String> countryList = new ArrayList<>();
        countryList.clear();

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity2.this) == true){

            ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity2.this);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<MyProfileResponse> call = apiInterface.myProfileData(token);
            call.enqueue(new Callback<MyProfileResponse>() {
                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {

                        if(response.body() != null){
                            setMyProfile(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(SubscriberDashboardActivity2.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity2.this);
        }
    }

    private void setMyProfile(MyProfileResponse myProfileResponse){

//        listAdapter = new ExpListViewSubsAdapter(this,listDataHeader,listDataChild, myProfileResponse);
//        expListView.setAdapter(listAdapter);

        partnerlistAdapter = new ExpListViewSubsPartnerAdapter(this,listDataHeaderPartner,listDataChildPartner, myProfileResponse);
        expListViewPartner.setAdapter(partnerlistAdapter);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit!")
                .setMessage("Are you sure?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        SubscriberDashboardActivity2.super.onBackPressed();
                    }
                }).create().show();
    }

}