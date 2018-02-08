package com.senzecit.iitiimshaadi.viewController;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.RequestedFriendAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.fragment.RequestedFriendFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.SubsAdvanceSearchResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.FolderListModelResponse;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.MyMeta;
import com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.UserDetail;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogTwoClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_custom_folders);

        init();
        handle();

        callWebServiceForCustomFolder();

    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Custom Folders");

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

    private void handle(){
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

        //        SPINNER
        List<String> list = new ArrayList<>();
        list.add("Folder-1");
        list.add("Folder-2");
        list.add("Folder-3");
        list.add("Folder-4");

        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(this, R.layout.spnr_layout, list);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFolderSpnr.setAdapter(arrayAdapter1);
        mFolderSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = mFolderSpnr.getSelectedItem().toString();
                Toast.makeText(CustomFoldersActivity.this, selected, Toast.LENGTH_SHORT).show();
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

        mFolderNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())){
                    mFolderSpnr.setVisibility(View.VISIBLE);
                }else {
                    mFolderSpnr.setVisibility(View.GONE);


                }
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
                if(mFolderNameET.getText().length()>0){
                    String renamedFolder = mFolderNameET.getText().toString();
                    AlertDialogSingleClick.getInstance().showDialog(CustomFoldersActivity.this, "Add folder \'"+renamedFolder+"\'", "Functionality Work");
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(CustomFoldersActivity.this, "Alert! Field Empty", "Functionality Work");
                }
                break;
            case R.id.idEditFolder:
                if(mFolderNameET.getText().length()>0){
                    String renamedFolder = mFolderNameET.getText().toString();
                    String currentFolder = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText().toString();
                    AlertDialogTwoClick.getInstance().showDialog(CustomFoldersActivity.this, "Rename \'"+currentFolder+"\' to \'"+renamedFolder+"\'", "Functionality Work");
                }else {
                    AlertDialogTwoClick.getInstance().showDialog(CustomFoldersActivity.this, "Alert! Field Empty", "Functionality Work");
                }
                break;
            case R.id.idDeleteFolder:
                String currentFolder = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText().toString();
                AlertDialogTwoClick.getInstance().showDialog(CustomFoldersActivity.this, "Delete \'"+currentFolder+"\'", "Functionality Work");
//                Toast.makeText(CustomFoldersActivity.this, "Output : "+mViewPager.getCurrentItem(), Toast.LENGTH_LONG).show();
//                Toast.makeText(CustomFoldersActivity.this, "Output : "+mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText()
//                        , Toast.LENGTH_LONG).show();

                break;
        }
    }

    /** API */
    /** Folder Title */
    public void callWebServiceForCustomFolder(){

        String token = Constants.Token_Paid;

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(this);
        Call<FolderListModelResponse> call = apiInterface.customFolderList(token);
        call.enqueue(new Callback<FolderListModelResponse>() {
            @Override
            public void onResponse(Call<FolderListModelResponse> call, Response<FolderListModelResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){

                        List<MyMeta> myMetaList = response.body().getMyMetas();

                        setTitle(myMetaList);

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
    }
    /** Folder List Data */
    public void callWebServiceForCurrentCustomFolder(String mTitle){

        String token = Constants.Token_Paid;

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
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
    }

    public void setTitle(List<MyMeta> myMetaList){
        for(int i = 0; i<myMetaList.size(); i++){
            addTab(myMetaList.get(i).getMetaValue());
        }
    }
    public void setUserList(List<UserDetail> userList){
        RequestedFriendAdapter adapter = new RequestedFriendAdapter(this, userList);
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


}
