package com.senzecit.iitiimshaadi.navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import com.senzecit.iitiimshaadi.model.exp_listview.ExpOwnProfileModel;
import com.senzecit.iitiimshaadi.model.exp_listview.ExpPartnerProfileModel;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.AboutUsActivity;
import com.senzecit.iitiimshaadi.viewController.ChatMessagesActivity;
import com.senzecit.iitiimshaadi.viewController.ContactUsActivity;
import com.senzecit.iitiimshaadi.viewController.DisclaimerActivity;
import com.senzecit.iitiimshaadi.viewController.FAQActivity;
import com.senzecit.iitiimshaadi.viewController.FriendsActivity;
import com.senzecit.iitiimshaadi.viewController.HowToNavigatePageActivity;
import com.senzecit.iitiimshaadi.viewController.MediaCoverageActivity;
import com.senzecit.iitiimshaadi.viewController.NotificationsActivity;
import com.senzecit.iitiimshaadi.viewController.PrivacyActivity;
import com.senzecit.iitiimshaadi.viewController.ProfileActivity;
import com.senzecit.iitiimshaadi.viewController.SettingsActivity;
import com.senzecit.iitiimshaadi.viewController.SplashActivity;
import com.senzecit.iitiimshaadi.viewController.SuccessStoriesActivity;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;

public class PaidBaseActivity extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawer;
    FrameLayout frameLayout;
    AppPrefs prefs;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_paid_base, null);

        FrameLayout activityContainer = drawer.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        //getLayoutInflater().inflate(R.layout.activity_home, activityContainer, true);
        super.setContentView(drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);

        prefs = new AppPrefs(PaidBaseActivity.this);

        final ToggleButton rightToggle = findViewById(R.id.right_menu_toggle);
        ImageView mChatIV = findViewById(R.id.idChat);
        ImageView mMyFriendIV = findViewById(R.id.idMyFriends);
        ImageView mNotificationIV = findViewById(R.id.idNotification);

        mChatIV.setOnClickListener(this);
        mMyFriendIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
//        Button btn1 = (Button) findViewById(R.id.idNavHome);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.activity_content);

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
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.requestLayout();
        View headerview = navigationView.getHeaderView(0);

        TextView mAboutBtn = headerview.findViewById(R.id.idAboutNav);
        TextView mProfileBtn = headerview.findViewById(R.id.idMyProfilePaidNav);
        TextView mMediaCovBtn = headerview.findViewById(R.id.idMediaCoverageNav);
        TextView mSuccessStoryBtn = headerview.findViewById(R.id.idSuccessStoryNav);
        TextView mHowToNavBtn = headerview.findViewById(R.id.idHowToNav);
        TextView mPrivacyPolicyBtn = headerview.findViewById(R.id.idPrivacyPolicyNav);
        TextView mContactUsBtn = headerview.findViewById(R.id.idContactUsNav);
        TextView mFaqBtn = headerview.findViewById(R.id.idFAQNav);
        TextView mDisclaimerBtn = headerview.findViewById(R.id.idDisclaimerNav);
        TextView mSetting = headerview.findViewById(R.id.idSettingNav);
        TextView mLogout = headerview.findViewById(R.id.idLogoutNav);


        mAboutBtn.setOnClickListener(PaidBaseActivity.this);
        mProfileBtn.setOnClickListener(PaidBaseActivity.this);
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

        drawer.closeDrawer(Gravity.RIGHT);
        drawer.postDelayed(new Runnable() {
            @Override
            public void run() {

                switch (view.getId()) {

                    case R.id.idAboutNav: {

                        // Toast.makeText(getApplicationContext(), "About", // Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PaidBaseActivity.this, AboutUsActivity.class));
                        break;
                    }
                    case R.id.idMyProfilePaidNav: {

                        // Toast.makeText(getApplicationContext(), "About", // Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PaidBaseActivity.this, ProfileActivity.class));
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
                        startActivity(new Intent(PaidBaseActivity.this, DisclaimerActivity.class));
                        break;
                    }
                    case R.id.idSettingNav: {
                        // Toast.makeText(getApplicationContext(), "Settings ", // Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PaidBaseActivity.this, SettingsActivity.class));
                        break;

                    }
                    case R.id.idChat:
                        if(prefs.getInt(CONSTANTPREF.CHAT_USER_COUNT) == 0){
                            AlertDialogSingleClick.getInstance().showDialog(PaidBaseActivity.this, "Alert", AppMessage.NO_CHAT_USER);
                        }else {
                            startActivity(new Intent(PaidBaseActivity.this, ChatMessagesActivity.class));
                        }
                    break;
                    case R.id.idMyFriends:
                        startActivity(new Intent(PaidBaseActivity.this, FriendsActivity.class));
                        break;
                    case R.id.idNotification:
//                AlertDialogSingleClick.getInstance().showDialog(PaidBaseActivity.this, "Notification Alert", "There is no new notification");
                        Navigator.getClassInstance().navigateToActivity(PaidBaseActivity.this, NotificationsActivity.class);
                        break;
                    case R.id.idLogoutNav: {
                        // Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();


                        new AlertDialog.Builder(PaidBaseActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Logout Alert")
                                .setMessage("Are you sure?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        AppPrefs prefs = AppController.getInstance().getPrefs();
                                        prefs.remove(CONSTANTS.LOGGED_TOKEN);
                                        prefs.remove(CONSTANTS.LOGGED_USERNAME);
                                        prefs.remove(CONSTANTS.LOGGED_USERID);
                                        prefs.remove(CONSTANTS.LOGGED_USER_TYPE);
                                        prefs.remove(CONSTANTS.LOGGED_USER_PIC);
                                        prefs.remove(CONSTANTS.LOGGED_EMAIL);
                                        prefs.remove(CONSTANTS.LOGGED_MOB);
                                        prefs.remove(CONSTANTS.OTHER_USERID);
                                        prefs.remove(CONSTANTPREF.PROGRESS_STATUS_FOR_TAB);
                                        prefs.remove(CONSTANTPREF.LOGIN_USERNAME);
                                        prefs.remove(CONSTANTPREF.LOGIN_PASSWORD);
                                        prefs.remove(CONSTANTPREF.CHAT_USER_COUNT);


                                        ExpOwnProfileModel.getInstance().resetModel();
                                        ExpPartnerProfileModel.getInstance().resetModel();
                                        Intent intent = new Intent(PaidBaseActivity.this, SplashActivity.class);

                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();


                                    }
                                })
                                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                })
                                .show();

                        break;
                    }

                }
        }
    }, 200);
    }

}
