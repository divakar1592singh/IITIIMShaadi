package com.senzecit.iitiimshaadi.viewController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.NotificationsAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.notification.all.AllNotificationRespnse;
import com.senzecit.iitiimshaadi.model.api_response_model.notification.all.GetAllNotificaiton;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.alert.AlertNavigateSingleClick;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    RecyclerView mRecyclerViewChatUser;
    String profileUrl = "https://cdn.iconscout.com/public/images/icon/premium/png-512/avatar-blonde-girl-lady-office-woman-32e5b3ed5e1360f8-512x512.png";

    APIInterface apiInterface;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat_messages);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewChatUser.setLayoutManager(layoutManager);

        callServiceForAllNotific();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                break;
        }
    }



    /** API Integration*/
    public void callServiceForAllNotific(){

//        String token = CONSTANTS.Token_Paid;

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        retrofit2.Call<AllNotificationRespnse> call = apiInterface.allNotificationService(token);
        call.enqueue(new Callback<AllNotificationRespnse>() {
            @Override
            public void onResponse(retrofit2.Call<AllNotificationRespnse> call, Response<AllNotificationRespnse> response) {
                if(response.isSuccessful()){
                    AllNotificationRespnse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")){

                        List<GetAllNotificaiton> list = serverResponse.getGetAllNotificaitons();
                        if(list.size() > 0){

                            parserNotificationData(list);
                        }else {
                            AlertNavigateSingleClick.getInstance().showDialog(NotificationsActivity.this, PaidSubscriberDashboardActivity.class, "Notification Alert", "No Notification Received");
                        }

                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AllNotificationRespnse> call, Throwable t) {

            }
        });

    }

    public void parserNotificationData(List<GetAllNotificaiton> list){

        NotificationsAdapter adapter = new NotificationsAdapter(this, list);
        mRecyclerViewChatUser.setAdapter(adapter);

    }

/*    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(ChatMessagesActivity.this, "Stop click hua", Toast.LENGTH_LONG).show();
        NotificationsActivity.this.finish();
    }*/
}
