package com.senzecit.iitiimshaadi.viewController;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ExpListViewSubscriberAdapter;
import com.senzecit.iitiimshaadi.adapter.ExpListViewSubscriberPartnerAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
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
import java.net.UnknownHostException;
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

public class SubscriberDashboardActivity extends BaseNavActivity {

    private static final String TAG = SubscriptionActivity.class.getSimpleName();

    ExpListViewSubscriberAdapter listAdapter, listAdapterPartner;
    ExpListViewSubscriberPartnerAdapter partnerlistAdapter;
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
    private int lastExpandedPosition = -1;
//
    private int mShortAnimationDuration;
    private Animator mCurrentAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_dashboard);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

        init();
        handleClick();
        prepareListData();
        prepareListDataPartner();

        listAdapter = new ExpListViewSubscriberAdapter(this,listDataHeader,listDataChild);
        expListView.setAdapter(listAdapter);

        partnerlistAdapter = new ExpListViewSubscriberPartnerAdapter(this,listDataHeaderPartner,listDataChildPartner);
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

        mScrollView.smoothScrollTo(0, 0);
        prefs.putInt(CONSTANTPREF.PROGRESS_STATUS_FOR_TAB, 1);
    }


    private void init(){
        mScrollView = (ScrollView)findViewById(R.id.idScrlView);

        mProfileCIV = (CircleImageView) findViewById(R.id.idProfileCIV) ;
        mUsrNameTV = (TextView)findViewById(R.id.idUserNameTV) ;
        mUsrIdTV = (TextView)findViewById(R.id.idUserId) ;
        mProgress = (ProgressBar)findViewById(R.id.idprogress);
        mProfilepercTV = (TextView)findViewById(R.id.idProfilePercTV);

        mAlbumIV = (ImageView)findViewById(R.id.idAlbum);

        mEmailVerifyTV = (TextView)findViewById(R.id.idEmailVerify);
        mMobVerifyTV = (TextView)findViewById(R.id.idMobVerify);
        mDocumentsVerifyTV = (TextView)findViewById(R.id.idDocumentsVerify);
        mProofVerifyTV = (TextView)findViewById(R.id.idProofVerify);

        expListView = (ExpandableListView) findViewById(R.id.expandableLV);
        expListViewPartner = (ExpandableListView) findViewById(R.id.partnerPrefExpLV);
    }
    public void handleClick() {

        mProfileCIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SubscriberDashboardActivity.this, "Profile", Toast.LENGTH_LONG).show();
//                Navigator.getClassInstance().navigateToActivity(SubscriberDashboardActivity.this, ProfileActivity.class);
                String userId = prefs.getString(CONSTANTS.LOGGED_USERID);
                String profileUri = CONSTANTS.IMAGE_AVATAR_URL+userId+"/"+prefs.getString(CONSTANTS.LOGGED_USER_PIC);

                zoomImageFromThumb(mProfileCIV, profileUri);

            }
        });

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);


        mAlbumIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SubscriberDashboardActivity.this, "Album", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SubscriberDashboardActivity.this, AlbumActivity.class));
            }
        });

        mEmailVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SubscriberDashboardActivity.this, "Email", Toast.LENGTH_LONG).show();

                alertDialogEmail();
            }
        });
        mMobVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SubscriberDashboardActivity.this, "Mobile", Toast.LENGTH_LONG).show();
                alertDialogMobile();
            }
        });
        mDocumentsVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SubscriberDashboardActivity.this, "Documents", Toast.LENGTH_LONG).show();
                alertDialogDocuments();
            }
        });
        mProofVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SubscriberDashboardActivity.this, "ID Proof", Toast.LENGTH_LONG).show();
                alertDialogIDProof();
            }
        });
//        EXP
//        expListView, expListViewPartner
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                for (int g = 0; g < listAdapter.getGroupCount(); g++) {
                    if (g != groupPosition) {
                        expListView.collapseGroup(g);
                    }
                    setCollapseListViewHeight(expListView, g);
                }

            }
        });
/*

        expListViewPartner.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expListViewPartner.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
*/


