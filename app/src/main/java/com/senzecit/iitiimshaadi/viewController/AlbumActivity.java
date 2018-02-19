package com.senzecit.iitiimshaadi.viewController;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

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

    /*Profile Image*/
    private static final int READ_FILE_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_album);

        apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);

        init();
        handleView();

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
//                startActivity(new Intent(AlbumActivity.this, PaidSubscriberDashboardActivity.class));
                AlbumActivity.this.finish();
                break;
            case R.id.idAddBtnLL:
                mNoImageFoundFL.setVisibility(View.GONE);
                mImageFoundFL.setVisibility(View.VISIBLE);
                break;
            case R.id.idAddImgLL:
//                mNoImageFoundFL.setVisibility(View.GONE);
//                mImageFoundFL.setVisibility(View.VISIBLE);
//                showToast("Functionality Development Part");
//                showMediaChooser(view);
                showStorage();
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

        String token = Constants.Token_Paid;

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
//                        Toast.makeText(AlbumActivity.this, "Menu : Camera", Toast.LENGTH_SHORT).show();
//                        ClickImageFromCamera() ;
                        break;
                    case R.id.storage:
//                        Toast.makeText(AlbumActivity.this, "Menu : Storage", Toast.LENGTH_SHORT).show();
//                        GetImageFromGallery();
                        showStorage();
                        break;

                }
                return false;
            }
        });
        popupMenu.show();
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
                        show(uri);

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



    public void show(Uri uri) throws URISyntaxException{
        File filpath = new File(uri.getPath());
        Toast.makeText(AlbumActivity.this, "Called:"+filpath.toString(), Toast.LENGTH_SHORT).show();
    }

    public void callWebServiceForFileUpload(final File file)throws URISyntaxException {

        System.out.print(file);

        String token = Constants.Temp_Token;
//        Toast.makeText(AlbumActivity.this, "Method : "+typeOf, Toast.LENGTH_LONG).show();

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

                    AlertDialogSingleClick.getInstance().showDialog(AlbumActivity.this, "ID", "Response :" + response.body().getMessage().getSuccess());
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

    /*EXTRA*/
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
    @Override
    protected void onStop() {
        super.onStop();
        AlbumActivity.this.finish();
    }

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
