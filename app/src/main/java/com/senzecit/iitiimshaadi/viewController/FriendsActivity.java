package com.senzecit.iitiimshaadi.viewController;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.fragment.InvitedFriendFragment;
import com.senzecit.iitiimshaadi.fragment.MyFriendsFragment;
import com.senzecit.iitiimshaadi.fragment.RequestedFriendFragment;
import com.senzecit.iitiimshaadi.fragment.ShortlistedFriendFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsActivity extends AppCompatActivity implements View.OnClickListener, MyFriendsFragment.OnMyFriendListener,
InvitedFriendFragment.OnInvitedFriendListener, ShortlistedFriendFragment.OnShortlistedFriendListener,
RequestedFriendFragment.OnRequestedFriendListener{

    private static final String TAG = FriendsActivity.class.getSimpleName();
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    APIInterface apiInterface;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_friends);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

        init();
        mBack.setOnClickListener(this);

    }

    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Friends");

        mTabLayout = findViewById(R.id.tab);
        mViewPager = findViewById(R.id.viewPager);
        setupViewPager(mViewPager);

        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(0);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyFriendsFragment(), "My Friends (0)");
        adapter.addFragment(new InvitedFriendFragment(), "Invited Friends (0)");
        adapter.addFragment(new RequestedFriendFragment(), "Requests Received (0)");
        adapter.addFragment(new ShortlistedFriendFragment(), "Shortlisted (0)");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                onBackNavigation();
                break;
        }
    }

    @Override
    public void onFragmentSetSetMyFriend(int size) {
//        mTabLayout.getTabAt(0).setText("My Friends ("+size+")");
        mTabLayout.getTabAt(0).setText(new StringBuffer("My Friends (").append(size).append(")"));

    }

    @Override
    public void onFragmentSetInvitedFriend(int size) {
//        mTabLayout.getTabAt(1).setText("Invited Friends ("+size+")");
        mTabLayout.getTabAt(1).setText(new StringBuffer("Invited Friends (").append(size).append(")"));
    }

    @Override
    public void onFragmentSetRequestedFriend(int size) {
        mTabLayout.getTabAt(2).setText(new StringBuffer("Request Received (").append(size).append(")"));
    }

    @Override
    public void onFragmentSetShortlistedFriend(int size) {
        mTabLayout.getTabAt(3).setText(new StringBuffer("Shortlisted Friends (").append(size).append(")"));
    }


//    FREND MANIPULATION

    @Override
    public void onFragmentAddFriend(String typeOf, String otherUserId) {
        callWebServiceForManipulateFriend( typeOf, otherUserId);
    }

    @Override
    public void onFragmentCancelReq(String typeOf, String otherUserId) {

        callWebServiceForManipulateFriend( typeOf, otherUserId);
    }

    @Override
    public void onFragmentShortListFriend(String typeOf, String otherUserId) {
        callWebServiceForManipulateFriend( typeOf, otherUserId);
    }

    @Override
    public void onFragmentUnShortListFriend(String typeOf, String otherUserId) {
        callWebServiceForManipulateFriend( typeOf, otherUserId);
    }


    @Override
    public void onFragmentRemoveFriend(String typeOf, String otherUserId) {
        callWebServiceForManipulateFriend( typeOf, otherUserId);
    }



/*
    @Override
    public void onFragmentSetRequestedFriend(ArrayList<String> requestAL) {
        arrListData = requestAL;
        mTabLayout.getTabAt(2).setText("Requested Friends ("+arrListData.size()+")");
    }*/

    /** API - Friend Manipulation Task */
    private void callWebServiceForManipulateFriend(String typeOf, String otherUserID)
    {
        if(NetworkClass.getInstance().checkInternet(FriendsActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(FriendsActivity.this);
        Call<AddFolderResponse> call = callManipulationMethod(typeOf, otherUserID);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                try {
                    if (response.isSuccessful()) {
                        if(response.body().getMessage().getSuccess().length() > 0){
                            String msg = response.body().getMessage().getSuccess();
                            AlertDialogSingleClick.getInstance().showDialog(FriendsActivity.this, "Friend Alert", msg);
                        }
                    }
                }catch (NullPointerException npe)
                {
                    Log.e(TAG, "Error : "+npe, npe);
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(FriendsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(FriendsActivity.this);
        }
    }
    public Call<AddFolderResponse> callManipulationMethod(String typeOf, String friend_user)
    {
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(typeOf.equalsIgnoreCase(UserDefinedKeyword.ADD.toString())){
            return apiInterface.serviceAddAsFriend(token, friend_user);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.REMOVE.toString())){
            return apiInterface.serviceRemoveFriend(token, friend_user);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.CANCEL.toString())){
            return apiInterface.serviceCancelFriend(token, friend_user);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.SHORTLIST.toString())){
            return apiInterface.serviceShortlistFriend(token, friend_user);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.UNSHORTLIST.toString())){
            return apiInterface.serviceUnShortlistFriend(token, friend_user);
        }else {
//            Toast.makeText(FriendsActivity.this, "Default Called", Toast.LENGTH_SHORT).show();
            return null;
        }

    }
    /** --- */

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackNavigation();
    }

    public void onBackNavigation(){

        try{
            String userType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
            if (userType.equalsIgnoreCase("paid_subscriber_viewer")) {

                Intent intent = new Intent(this, PaidSubscriberDashboardActivity.class);
                startActivity(intent);
            } else if (userType.equalsIgnoreCase("subscriber_viewer")) {

                Intent intent = new Intent(this, SubscriberDashboardActivity.class);
                startActivity(intent);
            } else if (userType.equalsIgnoreCase("subscriber")) {

                Intent intent = new Intent(this, SubscriberDashboardActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, IntroSliderWebActivity.class);
                startActivity(intent);
            }
        }catch (NullPointerException npe){

            Log.e("TAG", "#Error : "+npe, npe);
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();

        }
    }


}
