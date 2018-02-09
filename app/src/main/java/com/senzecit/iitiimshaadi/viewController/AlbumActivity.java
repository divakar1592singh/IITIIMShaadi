package com.senzecit.iitiimshaadi.viewController;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.AlbumAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.Album;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.AllAlbumResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.rename_folder.RenameFolderResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.FolderListModelResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.MyMeta;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;

import java.io.File;
import java.util.List;

import in.gauriinfotech.commons.Commons;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack,mAlbumLogo;
    GridView mGridView;
    LinearLayout mAddBtnLL,mAddImage;
    FrameLayout mNoImageFoundFL,mImageFoundFL;
    APIInterface apiInterface;

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
                showToast("Functionality Development Part");
                break;
        }
    }

    public void showAlbumImage(List<Album> albumList){

        AlbumAdapter albumAdapter = new AlbumAdapter(this, albumList);
        mGridView.setAdapter(albumAdapter);

    }

    /** API */
    /** Folder Title */
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



}
