package com.senzecit.iitiimshaadi.viewController;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.senzecit.iitiimshaadi.adapter.ChatUserAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.chat.SingleChatPostRequest;
import com.senzecit.iitiimshaadi.model.api_response_model.chat_user.ChatUserListModel;
import com.senzecit.iitiimshaadi.model.api_response_model.chat_user.Result;
import com.senzecit.iitiimshaadi.navigation.PaidBaseActivity;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaidSubscriberDashboardActivity extends PaidBaseActivity {

    private static final String TAG = "PaidSubscriberDashboard";
    ScrollView mScrollView;
    CircleImageView mProfileCIV;
    ImageView mAlbumIV;
    TextView mUsrNameTV, mUsrIdTV, mProfilePercTV, mMyProfileTV, mProfileShowTV, mShowMessageTV;
    TextView mFriendsTV, mSearchPartnerTV, mPremServicesTV, mChatMessageTV, mSubscriptionTV, mCustomFolderTV, mWalletTV, mUploadVideoTV, mReferFrndTV;
    TextView mInterestReceivedTV, mChatReceivedTV;
    AppPrefs prefs;
    ProgressBar mProgress;

    //
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int mShortAnimationDuration;
    private Animator mCurrentAnimator;
    LinearLayout mAlbumLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_subscriber_dashboard);

        prefs = AppController.getInstance().getPrefs();

        initView();
        handleClick();
    }

    @Override
    protected void onStart() {
        super.onStart();

        prefs.putInt(CONSTANTPREF.PROGRESS_STATUS_FOR_TAB, 1);
        callWebServiceForPaidSubs();

    }

    public void initView(){

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.idSwipeRefreshLayout);
        mAlbumLayout = (LinearLayout)findViewById(R.id.idAlbumLayout);
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

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callWebServiceForPaidSubsRefresh();
            }
        });

        mProfileCIV.setOnClickListener(this);
        mMyProfileTV.setOnClickListener(this);
        mProfileShowTV.setOnClickListener(this);
        mShowMessageTV.setOnClickListener(this);
        mAlbumLayout.setOnClickListener(this);
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

        String userId = prefs.getString(CONSTANTS.LOGGED_USERID);
        String profileUri = CONSTANTS.IMAGE_AVATAR_URL+userId+"/"+prefs.getString(CONSTANTS.LOGGED_USER_PIC);
        String userName = prefs.getString(CONSTANTS.LOGGED_USERNAME);

        if(!TextUtils.isEmpty(profileUri)){
            Glide.with(PaidSubscriberDashboardActivity.this).load(profileUri).error(R.drawable.profile_img1).into(mProfileCIV);
        }

        mUsrNameTV.setText(new StringBuilder("@").append(userName));
        mUsrIdTV.setText(new StringBuilder("@").append(userId));

    }
    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()){
            case R.id.idProfileCIV: {
//                Toast.makeText(PaidSubscriberDashboardActivity.this,"Profile", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(PaidSubscriberDashboardActivity.this,ProfileActivity.class));

                String userId = prefs.getString(CONSTANTS.LOGGED_USERID);
                String profileUri = CONSTANTS.IMAGE_AVATAR_URL+userId+"/"+prefs.getString(CONSTANTS.LOGGED_USER_PIC);

                zoomImageFromThumb(mProfileCIV, profileUri);

                break;
            }
            case R.id.idMyProfileTV: {
                //Toast.makeText(PaidSubscriberDashboardActivity.this,"My Profile", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,ProfileActivity.class));
                break;
            }
            case R.id.idProfileShowTV: {
//                Toast.makeText(PaidSubscriberDashboardActivity.this,"Profile Show", //Toast.LENGTH_SHORT).show();

                if(mInterestReceivedTV.getText().toString().equalsIgnoreCase("(0)")){
                    AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "Alert!", "No interest received");
                }else {
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,AllInterestActivity.class));
                }
