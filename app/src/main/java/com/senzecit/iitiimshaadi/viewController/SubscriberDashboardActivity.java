package com.senzecit.iitiimshaadi.viewController;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ExpListViewSubscriberAdapter;
import com.senzecit.iitiimshaadi.adapter.ExpListViewSubscriberPartnerAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.email_verification.EmailVerificationResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.id_verification.IdVerificationResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.email_verification.EmailVerificationRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.id_verification.IdVerificationRequest;
import com.senzecit.iitiimshaadi.navigation.BaseActivity;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

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

public class SubscriberDashboardActivity extends BaseActivity {

    private static final String TAG = SubscriptionActivity.class.getSimpleName();

    ExpListViewSubscriberAdapter listAdapter, listAdapterPartner;
    ExpandableListView expListView, expListViewPartner;
    ExpListViewSubscriberPartnerAdapter partnerlistAdapter;
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
    /** Network*/
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_dashboard);

        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);

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
    }

    private void init(){
        mScrollView = (ScrollView)findViewById(R.id.idScrlView);

        mProfileCIV = (CircleImageView) findViewById(R.id.idProfileCIV) ;
        mUsrNameTV = (TextView)findViewById(R.id.idUserNameTV) ;
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
                Toast.makeText(SubscriberDashboardActivity.this, "Profile", Toast.LENGTH_LONG).show();
            }
        });
        mAlbumIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubscriberDashboardActivity.this, "Album", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SubscriberDashboardActivity.this, AlbumActivity.class));
            }
        });

        mEmailVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubscriberDashboardActivity.this, "Email", Toast.LENGTH_LONG).show();

                alertDialogEmail();
            }
        });
        mMobVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubscriberDashboardActivity.this, "Mobile", Toast.LENGTH_LONG).show();
                alertDialogMobile();
            }
        });
        mDocumentsVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubscriberDashboardActivity.this, "Documents", Toast.LENGTH_LONG).show();
                alertDialogDocuments();

            }
        });
        mProofVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubscriberDashboardActivity.this, "ID Proof", Toast.LENGTH_LONG).show();
                alertDialogIDProof();
            }
        });
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
        listDataHeader.add("Acquaintances");
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

