package com.senzecit.iitiimshaadi.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.senzecit.iitiimshaadi.fragment.InvitedFriendFragment;
import com.senzecit.iitiimshaadi.fragment.MyFriendsFragment;
import com.senzecit.iitiimshaadi.fragment.RequestedFriendFragment;
import com.senzecit.iitiimshaadi.fragment.ShortlistedFriendFragment;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogTwoClick;

import java.util.ArrayList;
import java.util.List;

public class CustomFolderActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    ImageView mAddFolderIV, mEditFolderIV, mDeleteFolderIV;
    EditText mFolderNameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_custom_folder);
        init();
        handle();

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


        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RequestedFriendFragment(), "Wishlist");

//        adapter.addFragment(new RequestedFriendFragment(), "New Folder");
        /*adapter.addFragment(new RequestedFriendFragment(), "Requests Received");
        adapter.addFragment(new InvitedFriendFragment(), "Invited Friends");
        adapter.addFragment(new ShortlistedFriendFragment(), "Shortlisted Friends");*/
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
                AlertDialogTwoClick alertAdd = new AlertDialogTwoClick();
                alertAdd.showDialog(CustomFolderActivity.this, "Add folder \'"+renamedFolder+"\'", "Functionality Work");
                }else {
                    AlertDialogTwoClick alertEdit = new AlertDialogTwoClick();
                    alertEdit.showDialog(CustomFolderActivity.this, "Alert! Field Empty", "Functionality Work");
                }
                break;
            case R.id.idEditFolder:
                if(mFolderNameET.getText().length()>0){
                    String renamedFolder = mFolderNameET.getText().toString();
                    String currentFolder = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText().toString();
                    AlertDialogTwoClick alertEdit = new AlertDialogTwoClick();
                    alertEdit.showDialog(CustomFolderActivity.this, "Rename \'"+currentFolder+"\' to \'"+renamedFolder+"\'", "Functionality Work");
                }else {
                    AlertDialogTwoClick alertEdit = new AlertDialogTwoClick();
                    alertEdit.showDialog(CustomFolderActivity.this, "Alert! Field Empty", "Functionality Work");
                }
                break;
            case R.id.idDeleteFolder:
                String currentFolder = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText().toString();
                AlertDialogTwoClick alertDelete = new AlertDialogTwoClick();
                alertDelete.showDialog(CustomFolderActivity.this, "Delete \'"+currentFolder+"\'", "Functionality Work");
//                Toast.makeText(CustomFolderActivity.this, "Output : "+mViewPager.getCurrentItem(), Toast.LENGTH_LONG).show();
//                Toast.makeText(CustomFolderActivity.this, "Output : "+mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText()
//                        , Toast.LENGTH_LONG).show();

break;
        }
    }

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