//                showToast("No interest received");
//                AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "Alert!", "No interest received");
                break;
            }
            case R.id.idShowMessageTV: {
//                //Toast.makeText(PaidSubscriberDashboardActivity.this,"Show com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.Message", //Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,ChatMessagesActivity.class));
//                AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "Alert!", "No message received");
                break;
            }
            case R.id.idAlbumLayout: {
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
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,PaidSearchPartnerActivity.class));
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
//                AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "Alert!", "Working on Chat");

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
    public void callWebServiceForPaidSubs(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(PaidSubscriberDashboardActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(PaidSubscriberDashboardActivity.this);
        AndroidNetworking.post("https://iitiimshaadi.com/api/paid_subscriber.json")
                .addBodyParameter("token", token)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                            setPaidSubs(response);
                        chatUserWebApi();
                    }
                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        reTryMethod();
                    }
                });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSubscriberDashboardActivity.this);
        }

    }
    public void callWebServiceForPaidSubsRefresh(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(PaidSubscriberDashboardActivity.this) == true){
            AndroidNetworking.post("https://iitiimshaadi.com/api/paid_subscriber.json")
                    .addBodyParameter("token", token)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            mSwipeRefreshLayout.setRefreshing(false);
//                            setSubsDashboardData( username,  profileCompletionPerc);
                            setPaidSubs(response);
                            chatUserWebApi();
                        }

                        @Override
                        public void onError(ANError error) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            reTryMethod();
                        }
                    });
        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSubscriberDashboardActivity.this);
        }
    }

    public void setPaidSubs(JSONObject jsonObject){

        try {
            String username = jsonObject.getJSONObject("basicData").getString("name");
            int profileCompletionPerc = jsonObject.getJSONObject("basicData").getInt("profile_complition");
            JSONArray jsonArray = jsonObject.getJSONArray("allInterestReceived");

        mProfilePercTV.setText(new StringBuilder(String.valueOf(profileCompletionPerc)).append("%"));
        mProgress.setProgress(profileCompletionPerc);
//        List<AllInterestReceived> list = serverResponse.getAllInterestReceived();
        mInterestReceivedTV.setText("("+String.valueOf(jsonArray.length())+")");

        } catch (JSONException e) {
            e.printStackTrace();
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
            ProgressClass.getProgressInstance().showDialog(PaidSubscriberDashboardActivity.this);
            Glide.with(PaidSubscriberDashboardActivity.this).load(imageResId)
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


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit!")
                .setMessage("Are you sure?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        PaidSubscriberDashboardActivity.super.onBackPressed();

                    }
                }).create().show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    private void chatUserWebApi(){
        APIInterface apiInterface;
        String from_user = prefs.getString(CONSTANTS.LOGGED_USERID);
        SingleChatPostRequest request = new SingleChatPostRequest();
        request.from_user = from_user;

//        ProgressClass.getProgressInstance().showDialog(PaidSubscriberDashboardActivity.this);
        apiInterface = APIClient.getClient(CONSTANTS.CHAT_HISTORY_URL).create(APIInterface.class);
        Call<ChatUserListModel> call1 = apiInterface.singleChatUserList(request);
        call1.enqueue(new Callback<ChatUserListModel>() {
            @Override
            public void onResponse(Call<ChatUserListModel> call, Response<ChatUserListModel> response) {
//                ProgressClass.getProgressInstance().stopProgress();
                if(response.isSuccessful()&&response.code()==200) {
                    try{
                        if(response.body().getResponseCode() == 200) {

                            List<Result> chatList = response.body().getResult();
                            mChatReceivedTV.setText("("+chatList.size()+")");

                        }else {
//                            Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException npe){
                        Log.e(TAG, "#Error : "+npe, npe);
                    }
                } else {
//                    Toast.makeText(getActivity(), "Confused", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatUserListModel> call, Throwable t) {
                call.cancel();
//                ProgressClass.getProgressInstance().stopProgress();
//                Toast.makeText(PaidSubscriberDashboardActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void reTryMethod(){

        String title = "Alert";
        String msg = "Oops. Please Try Again! \n";

        final Dialog dialog = new Dialog(PaidSubscriberDashboardActivity.this);
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
                callWebServiceForPaidSubs();
//                dialog.cancel();
            }
        });

        dialog.show();
    }


}