//        List<String> video = new ArrayList<String>();
//        video.add("My Video");

        listDataChild.put(listDataHeader.get(0), basicsLifestyle); // Header, Child data
        listDataChild.put(listDataHeader.get(1), religiousBackgroung);
        listDataChild.put(listDataHeader.get(2), contactDetail);
        listDataChild.put(listDataHeader.get(3), familyDetails);
        listDataChild.put(listDataHeader.get(4), educationCareer);
        listDataChild.put(listDataHeader.get(5), aboutMe);
        listDataChild.put(listDataHeader.get(6), acquiantances);
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

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
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

        mMessage.setText("Verfication email sended. Check your mail and follow instruction");

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }
    private void alertDialogMobile(){

        final Button mConfirm,mLogin;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.popup_mobile_layout,null);

        mConfirm = dialogView.findViewById(R.id.confirmBtn);
        mLogin = dialogView.findViewById(R.id.loginFPLBtn);

       /* mConfirm.setBackground(getResources().getDrawable(R.drawable.button_shape_select_forgot));
        mConfirm.setTextColor(getResources().getColor(R.color.colorWhite));*/

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mConfirm.setBackground(getResources().getDrawable(R.drawable.button_shape_select_forgot));
                mConfirm.setTextColor(getResources().getColor(R.color.colorWhite));*/
                dialog.dismiss();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mLogin.setBackground(getResources().getDrawable(R.drawable.button_shape_select_forgot));
                mLogin.setTextColor(getResources().getColor(R.color.colorWhite));*/
                dialog.dismiss();

            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }
    private void alertDialogDocuments(){


        Button mConfirm,mLogin ;
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


        mConfirm = dialogView.findViewById(R.id.confirmBtn);
        mLogin = dialogView.findViewById(R.id.loginFPLBtn);

       /* mConfirm.setBackground(getResources().getDrawable(R.drawable.button_shape_select_forgot));
        mConfirm.setTextColor(getResources().getColor(R.color.colorWhite));*/
        mDocBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 1;
                showStorage();
            }
        });
        mDocBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 2;
                showStorage();
            }
        });
        mDocBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 3;
                showStorage();
            }
        });
        mDocBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 4;
                showStorage();
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mConfirm.setBackground(getResources().getDrawable(R.drawable.button_shape_select_forgot));
                mConfirm.setTextColor(getResources().getColor(R.color.colorWhite));*/
                dialog.dismiss();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mLogin.setBackground(getResources().getDrawable(R.drawable.button_shape_select_forgot));
                mLogin.setTextColor(getResources().getColor(R.color.colorWhite));*/
                dialog.dismiss();

            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }
    private void alertDialogIDProof(){

        final Button mBrowse, mCancel, mSubmt;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.popup_id_proof_layout,null);

        mBiodataTv= dialogView.findViewById(R.id.tvIDProof);
        mBrowse = dialogView.findViewById(R.id.idBiodataUpload);
        mCancel = dialogView.findViewById(R.id.idCancelBtn);
        mSubmt = dialogView.findViewById(R.id.idSubmitBtn);

       /* mConfirm.setBackground(getResources().getDrawable(R.drawable.button_shape_select_forgot));
        mConfirm.setTextColor(getResources().getColor(R.color.colorWhite));*/
        mBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStorage();
                btnChooserCount = 5;
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        mSubmt.setOnClickListener(new View.OnClickListener() {
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
            mDocBtn1.setText("Upload Now");
//            uploadCvFile(file);
        }else if (btnChooserCount == 2){
            tvDoc2.setText(getFileName(file.getPath()));
            mDocBtn2.setText("Upload Now");
//            uploadCvFile(file);
        }else if (btnChooserCount == 3){
            tvDoc3.setText(getFileName(file.getPath()));
            mDocBtn3.setText("Upload Now");
//            uploadCvFile(file);
        }else if (btnChooserCount == 4){
            tvDoc4.setText(getFileName(file.getPath()));
            mDocBtn4.setText("Upload Now");
//            uploadCvFile(file);
        }else if (btnChooserCount == 5){
            mBiodataTv.setText(getFileName(file.getPath()));
            callWebServiceForIDUpload(file);
        }

    }

    private String getFileName(String filePath){
        return filePath.substring(filePath.lastIndexOf("/")+1);
    }

    /** API INTEGRATION */

    public void callWebServiceForEmailVerification(){


        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);

        EmailVerificationRequest emailVerirequest = new EmailVerificationRequest();
        emailVerirequest.token = Constants.Temp_Token;
        emailVerirequest.email = "senzec1@gmail.com";

        Call<EmailVerificationResponse> call = apiInterface.emailVerification(emailVerirequest);
        call.enqueue(new Callback<EmailVerificationResponse>() {
            @Override
            public void onResponse(Call<EmailVerificationResponse> call, Response<EmailVerificationResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {

/*                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getResponseData() != null){
                            Toast.makeText(LoginActivity.this, "Succesfully", Toast.LENGTH_SHORT).show();
                            ResponseData responseData = response.body().getResponseData();
                            setPrefData(responseData);
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                    }*/

                }
            }

            @Override
            public void onFailure(Call<EmailVerificationResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SubscriberDashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }
    public void callWebServiceForIDVerification() {

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);

      /*  IdVerificationRequest requestss = new IdVerificationRequest();
        request.token = Constants.Temp_Token;
        request.id_proof = "senzec1@gmail.com";


        Call<EmailVerificationResponse> call = apiInterface.emailVerification(requestss);
        call.enqueue(new Callback<EmailVerificationResponse>() {
            @Override
            public void onResponse(Call<EmailVerificationResponse> call, Response<EmailVerificationResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {

*//*                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getResponseData() != null){
                            Toast.makeText(LoginActivity.this, "Succesfully", Toast.LENGTH_SHORT).show();
                            ResponseData responseData = response.body().getResponseData();
                            setPrefData(responseData);
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                    }*//*

                }
            }

            @Override
            public void onFailure(Call<EmailVerificationResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SubscriberDashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });*/
    }

    /* File Upload */
    public void callWebServiceForIDUpload(final File file)throws URISyntaxException{

        /*String fullPath = Commons.getPath(selectedImageUri, this);
        File file = new File(fullPath);*/


        Call<IdVerificationResponse> callUpload = null;

        IdVerificationRequest idVerificationRequest = new IdVerificationRequest();
        idVerificationRequest.token = Constants.Temp_Token;

        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("id_proof", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());
//RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ProgressClass.getProgressInstance().showDialog(SubscriberDashboardActivity.this);
        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        callUpload = apiInterface.idVerification(Constants.Temp_Token, fileToUpload, filename);

        callUpload.enqueue(new Callback<IdVerificationResponse>() {
            @Override
            public void onResponse(Call<IdVerificationResponse> call, Response<IdVerificationResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if(response.isSuccessful()){

                    AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "ID", "Response :"+response.body().getMessage().getSuccess() );
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "ID", "Confuse");
                }

            }

            @Override
            public void onFailure(Call<IdVerificationResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "ID", "Oops");
            }
        });

    }

}