package com.senzecit.iitiimshaadi.viewController;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.OtherExpListAdapter;
import com.senzecit.iitiimshaadi.adapter.OtherExpListPartnerAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.chat.SocketSingleChatActivity;
import com.senzecit.iitiimshaadi.model.api_response_model.other_profile.OtherProfileResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtherProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "OtherProfileActivity";
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    Button mMyProfile,mPartnerProfile;
    OtherExpListAdapter listAdapter;
    ExpandableListView expListView, expListViewPartner;
    OtherExpListPartnerAdapter partnerlistAdapter;
    List<String> listDataHeader,listDataHeaderPartner;
    HashMap<String, List<String>> listDataChild,listDataChildPartner;
    ScrollView mScrollView;
    boolean userProfile = true;
    boolean partnerPrefrence =true;
    AppPrefs prefs;
    CircleImageView mProfileCIV;
    TextView mUsrNameTV, mUsrIdTV;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        prefs = AppController.getInstance().getPrefs();


        init();
        mScrollView.smoothScrollTo(0,0);
        mScrollView.setFocusableInTouchMode(true);
        mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        mMyProfile.setOnClickListener(this);
        mPartnerProfile.setOnClickListener(this);
        mBack.setOnClickListener(this);
//        mTitle.setOnClickListener(this);

        String userId = prefs.getString(CONSTANTS.OTHER_USERID);
        callWebServiceForOtherProfile(userId);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.album_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.idChat:

                Navigator.getClassInstance().navigateToActivity(OtherProfileActivity.this, SocketSingleChatActivity.class);
//                Toast.makeText(OtherProfileActivity.this, "Album", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.idAlbum:

                Navigator.getClassInstance().navigateToActivity(OtherProfileActivity.this, OtherAlbumActivity.class);
