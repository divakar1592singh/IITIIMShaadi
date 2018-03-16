package com.senzecit.iitiimshaadi.viewController;

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
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ChatUserAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatMessagesActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    FrameLayout mFrameLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RecyclerView mRecyclerViewChatUser;
    String profileUrl = "https://cdn.iconscout.com/public/images/icon/premium/png-512/avatar-blonde-girl-lady-office-woman-32e5b3ed5e1360f8-512x512.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat_messages);
        init();
        handleView();
        mBack.setOnClickListener(this);
//        chatuserFragment();
    }
    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);

        mRecyclerViewChatUser = (RecyclerView) findViewById(R.id.idChatUserRV);

    }

    public void handleView(){
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");

        ChatUserAdapter adapter = new ChatUserAdapter(this, list);
        mRecyclerViewChatUser.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewChatUser.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                ChatMessagesActivity.this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        /*int count = mFragmentManager.getBackStackEntryCount();
        if(count>1){
            Fragment home= mFragmentManager.findFragmentByTag("chatUserFragment");
            if(home!=null){
                if(home.isVisible()){
                    ChatMessagesActivity.this.finish();
                }else{
                    mFragmentManager.popBackStack("chatMessageFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
            super.onBackPressed();
        }else{
//            Toast.makeText(ChatMessagesActivity.this, "Finish", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ChatMessagesActivity.this, PaidSubscriberDashboardActivity.class));

        }
        super.onBackPressed();*/

   /*     Fragment home= mFragmentManager.findFragmentByTag("chatUserFragment");
        if(home!=null){
            if(home.isVisible()){
                ChatMessagesActivity.this.finish();
            }else{
                mFragmentManager.popBackStack("chatMessageFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
        super.onBackPressed();*/
    }

/*    private void chatuserFragment(){
        ChatUserFragment chatUserFragment = new ChatUserFragment();
        setFragment(chatUserFragment,"chatUserFragment");
        chatUserFragment.setChatUserFragmentCommunicator(this);
        mFragmentTransaction.addToBackStack("TAG");
        mTitle.setText("Chat/com.senzecit.iitiimshaadi.model.customFolder.customFolderModel.Message");

    }
    private void setFragment(Fragment fragment, String tagName){
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.chat_message_FL,fragment,tagName);
        mFragmentTransaction.commit();
    }

    @Override
    public void gotoChat() {
        chatMessageFragment();
    }
    private void chatMessageFragment(){
        ChatMessageFragment chatMessageFragment = new ChatMessageFragment();
        setFragment(chatMessageFragment,"chatMessageFragment");
        mFragmentTransaction.addToBackStack("TAG");
        mTitle.setText("Name");

    }*/

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(ChatMessagesActivity.this, "Stop click hua", Toast.LENGTH_LONG).show();
        ChatMessagesActivity.this.finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}
