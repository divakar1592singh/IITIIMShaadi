package com.senzecit.iitiimshaadi.viewController;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
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
import com.androidnetworking.utils.Utils;
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
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.id_verification.IdVerificationResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.email_verification.EmailVerificationRequest;
import com.senzecit.iitiimshaadi.navigation.PaidBaseActivity;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTPREF;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.DataHandlingClass;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    TextView mEmailVerifyTV, mMobVerifyTV, mDocumentsVerifyTV, mProofVerifyTV;

    //
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int mShortAnimationDuration;
    private Animator mCurrentAnimator;
    LinearLayout mAlbumLayout;

    //VERIFICATION BUTTON LIST
    AlertDialog dialogID = null;
    int btnChooserCount = 0;
    String typeOf;
    TextView tvDoc1, tvDoc2, tvDoc3, tvDoc4, mBiodataTv;
    Button mDocBtn1, mDocBtn2, mDocBtn3, mDocBtn4 ;
    LinearLayout mButtonLayout;
    private static final int READ_FILE_REQUEST_CODE = 101;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_subscriber_dashboard);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
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

        mButtonLayout = (LinearLayout) findViewById(R.id.idBtnLayout);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.idSwipeRefreshLayout);
        mAlbumLayout = (LinearLayout)findViewById(R.id.idAlbumLayout);
        mProgress = (ProgressBar)findViewById(R.id.idprogress);
        mProfileCIV = (CircleImageView)findViewById(R.id.idProfileCIV);
        mUsrNameTV = (TextView) findViewById(R.id.idUserNameTV);
        mUsrIdTV = (TextView) findViewById(R.id.idUserId);
        mProfilePercTV = (TextView)  findViewById(R.id.idProfilePercTV);
//        mMyProfileTV = (TextView)findViewById(R.id.idMyProfileTV);

        mProfileShowTV = (TextView)findViewById(R.id.idProfileShowTV) ;
        mShowMessageTV = (TextView)findViewById(R.id.idShowMessageTV);
