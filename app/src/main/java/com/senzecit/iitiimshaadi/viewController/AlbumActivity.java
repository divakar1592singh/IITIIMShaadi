package com.senzecit.iitiimshaadi.viewController;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.AlbumAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.Album;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.AllAlbumResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.subscriber.id_verification.IdVerificationResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.io.File;
import java.net.URISyntaxException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_album);

        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
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

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mAlbumLogo = (ImageView) findViewById(R.id.albumLogoIV);
        mBack.setVisibility(View.VISIBLE);
        mAlbumLogo.setVisibility(View.VISIBLE);
        mTitle.setText("Album");

        mGridView = (GridView) findViewById(R.id.gridView);
        mAddBtnLL = (LinearLayout) findViewById(R.id.idAddBtnLL);
        mAddImage = (LinearLayout) findViewById(R.id.idAddImgLL);
        mNoImageFoundFL = (FrameLayout) findViewById(R.id.idAlbumNoImageFL);
        mImageFoundFL = (FrameLayout) findViewById(R.id.idAlbumImageFoundFL);
    }

    public void handleView(){

        mBack.setOnClickListener(this);
        mAddBtnLL.setOnClickListener(this);
        mAddImage.setOnClickListener(this);

        callWebServiceForAllAlbum();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                AlbumActivity.this.finish();
                break;
            case R.id.idAddBtnLL:
                mNoImageFoundFL.setVisibility(View.GONE);
                mImageFoundFL.setVisibility(View.VISIBLE);
                break;
            case R.id.idAddImgLL:
                showMediaChooser(view);
                break;
        }
    }

    public void showAlbumImage(List<Album> albumList){
        AlbumAdapter albumAdapter = new AlbumAdapter(this, albumList);
        mGridView.setAdapter(albumAdapter);
    }

    /** API */
    /** Album List */
    public void callWebServiceForAllAlbum(){

//        String token = Constants.Token_Paid;
        String token = prefs.getString(Constants.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(this);
        Call<AllAlbumResponse> call = apiInterface.allAlbumist(token);
        call.enqueue(new Callback<AllAlbumResponse>() {
            @Override
            public void onResponse(Call<AllAlbumResponse> call, Response<AllAlbumResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
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
                Toast.makeText(AlbumActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
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
        }

    }

    public void callWebServiceForFileUpload(final Uri uri)throws URISyntaxException {

        String fullPath = Commons.getPath(uri, this);
        File   file = new File(fullPath);
        System.out.print(file);

//        String token = Constants.Token_Paid;
        String token = prefs.getString(Constants.LOGGED_TOKEN);

        final RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file[]", file.getName(), requestBody);
//      MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("id_proof", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());

        ProgressClass.getProgressInstance().showDialog(AlbumActivity.this);
        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
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

    }

    /** EXTRA */
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
/*
    @Override
    protected void onStop() {
        super.onStop();
        AlbumActivity.this.finish();
    }
*/

//
    //Image Crop Code End Here

/*    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(AlbumActivity.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(AlbumActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(AlbumActivity.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }*/

}
