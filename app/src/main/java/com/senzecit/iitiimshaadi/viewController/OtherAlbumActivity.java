package com.senzecit.iitiimshaadi.viewController;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.AlbumAdapter;
import com.senzecit.iitiimshaadi.adapter.OtherAlbumAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.Album;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.AllAlbumResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.other_profile.AllAlbumPic;
import com.senzecit.iitiimshaadi.model.api_response_model.other_profile.OtherProfileResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherAlbumActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AlbumActivity.class.getSimpleName();
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack,mAlbumLogo;
    GridView mGridView;
    APIInterface apiInterface;
    AppPrefs prefs;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_other_album);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

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

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        mGridView = (GridView) findViewById(R.id.gridView);

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
        OtherAlbumAdapter albumAdapter = new OtherAlbumAdapter(this, albumList);
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

        new AlertDialog.Builder(OtherAlbumActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert")
                .setMessage("Something went wrong!\n Please Try Again!")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String userId = prefs.getString(CONSTANTS.OTHER_USERID);
                        callWebServiceForOtherProfile(userId);
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
