package com.senzecit.iitiimshaadi.viewController;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.fragment.InvitedFriendFragment;
import com.senzecit.iitiimshaadi.fragment.MyFriendsFragment;
import com.senzecit.iitiimshaadi.fragment.RequestedFriendFragment;
import com.senzecit.iitiimshaadi.fragment.ShortlistedFriendFragment;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    private ArrayList<String> arrListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_friends);

        init();
        mBack.setOnClickListener(this);

    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Friends");

        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(mViewPager);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyFriendsFragment(), "My Friends (3)");
        adapter.addFragment(new InvitedFriendFragment(), "Invited Friends (3)");
        adapter.addFragment(new RequestedFriendFragment(), "Requests Received (3)");
        adapter.addFragment(new ShortlistedFriendFragment(), "Shortlisted (5)");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                FriendsActivity.this.finish();
                break;
        }
    }

   /* @Override
    public void onFragmentSetSetMyFriend(ArrayList<String> students) {
        arrListData = students;
        mTabLayout.getTabAt(0).setText("My Friends ("+arrListData.size()+")");
    }

    @Override
    public void onFragmentSetInvitedFriend(ArrayList<String> ar) {
        arrListData = ar;
        mTabLayout.getTabAt(1).setText("Invited Friends ("+arrListData.size()+")");
    }

    @Override
    public void onFragmentSetRequestedFriend(ArrayList<String> requestAL) {
        arrListData = requestAL;
        mTabLayout.getTabAt(2).setText("Requested Friends ("+arrListData.size()+")");
    }*/

    class ViewPagerAdapter extends FragmentPagerAdapter{

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
    }
}
