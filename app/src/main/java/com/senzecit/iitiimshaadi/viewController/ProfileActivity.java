package com.senzecit.iitiimshaadi.viewController;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ExpandableListViewAdapter;
import com.senzecit.iitiimshaadi.adapter.ExpandableListViewPartnerAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.my_profile.MyProfileResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    Button mMyProfile,mPartnerProfile;
    ExpandableListViewAdapter listAdapter;
    ExpandableListView expListView, expListViewPartner;
    ExpandableListViewPartnerAdapter partnerlistAdapter;
    List<String> listDataHeader,listDataHeaderPartner;
    HashMap<String, List<String>> listDataChild,listDataChildPartner;
    ScrollView mScrollView;
    boolean userProfile = true;
    boolean partnerPrefrence =true;
    AppPrefs prefs;

    CircleImageView mProfileCIV;
    TextView mUsrNameTV, mUsrIdTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        prefs = AppController.getInstance().getPrefs();

        init();
        mScrollView.smoothScrollTo(0,0);
        mScrollView.setFocusableInTouchMode(true);
        mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        mMyProfile.setOnClickListener(this);
        mPartnerProfile.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mTitle.setOnClickListener(this);

     /*   prepareListData();
        listAdapter = new ExpandableListViewAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        prepareListDataPartner();
        partnerlistAdapter = new ExpandableListViewPartnerAdapter(this, listDataHeaderPartner, listDataChildPartner);
        expListViewPartner.setAdapter(partnerlistAdapter);*/

        setProfileData();
        showMyProfile();

    }

    private void init(){

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Profile");

        mProfileCIV = (CircleImageView) findViewById(R.id.idProfileCIV) ;
        mUsrNameTV = (TextView)findViewById(R.id.idUserNameTV) ;
        mUsrIdTV = (TextView)findViewById(R.id.idUserId) ;

        mMyProfile = (Button) findViewById(R.id.myProfileBtn);
        mPartnerProfile = (Button) findViewById(R.id.partnerProfileBtn);
        expListView = (ExpandableListView) findViewById(R.id.expandableLV);
        expListViewPartner = (ExpandableListView) findViewById(R.id.expandablePartnerLV);

        mScrollView = (ScrollView) findViewById(R.id.scrollViewLayout);
    }


    public  void  setProfileData(){

        String profileUri = prefs.getString(Constants.LOGGED_USER_PIC);
        String userId = prefs.getString(Constants.LOGGED_USERID);
        String userName = prefs.getString(Constants.LOGGED_USERNAME);

        if(!TextUtils.isEmpty(profileUri)){
            Glide.with(ProfileActivity.this).load(profileUri).into(mProfileCIV);
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

    private void showMyProfile(){

        final List<String> countryList = new ArrayList<>();
        countryList.clear();

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<MyProfileResponse> call = apiInterface.myProfileData(Constants.Token_Paid);
        call.enqueue(new Callback<MyProfileResponse>() {
            @Override
            public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                if (response.isSuccessful()) {

                    if(response.body() != null){
                        setMyProfile(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setMyProfile(MyProfileResponse myProfileResponse){

        prepareListData();
        listAdapter = new ExpandableListViewAdapter(this, listDataHeader, listDataChild, myProfileResponse);
        expListView.setAdapter(listAdapter);

        prepareListDataPartner();
        partnerlistAdapter = new ExpandableListViewPartnerAdapter(this, listDataHeaderPartner, listDataChildPartner, myProfileResponse);
        expListViewPartner.setAdapter(partnerlistAdapter);

    }

}
