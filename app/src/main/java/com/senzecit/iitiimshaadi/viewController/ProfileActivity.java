package com.senzecit.iitiimshaadi.viewController;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ExpandableListViewAdapter;
import com.senzecit.iitiimshaadi.adapter.ExpandableListViewPartnerAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.my_profile.MyProfileResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.DataHandlingClass;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ProfileActivity.class.getSimpleName();
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    Button mMyProfile,mPartnerProfile;
    ImageView mUploadIv;
    ExpandableListViewAdapter listAdapter;
    ExpandableListView expListView, expListViewPartner;
    ExpandableListViewPartnerAdapter partnerlistAdapter;
    List<String> listDataHeader,listDataHeaderPartner;
    HashMap<String, List<String>> listDataChild,listDataChildPartner;
    ScrollView mScrollView;
    AppPrefs prefs;

    int lastExpandedPosition = -1;
    int lastExpandedPositionPT = -1;

    CircleImageView mProfileCIV;
    TextView mUsrNameTV, mUsrIdTV;
    APIInterface apiInterface;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

        init();
        handleView();

        setProfileData();
        callWebServiceMyProfile();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                callWebServiceMyProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/


    private void init(){

        mToolbar= (Toolbar) findViewById(R.id.toolbar);

        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Profile");

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.idRefreshLayout);

        mProfileCIV = (CircleImageView) findViewById(R.id.idProfileCIV) ;
        mUploadIv = (ImageView)findViewById(R.id.idUploadBtn);
        mUsrNameTV = (TextView)findViewById(R.id.idUserNameTV) ;
        mUsrIdTV = (TextView)findViewById(R.id.idUserId) ;

        mMyProfile = (Button) findViewById(R.id.myProfileBtn);
        mPartnerProfile = (Button) findViewById(R.id.partnerProfileBtn);
        expListView = (ExpandableListView) findViewById(R.id.expandableLV);
        expListViewPartner = (ExpandableListView) findViewById(R.id.expandablePartnerLV);

        mScrollView = (ScrollView) findViewById(R.id.scrollViewLayout);
    }

    public void handleView(){

        setSupportActionBar(mToolbar);
        Glide.with(ProfileActivity.this).load(DataHandlingClass.getInstance().getProfilePicName(ProfileActivity.this)).into(mProfileCIV);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callWebServiceMyProfileRefresh();
            }
        });

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

        expListViewPartner.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPositionPT != -1
                        && groupPosition != lastExpandedPositionPT) {
                    expListViewPartner.collapseGroup(lastExpandedPositionPT);
                }
                lastExpandedPositionPT = groupPosition;
                expListViewPartner.setSelectionFromTop(groupPosition, 0);
            }
        });


        mScrollView.smoothScrollTo(0,0);
        mScrollView.setFocusableInTouchMode(true);
        mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        mMyProfile.setOnClickListener(this);
        mPartnerProfile.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        mUploadIv.setOnClickListener(this::startCrop);

    }

    public  void  setProfileData(){

        String userId = prefs.getString(CONSTANTS.LOGGED_USERID);
        String profileUri = CONSTANTS.IMAGE_AVATAR_URL+userId+"/}"+prefs.getString(CONSTANTS.LOGGED_USER_PIC);
        String userName = prefs.getString(CONSTANTS.LOGGED_USERNAME);

        if(!TextUtils.isEmpty(profileUri)){
            Glide.with(ProfileActivity.this).load(profileUri).error(DataHandlingClass.getInstance().getProfilePicName(ProfileActivity.this)).into(mProfileCIV);
        }

        mUsrNameTV.setText(new StringBuilder("@").append(userName));
        mUsrIdTV.setText(new StringBuilder("@").append(userId));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myProfileBtn:
                mMyProfile.setBackground(getResources().getDrawable(R.drawable.button_shape_profile_select));
                mMyProfile.setTextColor(getResources().getColor(R.color.colorWhite));

                mPartnerProfile.setBackground(getResources().getDrawable(R.drawable.button_shape_profile_unselect));
                mPartnerProfile.setTextColor(getResources().getColor(R.color.colorGrey));

                expListView.setVisibility(View.VISIBLE);
                expListViewPartner.setVisibility(View.GONE);
                break;
            case R.id.partnerProfileBtn:
                mPartnerProfile.setBackground(getResources().getDrawable(R.drawable.button_shape_profile_select));
                mPartnerProfile.setTextColor(getResources().getColor(R.color.colorWhite));

                mMyProfile.setBackground(getResources().getDrawable(R.drawable.button_shape_profile_unselect));
                mMyProfile.setTextColor(getResources().getColor(R.color.colorGrey));


                expListView.setVisibility(View.GONE);
                expListViewPartner.setVisibility(View.VISIBLE);
                break;

            case R.id.backIV:
                ProfileActivity.this.finish();
                break;
            case R.id.toolbar_title:
//                startActivity(new Intent(ProfileActivity.this,SearchPartnerPaidActivity.class));
                break;
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
        educationCareer.add("Work Location");
        educationCareer.add("Annual Income");
        educationCareer.add("LinkdIn Url");
        educationCareer.add("Save Changes");

        List<String> aboutMe = new ArrayList<String>();
        aboutMe.add("Write something about you");
        aboutMe.add("Save Changes");



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

    private void callWebServiceMyProfile(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        final List<String> countryList = new ArrayList<>();
        countryList.clear();

        if(NetworkClass.getInstance().checkInternet(ProfileActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(ProfileActivity.this);
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
                Toast.makeText(ProfileActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(ProfileActivity.this);
        }
    }

    private void callWebServiceMyProfileRefresh(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        final List<String> countryList = new ArrayList<>();
        countryList.clear();

        if(NetworkClass.getInstance().checkInternet(ProfileActivity.this) == true){

            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<MyProfileResponse> call = apiInterface.myProfileData(token);
            call.enqueue(new Callback<MyProfileResponse>() {
                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {

                        if(response.body() != null){
                            setMyProfile(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                    call.cancel();
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(ProfileActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(ProfileActivity.this);
        }
    }


    private void setMyProfile(MyProfileResponse myProfileResponse){

        if(myProfileResponse.getBasicData() != null){
            String userId = String.valueOf(myProfileResponse.getEmailData().getId());
            String partUrl = myProfileResponse.getBasicData().getProfileImage();
            prefs.putString(CONSTANTS.LOGGED_USER_PIC, partUrl);
            Glide.with(ProfileActivity.this).load(CONSTANTS.IMAGE_AVATAR_URL+userId+"/"+partUrl).error(DataHandlingClass.getInstance().getProfilePicName(ProfileActivity.this)).into(mProfileCIV);

        }

        prepareListData();
        listAdapter = new ExpandableListViewAdapter(this, listDataHeader, listDataChild, myProfileResponse);
        expListView.setAdapter(listAdapter);

        prepareListDataPartner();
        partnerlistAdapter = new ExpandableListViewPartnerAdapter(this, listDataHeaderPartner, listDataChildPartner, myProfileResponse);
        expListViewPartner.setAdapter(partnerlistAdapter);

    }

    /** -----> IMAGE CROPPING */

    private void startCrop(View view){

        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    callWebServiceForFileUpload(resultUri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void callWebServiceForFileUpload(final Uri uri)throws URISyntaxException {

        String fullPath = Commons.getPath(uri, this);

        File   file = new File(fullPath);
        System.out.print(file);

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(ProfileActivity.this) == true){

        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file[]", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

        ProgressClass.getProgressInstance().showDialog(ProfileActivity.this);
        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<AddFolderResponse> callUpload = apiInterface.profileImageUpload(fileToUpload, filename, token);

        callUpload.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getMessage().getSuccess().equalsIgnoreCase("Your Profile picture has been saved.")) {

                            Toast.makeText(ProfileActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();
                            callWebServiceMyProfile();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Oops, Something went wrong!", Toast.LENGTH_LONG).show();
                        }
                    }catch (NullPointerException npe){
                        Log.e(TAG, "#Error : "+npe, npe);
                    }

//                    callWebServiceForAllAlbum();
                } else {
//                    AlertDialogSingleClick.getInstance().showDialog(ProfileActivity.this, "ID", "Confuse");
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                AlertDialogSingleClick.getInstance().showDialog(ProfileActivity.this, "Alert", "Oops, Something went wrong!");
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(ProfileActivity.this);
        }
    }


//    EXTRAS
    public void reTryMethod(){

        String title = "Alert";
        String msg = "Oops. Please Try Again! \n";

        final Dialog dialog = new Dialog(ProfileActivity.this);
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
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWebServiceMyProfile();
//                dialog.cancel();
            }
        });

        dialog.show();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

}
