package com.senzecit.iitiimshaadi.viewController;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ChatUserAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.chat.SingleChatPostRequest;
import com.senzecit.iitiimshaadi.model.api_response_model.chat_user.ChatUserListModel;
import com.senzecit.iitiimshaadi.model.api_response_model.chat_user.Result;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatMessagesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChatMessagesActivity";
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    FrameLayout mFrameLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RecyclerView mRecyclerViewChatUser;
    AppPrefs prefs;
    String profileUrl = "https://cdn.iconscout.com/public/images/icon/premium/png-512/avatar-blonde-girl-lady-office-woman-32e5b3ed5e1360f8-512x512.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat_messages);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        prefs = new AppPrefs(ChatMessagesActivity.this);

        init();
        handleView();
        mBack.setOnClickListener(this);
//        chatuserFragment();
        chatUserWebApi();
    }
    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);

        mTitle.setText("Chat History");
        mRecyclerViewChatUser = (RecyclerView) findViewById(R.id.idChatUserRV);

    }

    public void handleView(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewChatUser.setLayoutManager(layoutManager);
    }

    private void chatUserWebApi(){
        APIInterface apiInterface;
        String from_user = prefs.getString(CONSTANTS.LOGGED_USERID);
        SingleChatPostRequest request = new SingleChatPostRequest();
        request.from_user = from_user;

        ProgressClass.getProgressInstance().showDialog(ChatMessagesActivity.this);
        apiInterface = APIClient.getClient(CONSTANTS.CHAT_HISTORY_URL).create(APIInterface.class);
        Call<ChatUserListModel> call1 = apiInterface.singleChatUserList(request);
        call1.enqueue(new Callback<ChatUserListModel>() {
            @Override
            public void onResponse(Call<ChatUserListModel> call, Response<ChatUserListModel> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if(response.isSuccessful()&&response.code()==200) {
                    try{
                        if(response.body().getResponseCode() == 200) {
//                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                            List<Result> chatList = response.body().getResult();

                            ChatUserAdapter adapter = new ChatUserAdapter(ChatMessagesActivity.this, chatList);
                            mRecyclerViewChatUser.setAdapter(adapter);

                        }else {
//                            Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException npe){
                        Log.e(TAG, "#Error : "+npe, npe);
                    }
                } else {
//                    Toast.makeText(getActivity(), "Confused", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatUserListModel> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(ChatMessagesActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                //    ProgressClass.getProgressInstance().stopProgress();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                ChatMessagesActivity.this.finish();
                break;
        }
    }

/*    @Override
    public void onBackPressed() {
        startActivity(new Intent(Chat));
    }*/
/*
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
    }*/
}
