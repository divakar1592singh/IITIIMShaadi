package com.senzecit.iitiimshaadi.viewController;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.AlbumAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.Album;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.AllAlbumResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.pic_response.SetProfileResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.RecyclerItemClickListener;
import com.senzecit.iitiimshaadi.utils.UserDefinedKeyword;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.io.File;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.gauriinfotech.commons.Commons;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AlbumActivity.class.getSimpleName();
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack,mAlbumLogo;
    GridView mGridView;
    LinearLayout mAddBtnLL,mAddImage;
    FrameLayout mNoImageFoundFL,mImageFoundFL;
    APIInterface apiInterface;
    AppPrefs prefs;

    /*Profile Image*/
    private Uri fileUri; // file url to store image/video
    private Uri mCropImagedUri;
    private final int CROP_IMAGE = 101;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 51;
    private static final int READ_FILE_REQUEST_CODE = 52;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    String mPermission1 = Manifest.permission.ACCESS_COARSE_LOCATION;
    String mPermission2 = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    String mPermission3 = Manifest.permission.READ_EXTERNAL_STORAGE;
    String mPermission4 = Manifest.permission.CAMERA;
    private static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
    SwipeRefreshLayout mSwipeRefreshLayout;
    AlbumAdapter albumAdapter = null;

    private int mShortAnimationDuration;
    private Animator mCurrentAnimator;
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_album);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

        init();
        handleView();

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission,mPermission1,mPermission2,mPermission3,mPermission4},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        View view = findViewById(R.id.add);
        switch (item.getItemId()) {
            case R.id.add:

                showMediaChooser(view);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mAlbumLogo = (ImageView) findViewById(R.id.albumLogoIV);
        mBack.setVisibility(View.VISIBLE);
//        mAlbumLogo.setVisibility(View.VISIBLE);
        mTitle.setText("Album");

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        mGridView = (GridView) findViewById(R.id.gridView);
        mAddBtnLL = (LinearLayout) findViewById(R.id.idAddBtnLL);
        mAddImage = (LinearLayout) findViewById(R.id.idAddImgLL);
        mNoImageFoundFL = (FrameLayout) findViewById(R.id.idAlbumNoImageFL);
        mImageFoundFL = (FrameLayout) findViewById(R.id.idAlbumImageFoundFL);
    }

    public void handleView(){

        setSupportActionBar(mToolbar);
        mBack.setOnClickListener(this);
        mAddBtnLL.setOnClickListener(this);
        mAddImage.setOnClickListener(this);

        callWebServiceForAllAlbum();

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Album", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        callWebServiceForAllAlbumWithoutPrgs();
                    }
                }
        );


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if(count == 0) {
                    count++;
                    Toast.makeText(AlbumActivity.this, CONSTANTS.tap_message, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AlbumActivity.this, CONSTANTS.tap_twice_message, Toast.LENGTH_SHORT).show();
                }
                ImageView thumbView = v.findViewById(R.id.grid_imageView);
                thumbView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(AlbumActivity.this, "Inner", Toast.LENGTH_SHORT).show();
                        count = 0;
                        String imageResId = albumAdapter.getItem(position).toString();