//     Set Data
        setProfileData();
        callWebServiceForSubscribeDashboard();
    }

    public  void  setProfileData(){

        String userId = prefs.getString(CONSTANTS.LOGGED_USERID);
        String profileUri = CONSTANTS.IMAGE_AVATAR_URL+userId+"/"+prefs.getString(CONSTANTS.LOGGED_USER_PIC);
        String userName = prefs.getString(CONSTANTS.LOGGED_USERNAME);

        if(!TextUtils.isEmpty(profileUri)){
            Glide.with(SubscriberDashboardActivity.this).load(profileUri).error(R.drawable.profile_img1).into(mProfileCIV);
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
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
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
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
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
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
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
                     AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP", "OTP is empty");
                }else {
                    callWebServiceForOTPVerification(otp);
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

        final AlertDialog dialog = dialogBuilder.create();
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
                dialog.dismiss();
            }
        });


        dialog.setView(dialogView);
        dialog.show();
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

    public void setSubsDashboardData(String username, int profileCompletion){

        mProgress.setProgress(profileCompletion);
        mProfilepercTV.setText(new StringBuilder(String.valueOf(profileCompletion)).append("%")); ;
        mUsrNameTV.setText(new StringBuilder("@").append(username));
    }
    /** API INTEGRATION */

    /* Subscriber Dashboard*/
    public void callWebServiceForSubscribeDashboard(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);


        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
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
                            String username = response.getJSONObject("basicData").getString("name");
                            int profileCompletionPerc = response.getJSONObject("basicData").getInt("profile_complition");

                            setSubsDashboardData( username,  profileCompletionPerc);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        reTryMethod();
                    }
                });


        }else {
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity.this);
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

//        String token = CONSTANTS.Own_Token;
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

    public void callWebServiceForOTPVerification(String otp){

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
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("OTP is verified")) {

                            showAlertMsg("Alert", serverResponse.getMessage().getSuccess() );
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", serverResponse.getMessage().getSuccess());

                        } else {
                            Toast.makeText(SubscriberDashboardActivity.this, CONSTANTS.unknown_err, Toast.LENGTH_SHORT).show();
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

    /* File Upload */
    public void callWebServiceForFileUpload(final File file)throws URISyntaxException {

        System.out.print(file);

        Call<IdVerificationResponse> callUpload = null;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
//        Toast.makeText(SubscriberDashboardActivity.this, "Method : "+typeOf, Toast.LENGTH_LONG).show();

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity.this) == true){

        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(typeOf, file.getName(), requestBody);
//      MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("id_proof", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        callUpload = callManipulationMethod(fileToUpload, filename, token);

        callUpload.enqueue(new Callback<IdVerificationResponse>() {
            @Override
            public void onResponse(Call<IdVerificationResponse> call, Response<IdVerificationResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {

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
//                Toast.makeText(SubscriberDashboardActivity.this, "Default Called", Toast.LENGTH_SHORT).show();
                return null;
            }

        }

    public void callApiForDocStatus(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(SubscriberDashboardActivity.this) == true){

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

                                boolean email = email_verified.equalsIgnoreCase("Yes")?true:false;
                                boolean mob = mob_verified == 1?true:false;
                                boolean bioData = biodata_verified == 1?true:false;
                                boolean doc = doc_verified == 1?true:false;
                                boolean idProof = idProof_verified == 1?true:false;

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
            NetworkDialogHelper.getInstance().showDialog(SubscriberDashboardActivity.this);
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
        new AlertDialog.Builder(SubscriberDashboardActivity.this)
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

        new AlertDialog.Builder(SubscriberDashboardActivity.this)
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

//
private int onLayoutScrollByX = 0;
    private int onLayoutScrollByY = 0;

    public void planScrollBy(int x, int y) {
        onLayoutScrollByX += x;
        onLayoutScrollByY += y;
    }

 /*   @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.lay(changed, l, t, r, b);
        doPlannedScroll();
    }*/

    public void doPlannedScroll() {
        if (onLayoutScrollByX != 0 || onLayoutScrollByY != 0) {
            planScrollBy(onLayoutScrollByX, onLayoutScrollByY);
            onLayoutScrollByX = 0;
            onLayoutScrollByY = 0;
        }
    }

    //IMAGEZOOM
    private void zoomImageFromThumb(final View thumbView, String imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
//        expandedImageView.setImageResource(imageResId);

        try {
            ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
            Glide.with(SubscriberDashboardActivity.this).load(imageResId)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            ProgressClass.getProgressInstance().stopProgress();
                            if(e instanceof UnknownHostException)
//                                progressBar.setVisibility(View.VISIBLE);
//                                ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
                                ProgressClass.getProgressInstance().stopProgress();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                            ProgressClass.getProgressInstance().stopProgress();
                            return false;
                        }
                    })
                    .error(R.drawable.profile_img1)
                    .into(expandedImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }


}