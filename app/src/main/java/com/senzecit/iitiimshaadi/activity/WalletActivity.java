package com.senzecit.iitiimshaadi.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.WalletTransactionAdapter;
import com.senzecit.iitiimshaadi.fragment.ReferFriendFragment;
import com.senzecit.iitiimshaadi.fragment.WalletFragment;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener,WalletFragment.WalletFragmentCommunicator {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    FrameLayout mFrameLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_wallet);
        init();
        mFragmentManager = getSupportFragmentManager();

        mBack.setOnClickListener(this);
        type = getIntent().getStringExtra("type");
        if(type.equalsIgnoreCase("wallet")){
            walletFragment();
        }else if(type.equalsIgnoreCase("referFriend")){
            referFriendFragment();
        }


    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mFrameLayout = (FrameLayout) findViewById(R.id.main_wallet_FL);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                int count = mFragmentManager.getBackStackEntryCount();
                if(count>1){
                    Fragment home= mFragmentManager.findFragmentByTag("wallet");
                    if(home!=null){
                        if(home.isVisible()){
                            WalletActivity.this.finish();
                        }else{
                            mFragmentManager.popBackStack("referFriendFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    }
                    super.onBackPressed();
                }else{
                    WalletActivity.this.finish();
                }
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
       /* int count = mFragmentManager.getBackStackEntryCount();
        if(count>1){*/
            Fragment home= mFragmentManager.findFragmentByTag("wallet");
            if(home!=null){
                if(home.isVisible()){
                    WalletActivity.this.finish();
                }else{
                    mFragmentManager.popBackStack("referFriendFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
            super.onBackPressed();
        /*}else{
            WalletActivity.this.finish();
        }*/
        super.onBackPressed();
    }

    private void walletFragment(){
        WalletFragment walletFragment = new WalletFragment();
        setFragment(walletFragment,"wallet");
        mTitle.setText("Wallet");
        walletFragment.setWalletFragmentCommunicator(this);

    }

    private void setFragment(Fragment fragment,String tagName){
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.main_wallet_FL,fragment,tagName);
        mFragmentTransaction.addToBackStack(tagName);
        mFragmentTransaction.commit();
    }

    @Override
    public void referralFriend() {
        referFriendFragment();
    }

    private void referFriendFragment(){
        ReferFriendFragment referFriendFragment = new ReferFriendFragment();
        setFragment(referFriendFragment,"referFriendFragment");
        mTitle.setText("Refer a friend");
//        referFriendFragment.setWalletFragmentCommunicator(this);


    }
}
