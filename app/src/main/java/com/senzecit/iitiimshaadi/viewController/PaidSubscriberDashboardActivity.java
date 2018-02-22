package com.senzecit.iitiimshaadi.viewController;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_dashboard.AllInterestReceived;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_dashboard.PaidDashboardResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.PaidSubscriberResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.main.SubscriberMainResponse;
import com.senzecit.iitiimshaadi.navigation.PaidBaseActivity;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaidSubscriberDashboardActivity extends PaidBaseActivity {

    ScrollView mScrollView;
    CircleImageView mProfileCIV;
    ImageView mAlbumIV;
    TextView mUsrNameTV, mUsrIdTV, mProfilePercTV, mMyProfileTV, mProfileShowTV, mShowMessageTV;
    TextView mFriendsTV, mSearchPartnerTV, mPremServicesTV, mChatMessageTV, mSubscriptionTV, mCustomFolderTV, mWalletTV, mUploadVideoTV, mReferFrndTV;
    TextView mInterestReceivedTV, mChatReceivedTV;
    AppPrefs prefs;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_subscriber_dashboard);

        prefs = AppController.getInstance().getPrefs();

        initView();
        handleClick();
        callWebServiceForSubscribeDashboard();
    }

    public void initView(){

        mProgress = (ProgressBar)findViewById(R.id.idprogress);
        mProfileCIV = (CircleImageView)findViewById(R.id.idProfileCIV);
        mUsrNameTV = (TextView) findViewById(R.id.idUserNameTV);
        mUsrIdTV = (TextView) findViewById(R.id.idUserId);
        mProfilePercTV = (TextView)  findViewById(R.id.idProfilePercTV);
        mMyProfileTV = (TextView)findViewById(R.id.idMyProfileTV);

        mProfileShowTV = (TextView)findViewById(R.id.idProfileShowTV) ;
        mShowMessageTV = (TextView)findViewById(R.id.idShowMessageTV);

        mInterestReceivedTV = (TextView)findViewById(R.id.idInterestReceivedTV) ;
        mChatReceivedTV = (TextView)findViewById(R.id.idChatReceivedTV);

        mAlbumIV = (ImageView) findViewById(R.id.idAlbum);

        //BOTTOM
        mFriendsTV = (TextView)findViewById(R.id.idFriendsTV) ;
        mSearchPartnerTV = (TextView)findViewById(R.id.idSearchPartnerTV) ;
        mPremServicesTV = (TextView)findViewById(R.id.idPremServicesTV) ;
        mChatMessageTV = (TextView)findViewById(R.id.idChatMessageTV) ;
        mSubscriptionTV = (TextView)findViewById(R.id.idSubscriptionTV) ;
        mCustomFolderTV = (TextView)findViewById(R.id.idCustFolderTV) ;
        mWalletTV = (TextView)findViewById(R.id.idWalletTV) ;
        mUploadVideoTV = (TextView)findViewById(R.id.idUploadVideoTV) ;
        mReferFrndTV = (TextView)findViewById(R.id.idReferFrndTV) ;

    }
    public void handleClick(){
        mProfileCIV.setOnClickListener(this);
        mMyProfileTV.setOnClickListener(this);
        mProfileShowTV.setOnClickListener(this);
        mShowMessageTV.setOnClickListener(this);
        mAlbumIV.setOnClickListener(this);
        mFriendsTV.setOnClickListener(this);
        mSearchPartnerTV.setOnClickListener(this);
        mPremServicesTV.setOnClickListener(this);
        mChatMessageTV.setOnClickListener(this);
        mSubscriptionTV.setOnClickListener(this);
        mCustomFolderTV.setOnClickListener(this);
        mWalletTV.setOnClickListener(this);
        mUploadVideoTV.setOnClickListener(this);
        mReferFrndTV.setOnClickListener(this);

        setProfileData();

    }


    public  void  setProfileData(){

        String profileUri = prefs.getString(Constants.LOGGED_USER_PIC);
        String userId = prefs.getString(Constants.LOGGED_USERID);
        String userName = prefs.getString(Constants.LOGGED_USERNAME);

        if(!TextUtils.isEmpty(profileUri)){
            Glide.with(PaidSubscriberDashboardActivity.this).load(profileUri).into(mProfileCIV);
        }

        mUsrNameTV.setText(new StringBuilder("@").append(userName));
        mUsrIdTV.setText(new StringBuilder("@").append(userId));

    }
    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()){
            case R.id.idProfileCIV: {
                Toast.makeText(PaidSubscriberDashboardActivity.this,"Profile", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(PaidSubscriberDashboardActivity.this,ProfileActivity.class));
                break;
            }
            case R.id.idMyProfileTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"My Profile", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,ProfileActivity.class));
                break;
            }
            case R.id.idProfileShowTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Profile Show", //Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(PaidSubscriberDashboardActivity.this,ProfileActivity.class));