//        BUTTON LIST

        mEmailVerifyTV = (TextView)findViewById(R.id.idEmailVerify);
        mMobVerifyTV = (TextView)findViewById(R.id.idMobVerify);
        mDocumentsVerifyTV = (TextView)findViewById(R.id.idDocumentsVerify);
        mProofVerifyTV = (TextView)findViewById(R.id.idProofVerify);

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
//        mMyProfileTV.setOnClickListener(this);
        mProfileShowTV.setOnClickListener(this);
        mShowMessageTV.setOnClickListener(this);
        mAlbumLayout.setOnClickListener(this);

        mEmailVerifyTV.setOnClickListener(this);
        mMobVerifyTV.setOnClickListener(this);
        mDocumentsVerifyTV.setOnClickListener(this);
        mProofVerifyTV.setOnClickListener(this);

        mFriendsTV.setOnClickListener(this);
        mSearchPartnerTV.setOnClickListener(this);
        mPremServicesTV.setOnClickListener(this);
        mChatMessageTV.setOnClickListener(this);
        mSubscriptionTV.setOnClickListener(this);
        mCustomFolderTV.setOnClickListener(this);
        mWalletTV.setOnClickListener(this);
        mUploadVideoTV.setOnClickListener(this);
        mReferFrndTV.setOnClickListener(this);

        setVerificationStatus(true, true, true, true, true);
        setProfileData();

    }

    public  void  setProfileData(){

        String userId = prefs.getString(CONSTANTS.LOGGED_USERID);
        String profileUri = CONSTANTS.IMAGE_AVATAR_URL+userId+"/"+prefs.getString(CONSTANTS.LOGGED_USER_PIC);
        String userName = prefs.getString(CONSTANTS.LOGGED_USERNAME);

        if(!TextUtils.isEmpty(profileUri)){
//            Glide.with(PaidSubscriberDashboardActivity.this).load(profileUri).error(R.drawable.profile_img1).into(mProfileCIV);
            Glide.with(PaidSubscriberDashboardActivity.this).load(profileUri).error(DataHandlingClass.getInstance().getProfilePicName()).into(mProfileCIV);

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
           case R.id.idEmailVerify: {
                alertDialogEmail();
                break;
            }
           case R.id.idMobVerify: {
                alertDialogMobile();
                break;
            }
           case R.id.idDocumentsVerify: {
                alertDialogDocuments();
                break;
            }
           case R.id.idProofVerify: {
                alertDialogIDProof();
                break;
            }
            case R.id.idProfileShowTV: {
                if(mInterestReceivedTV.getText().toString().equalsIgnoreCase("(0)")){
                    AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "Alert!", "No interest received");
                }else {
                startActivity(new Intent(PaidSubscriberDashboardActivity.this,AllInterestActivity.class));
                }
                break;
            }
            case R.id.idShowMessageTV: {

                if(mChatReceivedTV.getText().toString().equalsIgnoreCase("(0)")){
                    AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "Alert!", "No interest received");
                }else {
                    startActivity(new Intent(PaidSubscriberDashboardActivity.this, ChatMessagesActivity.class));
                }
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
                    }
                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        reTryMethod();
                    }
                });

        }else {
            networkDialog();
//            NetworkDialogHelper.getInstance().showDialog(PaidSubscriberDashboardActivity.this);
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
                            setPaidSubs(response);
                        }

                        @Override
                        public void onError(ANError error) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            reTryMethod();
                        }
                    });
        }else {
            networkDialog();
            mSwipeRefreshLayout.setRefreshing(false);
//            NetworkDialogHelper.getInstance().showDialog(PaidSubscriberDashboardActivity.this);
        }
    }

    public void setPaidSubs(JSONObject jsonObject){

        try {
            String username = jsonObject.getJSONObject("basicData").getString("name");
            int profileCompletionPerc = jsonObject.getJSONObject("basicData").optInt("profile_complition");
            JSONArray jsonArray = jsonObject.getJSONArray("allInterestReceived");

        mProfilePercTV.setText(new StringBuilder(String.valueOf(profileCompletionPerc)).append("%"));
        mProgress.setProgress(profileCompletionPerc);
        mInterestReceivedTV.setText("("+String.valueOf(jsonArray.length())+")");
            mChatReceivedTV.setText("");
        mChatReceivedTV.setText("("+jsonObject.getInt("chatUserCount")+")");

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
                    .error(DataHandlingClass.getInstance().getProfilePicName())
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

    public void setVerificationStatus(boolean email, boolean mob, boolean bioData, boolean doc, boolean idProof){


        if(email == true){
            mEmailVerifyTV.setText("Email Verified");
            mEmailVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
            mEmailVerifyTV.setEnabled(false);
        }else if (email == false){
            mEmailVerifyTV.setText("Email Unverified");
            mEmailVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
            mEmailVerifyTV.setEnabled(true);
        }

        if(mob == true){
            mMobVerifyTV.setText("Mobile Verified");
            mMobVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
            mMobVerifyTV.setEnabled(false);
        }else if (mob == false){
            mMobVerifyTV.setText("Mobile Unverified");
            mMobVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
            mMobVerifyTV.setEnabled(true);
        }

        if(doc == true){
            mDocumentsVerifyTV.setText("Doc Verified");
            mDocumentsVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
            mDocumentsVerifyTV.setEnabled(false);
        }else if (doc == false){
            mDocumentsVerifyTV.setText("Doc Unverified");
            mDocumentsVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
            mDocumentsVerifyTV.setEnabled(true);
        }

        if(idProof == true){
            mProofVerifyTV.setText("ID Proof Verified");
            mProofVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
            mProofVerifyTV.setEnabled(false);
        }else if (idProof == false){
            mProofVerifyTV.setText("ID Proof Unverified");
            mProofVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
            mProofVerifyTV.setEnabled(true);
        }

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

    //        EXTRA
    public void showSnackBar(){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "Something went wrong! Retry", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }
    public void showAlertMsg(String title, String msg){
        new AlertDialog.Builder(PaidSubscriberDashboardActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /** ******** GARBAGE ******** */

    private void alertDialogEmail(){

        final TextView mMessage;
        final Button mCloseBtn;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.popup_email_layout,null);

        mMessage = dialogView.findViewById(R.id.tvEmail);
        mCloseBtn = dialogView.findViewById(R.id.idCloseBtn);

        mMessage.setText("Press 'Resend' to send verificatin mail");

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebServiceForEmailVerification();
                dialog.dismiss();
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }
    private void alertDialogMobile(){

        final Button mConfirm,mResend;
        final ImageView mCloseIV;
        final EditText mOtpET;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.popup_mobile_layout,null);

        mOtpET = dialogView.findViewById(R.id.idOtpTV);
        mConfirm = dialogView.findViewById(R.id.confirmBtn);
        mResend = dialogView.findViewById(R.id.resendFPLBtn);
        mCloseIV = dialogView.findViewById(R.id.idCloseIV);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otp = mOtpET.getText().toString().trim();
                if (TextUtils.isEmpty(otp)){
                    AlertDialogSingleClick.getInstance().showDialog(PaidSubscriberDashboardActivity.this, "OTP", "OTP is empty");
                }else {
                    callWebServiceForOTPVerification(otp, dialog);
                }

            }
        });

        mResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebServiceForResendOTP();
            }
        });

        mCloseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.show();
    }
    private void alertDialogDocuments(){

        Button mClose ;
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


        mClose = dialogView.findViewById(R.id.confirmBtn);

        mDocBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 1;
                typeOf = UserDefinedKeyword.bioData.toString();
                showStorage();
            }
        });
        mDocBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 2;
                typeOf = UserDefinedKeyword.higher_document.toString();
                showStorage();
            }
        });
        mDocBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 3;
                typeOf = UserDefinedKeyword.under_graduate.toString();
                showStorage();
            }
        });
        mDocBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 4;
                typeOf = UserDefinedKeyword.post_graduate.toString();
                showStorage();
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mConfirm.setBackground(getResources().getDrawable(R.drawable.button_shape_select_forgot));
                mConfirm.setTextColor(getResources().getColor(R.color.colorWhite));*/
                dialog.dismiss();
            }
        });


        dialog.setView(dialogView);
        dialog.show();
    }
    private void alertDialogIDProof(){

        final Button mBrowse, mCancel;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogID = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.popup_id_proof_layout,null);

        mBiodataTv= dialogView.findViewById(R.id.tvIDProof);
        mBrowse = dialogView.findViewById(R.id.idBiodataUpload);
        mCancel = dialogView.findViewById(R.id.idCancelBtn);

        mBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChooserCount = 5;
                typeOf = UserDefinedKeyword.id_proof.toString();
                showStorage();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogID.dismiss();
            }
        });


        dialogID.setView(dialogView);
        dialogID.show();
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