//                        Toast.makeText(AlbumActivity.this, "" + imageResId,
//                                Toast.LENGTH_LONG).show();
                        zoomImageFromThumb(thumbView, imageResId);
                    }
                });

            }
        });

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                AlbumActivity.this.finish();
                break;
            case R.id.idAddBtnLL:
                /*mNoImageFoundFL.setVisibility(View.GONE);
                mImageFoundFL.setVisibility(View.VISIBLE);*/
                showMediaChooser(view);
                break;
            case R.id.idAddImgLL:
                showMediaChooser(view);
                break;
        }
    }

    public void showAlbumImage(List<Album> albumList){
        albumAdapter = new AlbumAdapter(this, albumList);
        mGridView.setAdapter(albumAdapter);
    }

    /** Image Upload */
    public void showMediaChooser(View view){
        PopupMenu popupMenu = new PopupMenu(AlbumActivity.this, view);
        popupMenu.inflate(R.menu.menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.camera:
                          captureImage();
                        break;
                    case R.id.storage:
                        showStorage();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    private  void showStorage()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, READ_FILE_REQUEST_CODE);
    }
    /**
     * Creating file uri to store image
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    /**
     * returning image
     */
    private static File getOutputMediaFile(int type) {
        String dir = "Alpha";
        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                dir);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + dir + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("file_uri", fileUri);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            // if the result is capturing Image
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    try {
                        callWebServiceForFileUpload(fileUri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(getApplicationContext(),
                            "User cancelled image capture", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // failed to capture image
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                            .show();
                }

            } else if(requestCode == READ_FILE_REQUEST_CODE){
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                    Log.i(TAG, "Uri: " + uri.toString());
                    try {
//                        uploadCvFile(uri);
//                        show(uri);
                        callWebServiceForFileUpload(uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled recording
                    Toast.makeText(getApplicationContext(),"User cancelled selection", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to get file", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            if(requestCode==CROP_IMAGE) {
                if (data != null) {
                    try {
//                        Toast.makeText(AlbumActivity.this, "Crop", Toast.LENGTH_LONG).show();
                        callWebServiceForSetProfile(data.getData());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /** API */
    /** Album List */
    public void callWebServiceForAllAlbum(){

//        String token = CONSTANTS.Token_Paid;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(AlbumActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(this);
        Call<AllAlbumResponse> call = apiInterface.allAlbumist(token);
        call.enqueue(new Callback<AllAlbumResponse>() {
            @Override
            public void onResponse(Call<AllAlbumResponse> call, Response<AllAlbumResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    AllAlbumResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

//                            Toast.makeText(AlbumActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            try {
                                if (serverResponse.getAlbums().size() > 0) {
                                    mNoImageFoundFL.setVisibility(View.GONE);
                                    mImageFoundFL.setVisibility(View.VISIBLE);

                                    List<Album> albumList = serverResponse.getAlbums();
                                    showAlbumImage(albumList);
                                }
                            }catch (NullPointerException npe){
                                Log.e(TAG, " # Error : "+npe, npe);
                            }

                        } else {
                            reTryMethod();
//                            Toast.makeText(AlbumActivitybumActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
//                        Toast.makeText(AlbumActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        reTryMethod();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllAlbumResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(AlbumActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                reTryMethod();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(AlbumActivity.this);
        }
    }
    public void callWebServiceForAllAlbumWithoutPrgs(){

//        String token = CONSTANTS.Token_Paid;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(AlbumActivity.this) == true){

            Call<AllAlbumResponse> call = apiInterface.allAlbumist(token);
        call.enqueue(new Callback<AllAlbumResponse>() {
            @Override
            public void onResponse(Call<AllAlbumResponse> call, Response<AllAlbumResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    AllAlbumResponse albumResponse = response.body();
                    if(albumResponse.getMessage().getSuccess() != null) {
                        if (albumResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

                            Toast.makeText(AlbumActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            mNoImageFoundFL.setVisibility(View.GONE);
                            mImageFoundFL.setVisibility(View.VISIBLE);

                            List<Album> albumList = albumResponse.getAlbums();
                            showAlbumImage(albumList);

                        } else {
                            Toast.makeText(AlbumActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AlbumActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllAlbumResponse> call, Throwable t) {
                call.cancel();
                mSwipeRefreshLayout.setRefreshing(false);
//                Toast.makeText(AlbumActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }else {
        NetworkDialogHelper.getInstance().showDialog(AlbumActivity.this);
    }
    }

    public void callWebServiceForFileUpload(final Uri uri)throws URISyntaxException {

        String fullPath = Commons.getPath(uri, this);
        File   file = new File(fullPath);
        System.out.print(file);

//        String token = CONSTANTS.Token_Paid;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file[]", file.getName(), requestBody);
//      MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("id_proof", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

        if(NetworkClass.getInstance().checkInternet(AlbumActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(AlbumActivity.this);
        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<AddFolderResponse> callUpload = apiInterface.imageUpload(fileToUpload, filename, token);
        callUpload.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {

                    callWebServiceForAllAlbum();
                    Toast.makeText(AlbumActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialogSingleClick.getInstance().showDialog(AlbumActivity.this, "ID", "Confuse");
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                AlertDialogSingleClick.getInstance().showDialog(AlbumActivity.this, "ID", "Oops");
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(AlbumActivity.this);
        }
    }

    public void reTryMethod(){

        String title = "Alert";
        String msg = "Oops. Please Try Again! \n";

        final Dialog dialog = new Dialog(AlbumActivity.this);
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
                callWebServiceForAllAlbum();
//                dialog.cancel();
            }
        });

        dialog.show();
    }


    public void callWebServiceForSetProfile(final Uri uri)throws URISyntaxException {

        String fullPath = Commons.getPath(uri, AlbumActivity.this);
        File   file = new File(fullPath);

        System.out.print(file);
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file[]", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

        if(NetworkClass.getInstance().checkInternet(AlbumActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(AlbumActivity.this);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<SetProfileResponse> callUpload = apiInterface.setProfileFromURI(fileToUpload, filename, token);
        callUpload.enqueue(new Callback<SetProfileResponse>() {
            @Override
            public void onResponse(Call<SetProfileResponse> call, Response<SetProfileResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getMessage().getSuccess().equalsIgnoreCase("Your Profile picture has been saved.")) {

                            try {
                            String imageFullPath = response.body().getImageFullPath();
                            String[] subString = imageFullPath.split("/");
                            String realURL = subString[subString.length-1];
                            prefs.putString(CONSTANTS.LOGGED_USER_PIC, realURL);
                            }catch (NullPointerException npe){
                                Log.e("TAG", "#Error : "+npe, npe);
                            }
//                            AlertDialogSingleClick.getInstance().showDialog(AlbumActivity.this, "Alert", "Uploaded Succs");
                            callWebServiceForAllAlbum();
                            Toast.makeText(AlbumActivity.this, "Profile Changed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AlbumActivity.this, "Oops, Something went wrong!", Toast.LENGTH_LONG).show();
                        }
                    }catch (NullPointerException npe){
                        Log.e("TAG", "#Error : "+npe, npe);
                    }
//                    callWebServiceForAllAlbum();
                } else {
                    AlertDialogSingleClick.getInstance().showDialog(AlbumActivity.this, "ID", "Confuse");
                }
            }

            @Override
            public void onFailure(Call<SetProfileResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                AlertDialogSingleClick.getInstance().showDialog(AlbumActivity.this, "Failed", "Something went wrong!");
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(AlbumActivity.this);
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
            ProgressClass.getProgressInstance().showDialog(AlbumActivity.this);
            Glide.with(AlbumActivity.this).load(imageResId).error(R.drawable.ic_not_available)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            if(e instanceof UnknownHostException)
//                                progressBar.setVisibility(View.VISIBLE);
                                ProgressClass.getProgressInstance().showDialog(AlbumActivity.this);
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

}
