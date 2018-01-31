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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
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

public class CustomFolderActivity extends FragmentActivity implements View.OnClickListener, RequestedFriendFragment.OnRequestedFriendListener {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    ImageView mAddFolderIV, mEditFolderIV, mDeleteFolderIV;
    EditText mFolderNameET;
    RequestedFriendFragment requestedFriendFragment;
    FragmentManager fragmentManager ;
    FragmentTransaction transaction ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_custom_folder);

        init();
        handle();



//        callWebServiceForSubsIDSearch();
//        requestedFriendFragment.setO
//        setupViewPager1(mViewPager);

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

    }

    private void handle(){
        mBack.setOnClickListener(this);
        mAddFolderIV.setOnClickListener(this);
        mEditFolderIV.setOnClickListener(this);
        mDeleteFolderIV.setOnClickListener(this);

        List<String> folderList = new ArrayList<>();
        folderList.add("Test");

        setupViewPager1(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager1(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RequestedFriendFragment(), "Wishlist");
        adapter.addFragment(new RequestedFriendFragment(), "New Folder");

        viewPager.setAdapter(adapter);
    }

    private void setupViewPager(ViewPager viewPager, List<String> folderList) {

        Fragment fragment = fragmentManager.findFragmentByTag("tag1");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        /*adapter.addFragment(new RequestedFriendFragment(), "Wishlist");
        adapter.addFragment(new RequestedFriendFragment(), "New Folder");*/

        for(int i = 0; i < folderList.size(); i++){
                adapter.addFragment(new RequestedFriendFragment(), folderList.get(i));
            }

//        adapter.notifyDataSetChanged();
        fragmentManager.beginTransaction().hide(fragment).commit();

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                CustomFolderActivity.this.finish();
                break;
            case R.id.idAddFolder:
                if(mFolderNameET.getText().length()>0){
                    String renamedFolder = mFolderNameET.getText().toString();
                    AlertDialogSingleClick.getInstance().showDialog(CustomFolderActivity.this, "Add folder \'"+renamedFolder+"\'", "Functionality Work");
                }else {
                    AlertDialogSingleClick.getInstance().showDialog(CustomFolderActivity.this, "Alert! Field Empty", "Functionality Work");
                }
                break;
            case R.id.idEditFolder:
                if(mFolderNameET.getText().length()>0){
                    String renamedFolder = mFolderNameET.getText().toString();
                    String currentFolder = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText().toString();
                    AlertDialogTwoClick.getInstance().showDialog(CustomFolderActivity.this, "Rename \'"+currentFolder+"\' to \'"+renamedFolder+"\'", "Functionality Work");
                }else {
                    AlertDialogTwoClick.getInstance().showDialog(CustomFolderActivity.this, "Alert! Field Empty", "Functionality Work");
                }
                break;
            case R.id.idDeleteFolder:
                String currentFolder = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText().toString();
                AlertDialogTwoClick.getInstance().showDialog(CustomFolderActivity.this, "Delete \'"+currentFolder+"\'", "Functionality Work");
//                Toast.makeText(CustomFolderActivity.this, "Output : "+mViewPager.getCurrentItem(), Toast.LENGTH_LONG).show();
//                Toast.makeText(CustomFolderActivity.this, "Output : "+mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText()
//                        , Toast.LENGTH_LONG).show();

break;
        }
    }

    /** API */
    /** Folder List */
    public void callWebServiceForSubsIDSearch(){

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
//                        setupViewPager(mViewPager, myMetaList);
                    }else {
                        AlertDialogSingleClick.getInstance().showDialog(CustomFolderActivity.this, "Search Partner", "Opps");
                    }

                }
            }

            @Override
            public void onFailure(Call<FolderListModelResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(CustomFolderActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    @Override
    public void onFragmentSetRequestedFriend(List<String> requestAL) {

//        setupViewPager(mViewPager, requestAL);
    }


    /** Class Handle*/
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public List<String> getmFragmentTitleList() {
            return mFragmentTitleList;
        }
    }
}