//        String fullPath = Commons.getPath(uri, this);
        String fullPath = "";

        File file = new File(fullPath);
        if (btnChooserCount == 1){
            tvDoc1.setText(getFileName(file.getPath()));
            showUploadAlert("Biodata", file);
        }else if (btnChooserCount == 2){
            tvDoc2.setText(getFileName(file.getPath()));
            showUploadAlert("Higher Ed.",file);
        }else if (btnChooserCount == 3){
            tvDoc3.setText(getFileName(file.getPath()));
            showUploadAlert("Under Grad.", file);
        }else if (btnChooserCount == 4){
            tvDoc4.setText(getFileName(file.getPath()));
            showUploadAlert("Post Grad.",file);
        }else if (btnChooserCount == 5){
            mBiodataTv.setText(getFileName(file.getPath()));
            showUploadAlert("ID", file);
        }
    }

    public void showUploadAlert(String title, File filePath){
        new AlertDialog.Builder(PaidSubscriberDashboardActivity.this)
                .setTitle(title+" Upload")
                .setMessage("are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            callWebServiceForFileUpload(filePath);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private String getFileName(String filePath){
        return filePath.substring(filePath.lastIndexOf("/")+1);
    }

    public void callWebServiceForEmailVerification(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        EmailVerificationRequest emailVerirequest = new EmailVerificationRequest();
        emailVerirequest.token = token;


        if(NetworkClass.getInstance().checkInternet(PaidSubscriberDashboardActivity.this) == true){

            ProgressClass.getProgressInstance().showDialog(PaidSubscriberDashboardActivity.this);
            Call<AddFolderResponse> call = apiInterface.emailVerification(emailVerirequest);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    try {
                        if (response.isSuccessful()) {

                            if (response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

                                showAlertMsg("Alert", "Verfication email sended. Check your mail and follow instruction");
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "Email Alert", "Verfication email sended. Check your mail and follow instruction");
                            }
                        }
                    }catch (NullPointerException npe){
                        Log.e(TAG, "#Error : "+npe, npe);
                        showSnackBar();
                    }
                }

                @Override
                public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(PaidSubscriberDashboardActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    ProgressClass.getProgressInstance().stopProgress();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSubscriberDashboardActivity.this);
        }
    }
    /** MOBILE */
    public void callWebServiceForResendOTP(){

//        String token = CONSTANTS.Own_Token;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);


        if(NetworkClass.getInstance().checkInternet(PaidSubscriberDashboardActivity.this) == true){

            ProgressClass.getProgressInstance().showDialog(PaidSubscriberDashboardActivity.this);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.resendOTP(token);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    String msg1 = "We have sent you a new OTP. In case, you don’t receive it, please send \\\"Verify\\\" message to our mobile number 07042947312.";
                    if (response.isSuccessful()) {
                        AddFolderResponse serverResponse = response.body();
                        if(serverResponse.getMessage().getSuccess() != null) {
                            if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase(msg1)) {
//                     if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("OTP is sent on your registered mobile number.")) {

//                            Toast.makeText(SubscriberDashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", serverResponse.getMessage().getSuccess());
                                showAlertMsg("Info", msg1);

                            }else {
                                showAlertMsg("Info", msg1);
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", msg1);
                            }
                        }else {
                            Toast.makeText(PaidSubscriberDashboardActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(PaidSubscriberDashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    ProgressClass.getProgressInstance().stopProgress();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSubscriberDashboardActivity.this);
        }
    }

    public void callWebServiceForOTPVerification(String otp, AlertDialog dialog){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(PaidSubscriberDashboardActivity.this) == true){

            ProgressClass.getProgressInstance().showDialog(PaidSubscriberDashboardActivity.this);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.verifiyOTP(token, otp);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse serverResponse = response.body();
                        if(serverResponse.getMessage().getSuccess() != null) {
                            if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("OTP is verified")) {

                                dialog.dismiss();
                                showAlertMsg("Alert", serverResponse.getMessage().getSuccess() );
//                            AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "OTP Alert", serverResponse.getMessage().getSuccess());

                            } else {
                                Toast.makeText(PaidSubscriberDashboardActivity.this, CONSTANTS.unknown_err, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(PaidSubscriberDashboardActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(PaidSubscriberDashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    ProgressClass.getProgressInstance().stopProgress();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSubscriberDashboardActivity.this);
        }
    }
    /** File Upload */
    public void callWebServiceForFileUpload(final File file)throws URISyntaxException {

        System.out.print(file);

        Call<IdVerificationResponse> callUpload = null;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
//        Toast.makeText(SubscriberDashboardActivity.this, "Method : "+typeOf, Toast.LENGTH_LONG).show();

        if(NetworkClass.getInstance().checkInternet(PaidSubscriberDashboardActivity.this) == true){

            final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(typeOf, file.getName(), requestBody);
//      MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("id_proof", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

            ProgressClass.getProgressInstance().showDialog(PaidSubscriberDashboardActivity.this);
            apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            callUpload = callManipulationMethod(fileToUpload, filename, token);

            callUpload.enqueue(new Callback<IdVerificationResponse>() {
                @Override
                public void onResponse(Call<IdVerificationResponse> call, Response<IdVerificationResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {

                        if(typeOf.equalsIgnoreCase(UserDefinedKeyword.id_proof.toString())){

                            dialogID.dismiss();
                        }
                        showAlertMsg("Info", response.body().getMessage().getSuccess());
//                    AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "Info", "" + response.body().getMessage().getSuccess());
                    } else {

                        showAlertMsg("Alert", CONSTANTS.unknown_err);
//                    AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "Info", "Confuse");
                    }

                }

                @Override
                public void onFailure(Call<IdVerificationResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    showAlertMsg("Alert", CONSTANTS.unknown_err);
//                AlertDialogSingleClick.getInstance().showDialog(SubscriberDashboardActivity.this, "ID", "Oops");
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(PaidSubscriberDashboardActivity.this);
        }
    }

    public Call<IdVerificationResponse> callManipulationMethod(MultipartBody.Part fileToUpload, RequestBody filename, String token)
    {

        if(typeOf.equalsIgnoreCase(UserDefinedKeyword.id_proof.toString())){
            return apiInterface.idVerification(fileToUpload, filename, token);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.bioData.toString())){
            return apiInterface.biodataUpload(fileToUpload, filename, token);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.higher_document.toString())){
            return apiInterface.highestEduUpload(fileToUpload, filename, token);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.under_graduate.toString())){
            return apiInterface.underGradCertUpload(fileToUpload, filename, token);
        }else if(typeOf.equalsIgnoreCase(UserDefinedKeyword.post_graduate.toString())){
            return apiInterface.postGradCertUpload(fileToUpload, filename, token);
        }else {
//                Toast.makeText(SubscriberDashboardActivity.this, "Default Called", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    public void networkDialog(){
        new AlertDialog.Builder(PaidSubscriberDashboardActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("No Network")
                .setMessage("Check Your Internet")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        finishActivity(0);
                        dialog.dismiss();
                    }

                })
                .show();
    }


}
