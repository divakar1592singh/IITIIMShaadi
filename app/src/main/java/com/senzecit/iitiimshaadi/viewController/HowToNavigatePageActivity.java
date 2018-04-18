package com.senzecit.iitiimshaadi.viewController;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.HowToNavigateAdapter;
import com.senzecit.iitiimshaadi.model.how_to_navigates.VideoModel;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;

public class HowToNavigatePageActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    AppPrefs prefs;

    ListView mYoutubeList;
    String[] VideoTitle = {"How to Register On IITIIMShaadi", "How to Search Partner", "How to send a friend request",
    "How to accept friend request", "How to Shortlist"};
    String[] imageThumb = {"thumb1", "thumb2", "thumb3", "thumb4", "thumb5"};
    ArrayList<VideoModel> videoArrList;
    VideoModel videoModel;
    HowToNavigateAdapter howToNavigateAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_navigate_page);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        prefs = AppPrefs.getInstance(this);

        mYoutubeList = findViewById(R.id.idYoutubeList);

        videoArrList = new ArrayList<>();

        for (int i = 0; i<imageThumb.length; i++){

            videoModel = new VideoModel(VideoTitle[i], imageThumb[i]);
            videoArrList.add(videoModel);
        }
        howToNavigateAdapter = new HowToNavigateAdapter(HowToNavigatePageActivity.this, videoArrList);
        mYoutubeList.setAdapter(howToNavigateAdapter);

        init();
    }

    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mTitle.setText("How to Navigate");
        mBack.setVisibility(View.VISIBLE);
        mBack.setOnClickListener(HowToNavigatePageActivity.this);
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