//                showToast("No interest received");
                AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "Alert!", "No interest received");
                break;
            }
            case R.id.idShowMessageTV: {
//                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Show com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.Message", //Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(PaidSubscriberDashboardActivity.this,ChatMessagesActivity.class));
                AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "Alert!", "No message received");
                break;
            }
            case R.id.idAlbum: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Album", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,AlbumActivity.class));
                break;
            }
            case R.id.idFriendsTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Friens", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,FriendsActivity.class));
                break;
            }
            case R.id.idSearchPartnerTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Search Partner", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,ResultPaidSearchPartnerActivity.class));
                break;
            }
            case R.id.idPremServicesTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Premier Services", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,PremierServicesActivity.class));
                break;
            }
            case R.id.idChatMessageTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Chat/com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.Message", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,ChatMessagesActivity.class));
                break;
            }
            case R.id.idSubscriptionTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Subscription", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,SubscriptionActivity.class));
                break;
            }
            case R.id.idCustFolderTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Custom Folder", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,CustomFoldersActivity.class));
                break;
            }
            case R.id.idWalletTV: {
//                Intent intent = new Intent(PaidSubscriberDashboardActivity.this,WalletActivity.class);
//                intent.putExtra("type","wallet");
//                startActivity(intent);
                break;
            }
            case R.id.idUploadVideoTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Upload Video", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,UploadVideoActivity.class));
                break;
            }
            case R.id.idReferFrndTV: {
//                Toast.makeText(PaidSubscriberDashboardActivity.this,"Refer Friend", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(PaidSubscriberDashboardActivity.this,WalletActivity.class);
//                intent.putExtra("type","referFriend");
//                startActivity(intent);
                break;
            }

        }
    }

    /** API INTEGRATION */

    /* Subscriber Dashboard*/
    public void callWebServiceForSubscribeDashboard(){

//        String token = Constants.Token_Paid;
        String token = prefs.getString(Constants.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(PaidSubscriberDashboardActivity.this);
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<PaidDashboardResponse> call = apiInterface.subscribeDashoardPaid(token);
        call.enqueue(new Callback<PaidDashboardResponse>() {
            @Override
            public void onResponse(Call<PaidDashboardResponse> call, Response<PaidDashboardResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    PaidDashboardResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

                            Toast.makeText(PaidSubscriberDashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            setPaidSubs(serverResponse);

                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "OTP Alert", "Confuse");
                        }
                    }else {
                        Toast.makeText(PaidSubscriberDashboardActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaidDashboardResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(PaidSubscriberDashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    public void setPaidSubs(PaidDashboardResponse serverResponse){

        mProfilePercTV.setText(new StringBuilder(String.valueOf(serverResponse.getBasicData().getProfileComplition())).append("%"));
        List<AllInterestReceived> list = serverResponse.getAllInterestReceived();
        mInterestReceivedTV.setText("("+String.valueOf(list.size())+")");
        mProgress.setProgress(serverResponse.getBasicData().getProfileComplition());
    }

    public void showToast(String msg){

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_dialog,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.logo_main);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
}
