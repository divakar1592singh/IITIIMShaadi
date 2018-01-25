package com.senzecit.iitiimshaadi.navigation_paid;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.activity.AboutUsActivity;
import com.senzecit.iitiimshaadi.activity.ChatMessagesActivity;
import com.senzecit.iitiimshaadi.activity.ContactUsActivity;
import com.senzecit.iitiimshaadi.activity.DisclaimerActivity;
import com.senzecit.iitiimshaadi.activity.FAQActivity;
import com.senzecit.iitiimshaadi.activity.FriendsActivity;
import com.senzecit.iitiimshaadi.activity.HowToNavigatePageActivity;
import com.senzecit.iitiimshaadi.activity.YoutubeActivity;
import com.senzecit.iitiimshaadi.activity.MediaCoverageActivity;
import com.senzecit.iitiimshaadi.activity.PrivacyActivity;
import com.senzecit.iitiimshaadi.activity.SettingsActivity;
import com.senzecit.iitiimshaadi.activity.SuccessStoriesActivity;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;

public class PaidBaseActivity extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawer;
    FrameLayout frameLayout;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_paid_base, null);

        FrameLayout activityContainer = (FrameLayout) drawer.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        //getLayoutInflater().inflate(R.layout.activity_home, activityContainer, true);
        super.setContentView(drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        final ToggleButton rightToggle = (ToggleButton)findViewById(R.id.right_menu_toggle);
        ImageView mChatIV = (ImageView)findViewById(R.id.idChat);
        ImageView mMyFriendIV = (ImageView)findViewById(R.id.idMyFriends);
        ImageView mNotificationIV = (ImageView)findViewById(R.id.idNotification);

        mChatIV.setOnClickListener(this);
        mMyFriendIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
//        Button btn1 = (Button) findViewById(R.id.idNavHome);
        setSupportActionBar(toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.activity_content);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);



        rightToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rightToggle.isChecked())
                {
                    drawer.openDrawer(Gravity.RIGHT);
                    drawer.bringToFront();
                    drawer.requestLayout();
                }else{
                    drawer.closeDrawer(Gravity.RIGHT);
                }
            }
        });


        // SIDE NAV
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.requestLayout();
        View headerview = navigationView.getHeaderView(0);

        TextView mAboutBtn = (TextView)headerview.findViewById(R.id.idAboutNav);
        TextView mMediaCovBtn = (TextView)headerview.findViewById(R.id.idMediaCoverageNav);
        TextView mSuccessStoryBtn = (TextView)headerview.findViewById(R.id.idSuccessStoryNav);
        TextView mHowToNavBtn = (TextView)headerview.findViewById(R.id.idHowToNav);
        TextView mPrivacyPolicyBtn = (TextView)headerview.findViewById(R.id.idPrivacyPolicyNav);
        TextView mContactUsBtn = (TextView)headerview.findViewById(R.id.idContactUsNav);
        TextView mFaqBtn = (TextView)headerview.findViewById(R.id.idFAQNav);
        TextView mDisclaimerBtn = (TextView)headerview.findViewById(R.id.idDisclaimerNav);
        TextView mSetting = (TextView)headerview.findViewById(R.id.idSettingNav);
        TextView mLogout = (TextView)headerview.findViewById(R.id.idLogoutNav);


        mAboutBtn.setOnClickListener(PaidBaseActivity.this);
        mMediaCovBtn.setOnClickListener(PaidBaseActivity.this);
        mSuccessStoryBtn.setOnClickListener(PaidBaseActivity.this);
        mHowToNavBtn.setOnClickListener(PaidBaseActivity.this);
        mPrivacyPolicyBtn.setOnClickListener(PaidBaseActivity.this);
        mContactUsBtn.setOnClickListener(PaidBaseActivity.this);
        mFaqBtn.setOnClickListener(PaidBaseActivity.this);
        mDisclaimerBtn.setOnClickListener(PaidBaseActivity.this);
        mSetting.setOnClickListener(PaidBaseActivity.this);
        mLogout.setOnClickListener(PaidBaseActivity.this);


        // SET DEFAULT COLOR
   /*     homeBtn.setTextColor(Color.parseColor("#000000"));
        newsFeedBtn.setTextColor(Color.parseColor("#000000"));
        calculatorBtn.setTextColor(Color.parseColor("#000000"));
        homeBtn.setTextColor(Color.parseColor("#000000"));*/


        if(layoutResID == R.layout.activity_subscriber_dashboard)
        {
//            homeBtn.setTextColor(Color.parseColor("#66d9ff"));
        }/*else if(layoutResID == R.layout.activity_newsfeed)
        {
            newsFeedBtn.setTextColor(Color.parseColor("#66d9ff"));
        }else if(layoutResID == R.layout.activity_calculator)
        {
            calculatorBtn.setTextColor(Color.parseColor("#66d9ff"));
        }else if(layoutResID == R.layout.activity_notification)
        {
            notificationBtn.setTextColor(Color.parseColor("#66d9ff"));
        }
*/
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.idAboutNav: {

                // Toast.makeText(getApplicationContext(), "About", // Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaidBaseActivity.this, AboutUsActivity.class));
                break;
            }
            case R.id.idMediaCoverageNav: {
                // Toast.makeText(getApplicationContext(), "Media Coverage", // Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaidBaseActivity.this, MediaCoverageActivity.class));
                break;
            }
            case R.id.idSuccessStoryNav: {
                // Toast.makeText(getApplicationContext(), "Success Story", // Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaidBaseActivity.this, SuccessStoriesActivity.class));
                break;
            }
            case R.id.idHowToNav: {
                // Toast.makeText(getApplicationContext(), "How To Navigate", // Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaidBaseActivity.this, HowToNavigatePageActivity.class));
                break;
            }
            case R.id.idPrivacyPolicyNav: {
                // Toast.makeText(getApplicationContext(), "Privacy Policy", // Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaidBaseActivity.this, PrivacyActivity.class));
                break;
            }
            case R.id.idContactUsNav: {
                // Toast.makeText(getApplicationContext(), "Contact Us", // Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaidBaseActivity.this, ContactUsActivity.class));
                break;
            }
            case R.id.idFAQNav: {
                // Toast.makeText(getApplicationContext(), "FAQ", // Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaidBaseActivity.this, FAQActivity.class));
                break;
            }
            case R.id.idDisclaimerNav: {
                // Toast.makeText(getApplicationContext(), "FAQ", // Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaidBaseActivity.this,DisclaimerActivity.class));
                break;
            }
            case R.id.idSettingNav: {
                // Toast.makeText(getApplicationContext(), "Settings ", // Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaidBaseActivity.this, SettingsActivity.class));
                break;

            }
            case R.id.idLogoutNav: {
                // Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.idChat:
                startActivity(new Intent(PaidBaseActivity.this, ChatMessagesActivity.class));
                break;
            case R.id.idMyFriends:
                startActivity(new Intent(PaidBaseActivity.this, FriendsActivity.class));
                break;
            case R.id.idNotification:
                AlertDialogSingleClick.getInstance().showDialog(PaidBaseActivity.this, "Notification Alert", "There is no new notification");


                break;

        }
    }

}
