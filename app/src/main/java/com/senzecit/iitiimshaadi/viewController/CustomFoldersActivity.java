package com.senzecit.iitiimshaadi.viewController;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.CustomFolderAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.rename_folder.RenameFolderResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.custom_folder.RenameFolderRequest;
import com.senzecit.iitiimshaadi.model.commons.FolderMetaDataModel;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.FolderListModelResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.MyMeta;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.UserDetail;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomFoldersActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    ImageView mAddFolderIV, mEditFolderIV, mDeleteFolderIV;
    EditText mFolderNameET;
    RecyclerView mRecyclerView;
    Spinner mFolderSpnr;
    APIInterface apiInterface;
    AppPrefs prefs;

    List<FolderMetaDataModel> metaDataList;
    FolderMetaDataModel folderMetaDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_custom_folders);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

        init();
        handle();

        callWebServiceForCustomFolder();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                callWebServiceForCustomFolder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("      Custom Folders");

        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mFolderNameET = (EditText)findViewById(R.id.folderNameET);
        mAddFolderIV = (ImageView)findViewById(R.id.idAddFolder);
        mEditFolderIV = (ImageView)findViewById(R.id.idEditFolder);
        mDeleteFolderIV = (ImageView)findViewById(R.id.idDeleteFolder);

        mRecyclerView = (RecyclerView) findViewById(R.id.requestFriendListRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mFolderSpnr = (Spinner)findViewById(R.id.folderNameSPNR);

    }

    private void addTab(String title) {
        mTabLayout.addTab(mTabLayout.newTab().setText(title));
    }
    private void removeTab() {
        mTabLayout.removeAllTabs();
    }

    private void handle(){

        setSupportActionBar(mToolbar);

        mBack.setOnClickListener(this);
        mAddFolderIV.setOnClickListener(this);
        mEditFolderIV.setOnClickListener(this);
        mDeleteFolderIV.setOnClickListener(this);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                callWebServiceForCurrentCustomFolder(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

//                Toast.makeText(CustomFoldersActivity.this, "onTabReselected:"+tab.getText(), Toast.LENGTH_LONG).show();
                callWebServiceForCurrentCustomFolder(tab.getText().toString());
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                CustomFoldersActivity.this.finish();
                break;
            case R.id.idAddFolder:
                checkAddValid();
                break;
            case R.id.idEditFolder:
                checkRenameValid();
                break;
            case R.id.idDeleteFolder:
                checkDeleteAlert();

                break;
        }
    }

    /** API */
    /** Folder Title */
    public void callWebServiceForCustomFolder(){

//        String token = CONSTANTS.Token_Paid;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(CustomFoldersActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(this);
        Call<FolderListModelResponse> call = apiInterface.customFolderList(token);
        call.enqueue(new Callback<FolderListModelResponse>() {
            @Override
            public void onResponse(Call<FolderListModelResponse> call, Response<FolderListModelResponse> response) {
                List<MyMeta> myMetaList = null;
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){

                        removeTab();
                        myMetaList = response.body().getMyMetas();

                        setTitle(myMetaList);
                        setDropDown(myMetaList);

                    }else {
                        reTryMethod();
//                        AlertDialogSingleClick.getInstance().showDialog(CustomFoldersActivity.this, "Search Partner", "Opps");
                    }

                }
            }

            @Override
            public void onFailure(Call<FolderListModelResponse> call, Throwable t) {
                call.cancel();
//                Toast.makeText(CustomFoldersActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
                reTryMethod();
            }
        });
        }else {
            NetworkDialogHelper.getInstance().showDialog(CustomFoldersActivity.this);
        }


    }
    /** Folder List Data */
    public void callWebServiceForCurrentCustomFolder(String mTitle){

//        String token = CONSTANTS.Token_Paid;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(CustomFoldersActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(this);
        Call<FolderListModelResponse> call = apiInterface.customFolderList(token);
        call.enqueue(new Callback<FolderListModelResponse>() {
            @Override
            public void onResponse(Call<FolderListModelResponse> call, Response<FolderListModelResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){

                        List<MyMeta> myMetaList = response.body().getMyMetas();

//                        setTitle(myMetaList);
                        userList(myMetaList, mTitle);
                    }else {
                        AlertDialogSingleClick.getInstance().showDialog(CustomFoldersActivity.this, "Search Partner", "Opps");
                    }
                }
            }

            @Override
            public void onFailure(Call<FolderListModelResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(CustomFoldersActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(CustomFoldersActivity.this);
        }
    }

    public void setTitle(List<MyMeta> myMetaList){
        for(int i = 0; i<myMetaList.size(); i++){
            addTab(myMetaList.get(i).getMetaValue());
        }
    }
    public void setDropDown(List<MyMeta> myMetaList){

        metaDataList =  new ArrayList<>();
        //        SPINNER
        List<String> list = new ArrayList<>();

        for(int i = 0; i < myMetaList.size(); i++){
            list.add(myMetaList.get(i).getMetaValue());
            folderMetaDataModel = new FolderMetaDataModel(myMetaList.get(i).getId(), myMetaList.get(i).getMetaValue());
            metaDataList.add(folderMetaDataModel);
        }

        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(this, R.layout.spnr_layout, list);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFolderSpnr.setAdapter(arrayAdapter1);
        mFolderSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = mFolderSpnr.getSelectedItem().toString();
//                Toast.makeText(CustomFoldersActivity.this, selected, Toast.LENGTH_SHORT).show();
                /*if(selected.equalsIgnoreCase("Delivered")){
                    holder.mCommentLayout.setVisibility(View.GONE);
                }else {
                    holder.mCommentLayout.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setUserList(List<UserDetail> userList){
        CustomFolderAdapter adapter = new CustomFolderAdapter(this, userList);
        mRecyclerView.setAdapter(adapter);

    }
    public void userList(List<MyMeta> myMetaList, String mTitle){
        for(int i = 0; i<myMetaList.size(); i++){
           if(myMetaList.get(i).getMetaValue().equalsIgnoreCase(mTitle)){
               if(myMetaList.get(i).getUserDetails() != null){
                   setUserList( myMetaList.get(i).getUserDetails());
               }else {
                   mRecyclerView.setAdapter(null);
               }
           }

        }
    }

    /** Folder Operation - Add, Rename, Remove */

    public void checkAddValid(){
        String newFolderName = mFolderNameET.getText().toString().trim();

        if(TextUtils.isEmpty(newFolderName)){
            AlertDialogSingleClick.getInstance().showDialog(CustomFoldersActivity.this, "Folder Empty", "Enter folder name.");
        }else{
            new AlertDialog.Builder(CustomFoldersActivity.this)
                    .setTitle("Add Folder Alert")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callWebServiceForAdd();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();


        }
    }

    public void checkRenameValid(){
        String newFolderName = mFolderNameET.getText().toString().trim();
        String sFolderName = mFolderSpnr.getSelectedItem().toString();

        if(TextUtils.isEmpty(newFolderName)){
            AlertDialogSingleClick.getInstance().showDialog(CustomFoldersActivity.this, "Folder Empty", "Enter folder name.");
        }else{
            new AlertDialog.Builder(CustomFoldersActivity.this)
                    .setTitle("Rename Alert")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callWebServiceForRename();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();


        }
    }

    public void checkDeleteAlert(){

            new AlertDialog.Builder(CustomFoldersActivity.this)
                    .setTitle("Delete Alert")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callWebServiceForDelete();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

    }

    public void callWebServiceForAdd(){

//        String token = CONSTANTS.Temp_Token;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        String sFolderName = mFolderNameET.getText().toString().trim();

        if(NetworkClass.getInstance().checkInternet(CustomFoldersActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(CustomFoldersActivity.this);
        Call<AddFolderResponse> call = apiInterface.addFolder(token, sFolderName);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    try {
                        AddFolderResponse addResponse = response.body();
                        if (addResponse.getMessage().getSuccess() != null) {
                            if (addResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {
                                Toast.makeText(CustomFoldersActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                callWebServiceForCustomFolder();
//                            AlertDialogSingleClick.getInstance().showDialog(CustomFoldersActivity.this, "Rename Folder", "Folder rename succesfull.");

                            } else {
//                            Toast.makeText(CustomFoldersActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CustomFoldersActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException npe){
                        Log.e("Custom Folder", "#Error : "+npe, npe );
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(CustomFoldersActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(CustomFoldersActivity.this);
        }
    }

    public void callWebServiceForRename(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        String sFolderName = mFolderNameET.getText().toString().trim();
        int positin = mFolderSpnr.getSelectedItemPosition();
        int metaId = metaDataList.get(positin).getMetaId();

        RenameFolderRequest request = new RenameFolderRequest();
        request.token = token;
        request.meta_id = metaId;
        request.name = sFolderName;

        if(NetworkClass.getInstance().checkInternet(CustomFoldersActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(CustomFoldersActivity.this);
        Call<RenameFolderResponse> call = apiInterface.renameFolder(request);
        call.enqueue(new Callback<RenameFolderResponse>() {
            @Override
            public void onResponse(Call<RenameFolderResponse> call, Response<RenameFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    RenameFolderResponse renameResponse = response.body();
                    if(renameResponse.getMessage().getSuccess() != null) {
                        if (renameResponse.getMessage().getSuccess().toString().equalsIgnoreCase("Folder is renamed")) {

                            callWebServiceForCustomFolder();
//                            AlertDialogSingleClick.getInstance().showDialog(CustomFoldersActivity.this, "Rename Folder", "Folder rename succesfull.");
//                            Toast.makeText(CustomFoldersActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(CustomFoldersActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CustomFoldersActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RenameFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(CustomFoldersActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(CustomFoldersActivity.this);
        }
    }

    public void callWebServiceForDelete(){

//        String token = CONSTANTS.Temp_Token;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        int positin = mFolderSpnr.getSelectedItemPosition();
        int folder_id = metaDataList.get(positin).getMetaId();

        if(NetworkClass.getInstance().checkInternet(CustomFoldersActivity.this) == true){

        ProgressClass.getProgressInstance().showDialog(CustomFoldersActivity.this);
        Call<AddFolderResponse> call = apiInterface.deleteFolder(token, folder_id);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    AddFolderResponse addResponse = response.body();
                    if(addResponse.getMessage().getSuccess() != null) {
                        if (addResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {
                            Toast.makeText(CustomFoldersActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            callWebServiceForCustomFolder();
//                            AlertDialogSingleClick.getInstance().showDialog(CustomFoldersActivity.this, "Rename Folder", "Folder rename succesfull.");

                        } else {
                            Toast.makeText(CustomFoldersActivity.this, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CustomFoldersActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(CustomFoldersActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(CustomFoldersActivity.this);
        }
    }

    public void reTryMethod(){

        String title = "Alert";
        String msg = "Oops. Please Try Again! \n";

        final Dialog dialog = new Dialog(CustomFoldersActivity.this);
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
                callWebServiceForAdd();
//                dialog.cancel();
            }
        });

        dialog.show();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

}