//                Toast.makeText(OtherProfileActivity.this, "Album", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void init(){

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("");

        mProfileCIV = (CircleImageView) findViewById(R.id.idProfileCIV) ;
        mUsrNameTV = (TextView)findViewById(R.id.idUserNameTV) ;
        mUsrIdTV = (TextView)findViewById(R.id.idUserId) ;

        mMyProfile = (Button) findViewById(R.id.myProfileBtn);
        mPartnerProfile = (Button) findViewById(R.id.partnerProfileBtn);
        expListView = (ExpandableListView) findViewById(R.id.expandableLV);
        expListViewPartner = (ExpandableListView) findViewById(R.id.expandablePartnerLV);

        mScrollView = (ScrollView) findViewById(R.id.scrollViewLayout);

        setSupportActionBar(mToolbar);
    }

    public void setProfileData(OtherProfileResponse profileResponse){

        try {
            String userId = String.valueOf(profileResponse.getBasicData().getUserId());
            String profileUri = CONSTANTS.IMAGE_AVATAR_URL + userId + "/" + profileResponse.getBasicData().getProfileImage();
            String userName = profileResponse.getBasicData().getName();

            if (!TextUtils.isEmpty(profileUri)) {
                Glide.with(OtherProfileActivity.this).load(profileUri).error(R.drawable.profile_img1).into(mProfileCIV);
            }

            String[] usernameAr = userName.split("\\s");
//            mTitle.setText(new StringBuilder("").append(usernameAr[0]));
            mUsrNameTV.setText(new StringBuilder("@").append(userName));
            mUsrIdTV.setText(new StringBuilder("@").append(userId));

            prefs.putString(CONSTANTS.OTHER_USERNAME, usernameAr[0]);

        }catch (IllegalArgumentException e){
            Log.e(TAG, " #Error :  "+e, e);
        }catch (NullPointerException npe){
            Log.e(TAG, " #Error :  "+npe, npe);
        }catch (IndexOutOfBoundsException ioe){
            Log.e(TAG, " #Error :  "+ioe, ioe);
        }
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
                OtherProfileActivity.this.finish();
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
        basicsLifestyle.add("Height");
        basicsLifestyle.add("Interests");

//        Religious Background
        List<String> religiousBackgroung = new ArrayList<String>();
        religiousBackgroung.add("Religion");
        religiousBackgroung.add("Caste");
        religiousBackgroung.add("Mother Tongue");

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

        List<String> familyDetails = new ArrayList<String>();
        familyDetails.add("Father's Name");
        familyDetails.add("Father's Occupation");
        familyDetails.add("Mother's Name");
        familyDetails.add("Mother's Occupation");
        familyDetails.add("Details on Sisters");
        familyDetails.add("Details on Brothers");

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

        List<String> aboutMe = new ArrayList<String>();
        aboutMe.add("About you");


        listDataChild.put(listDataHeader.get(0), basicsLifestyle); // Header, Child data
        listDataChild.put(listDataHeader.get(1), religiousBackgroung);
        listDataChild.put(listDataHeader.get(2), contactDetail);
        listDataChild.put(listDataHeader.get(3), familyDetails);
        listDataChild.put(listDataHeader.get(4), educationCareer);
        listDataChild.put(listDataHeader.get(5), aboutMe);

    }

    private void prepareListDataPartner() {
        listDataHeaderPartner = new ArrayList<String>();
        listDataChildPartner = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeaderPartner.add("Basics & Lifestyle");
        listDataHeaderPartner.add("Religious and Country Preference");
        listDataHeaderPartner.add("Education & Career");
        listDataHeaderPartner.add("Groom");

        // Adding child data
        List<String> basicsLifestyle = new ArrayList<String>();
        basicsLifestyle.add("Minimum Age");
        basicsLifestyle.add("Maximum Age");
        basicsLifestyle.add("Min Height");
        basicsLifestyle.add("Max Height");
        basicsLifestyle.add("Marital Status");

        List<String> religiousBackgroung = new ArrayList<String>();
        religiousBackgroung.add("Preferred Religion");
        religiousBackgroung.add("Preferred Caste");
        religiousBackgroung.add("Preferred Country");

        List<String> educationCareer = new ArrayList<String>();
        educationCareer.add("Preferred Education");

        List<String> aboutMe = new ArrayList<String>();
        aboutMe.add("Choice of Groom");

        listDataChildPartner.put(listDataHeaderPartner.get(0), basicsLifestyle); // Header, Child data
        listDataChildPartner.put(listDataHeaderPartner.get(1), religiousBackgroung);
        listDataChildPartner.put(listDataHeaderPartner.get(2), educationCareer);
        listDataChildPartner.put(listDataHeaderPartner.get(3), aboutMe);

    }

    private void setMyProfile(OtherProfileResponse profileResponse){

        prepareListData();
        listAdapter = new OtherExpListAdapter(this, listDataHeader, listDataChild, profileResponse);
        expListView.setAdapter(listAdapter);

        prepareListDataPartner();
        partnerlistAdapter = new OtherExpListPartnerAdapter(this, listDataHeaderPartner, listDataChildPartner, profileResponse);
        expListViewPartner.setAdapter(partnerlistAdapter);

    }

    /** API - other profile */
    private void callWebServiceForOtherProfile(String userId){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(OtherProfileActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(OtherProfileActivity.this);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<OtherProfileResponse> call = apiInterface.otherProfileData(token, userId);
        call.enqueue(new Callback<OtherProfileResponse>() {
            @Override
            public void onResponse(Call<OtherProfileResponse> call, Response<OtherProfileResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {

                    if(response.body() != null) {
                        if (response.body().getBasicData() != null)

                            setProfileData(response.body());
                        setMyProfile(response.body());

                    }else {



                    }
                }
            }

            @Override
            public void onFailure(Call<OtherProfileResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
//                Toast.makeText(OtherProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                reTryMethod();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(OtherProfileActivity.this);
        }

    }

    public void reTryMethod(){

        String title = "Alert";
        String msg = "Oops. Please Try Again! \n";

        dialog = new Dialog(OtherProfileActivity.this);
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
                finishActivity(0);
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = prefs.getString(CONSTANTS.OTHER_USERID);
                callWebServiceForOtherProfile(userId);
                dialog.dismiss();
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
