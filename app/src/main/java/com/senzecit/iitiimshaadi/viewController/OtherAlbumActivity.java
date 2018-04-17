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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.OtherAlbumAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.other_profile.AllAlbumPic;
import com.senzecit.iitiimshaadi.model.api_response_model.other_profile.OtherProfileResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.net.UnknownHostException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherAlbumActivity extends AppCompatActivity implements View.OnClickListener, OtherAlbumAdapter.OnOtherAlbumClickListner {

    private static final String TAG = AlbumActivity.class.getSimpleName();
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack,mAlbumLogo;
    GridView mGridView;

    private int mShortAnimationDuration;
    private Animator mCurrentAnimator;
    static int count = 0;
    OtherAlbumAdapter albumAdapter = null;
    APIInterface apiInterface;
    AppPrefs prefs;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_other_album);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

        init();
        handleView();

    }

    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mAlbumLogo = findViewById(R.id.albumLogoIV);
        mBack.setVisibility(View.VISIBLE);
        mAlbumLogo.setVisibility(View.VISIBLE);
        mTitle.setText("Album");

        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mGridView = findViewById(R.id.gridView);

    }

    public void handleView(){
        mBack.setOnClickListener(this);
        String userId = prefs.getString(CONSTANTS.OTHER_USERID);
        callWebServiceForOtherProfile(userId);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Album", "onRefresh called from SwipeRefreshLayout");
                        String userId = prefs.getString(CONSTANTS.OTHER_USERID);
                        callWebServiceForOtherProfile2(userId);
                    }
                }
        );


//
//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//
////                Toast.makeText(OtherAlbumActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
//                if(count == 0) {
//                    count++;
//                    Toast.makeText(OtherAlbumActivity.this, CONSTANTS.tap_message, Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(OtherAlbumActivity.this, CONSTANTS.tap_twice_message, Toast.LENGTH_SHORT).show();
//                }
//
//                ImageView thumbView = v.findViewById(R.id.grid_imageView);
//                thumbView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        count = 0;
//                        String imageResId = albumAdapter.getItem(position).toString();
////                        Toast.makeText(AlbumActivity.this, "" + imageResId,
////                                Toast.LENGTH_LONG).show();
//                        zoomImageFromThumb(thumbView, imageResId);
//                    }
//                });
//
//            }
//        });

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                OtherAlbumActivity.this.finish();
                break;
        }
    }

    public void showAlbumImage(List<AllAlbumPic> albumList){
        albumAdapter = new OtherAlbumAdapter(this, albumList);
        mGridView.setAdapter(albumAdapter);
    }

    /** API */
    /** API - other profile */
    private void callWebServiceForOtherProfile(String userId){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(OtherAlbumActivity.this) == true){

            ProgressClass.getProgressInstance().showDialog(OtherAlbumActivity.this);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<OtherProfileResponse> call = apiInterface.otherProfileData(token, userId);
            call.enqueue(new Callback<OtherProfileResponse>() {
                @Override
                public void onResponse(Call<OtherProfileResponse> call, Response<OtherProfileResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        OtherProfileResponse serverResponse = response.body();
                        if(serverResponse != null){

                            List<AllAlbumPic> albumList = serverResponse.getAllAlbumPics();

                            if(albumList.size() > 0){
                                showAlbumImage(albumList);
                            }else {
                                showAlert();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<OtherProfileResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    reTryMethod();
//                Toast.makeText(OtherProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(OtherAlbumActivity.this);
        }

    }

    private void callWebServiceForOtherProfile2(String userId){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(OtherAlbumActivity.this) == true){

            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<OtherProfileResponse> call = apiInterface.otherProfileData(token, userId);
            call.enqueue(new Callback<OtherProfileResponse>() {
                @Override
                public void onResponse(Call<OtherProfileResponse> call, Response<OtherProfileResponse> response) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        OtherProfileResponse serverResponse = response.body();
                        if(serverResponse != null){

                            List<AllAlbumPic> albumList = serverResponse.getAllAlbumPics();

                            if(albumList.size() > 0){
                                showAlbumImage(albumList);
                            }else {
                                showAlert();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<OtherProfileResponse> call, Throwable t) {
                    call.cancel();
                    mSwipeRefreshLayout.setRefreshing(false);
                    reTryMethod();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(OtherAlbumActivity.this);
        }

    }


    public void showAlert(){
        new AlertDialog.Builder(OtherAlbumActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Info")
                .setMessage(CONSTANTS.album_not_found)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Navigator.getClassInstance().navigateToActivity(OtherAlbumActivity.this, OtherProfileActivity.class);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void reTryMethod(){

        String title = "Alert";
        String msg = "Oops. Please Try Again! \n";

        final Dialog dialog = new Dialog(OtherAlbumActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_two_click);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTxt = dialog.findViewById(R.id.txt_file_path);
        titleTxt.setText(title);
        TextView msgTxt = dialog.findViewById(R.id.idMsg);
        msgTxt.setText(msg);

        Button dialogBtn_cancel = dialog.findViewById(R.id.btn_cancel);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(OtherAlbumActivity.this, OtherProfileActivity.class);

                startActivity(intent1);
                finish();
                dialog.dismiss();
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = prefs.getString(CONSTANTS.OTHER_USERID);
                callWebServiceForOtherProfile(userId);
            }
        });

        dialog.show();
    }


    //IMAGEZOOM
    private void zoomImageFromThumb(final View thumbView, String imageURL) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = findViewById(
                R.id.expanded_image);
//        expandedImageView.setImageResource(imageResId);

        try {
            ProgressClass.getProgressInstance().showDialog(OtherAlbumActivity.this);
            Glide.with(OtherAlbumActivity.this).load(imageURL).error(R.drawable.ic_not_available)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            if(e instanceof UnknownHostException)
//                                progressBar.setVisibility(View.VISIBLE);
                                ProgressClass.getProgressInstance().showDialog(OtherAlbumActivity.this);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                            ProgressClass.getProgressInstance().stopProgress();
                            return false;
                        }
                    })
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
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onAlbumClick(ImageView thumbView, String imageURL) {

//        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

        zoomImageFromThumb(thumbView, imageURL);
    }
}
