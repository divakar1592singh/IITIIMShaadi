package com.senzecit.iitiimshaadi.viewController;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.NotificationsAdapter;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.notification.all.AllNotificationRespnse;
import com.senzecit.iitiimshaadi.model.api_response_model.notification.all.GetAllNotificaiton;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.alert.ToastDialogMessage;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    RecyclerView mRecyclerViewChatUser;
    APIInterface apiInterface;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat_messages);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        prefs = AppController.getInstance().getPrefs();

        init();
        handleView();
        mBack.setOnClickListener(this);
//        chatuserFragment();
    }
    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);

        mRecyclerViewChatUser = findViewById(R.id.idChatUserRV);

    }

    public void handleView(){

        mTitle.setText("All Notification");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewChatUser.setLayoutManager(layoutManager);

        callServiceForAllNotific();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                Navigator.getClassInstance().navigateToActivity(NotificationsActivity.this, PaidSubscriberDashboardActivity.class);
                break;
        }
    }

    /** API Integration*/
    public void callServiceForAllNotific(){
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        if(NetworkClass.getInstance().checkInternet(NotificationsActivity.this) == true){

            ProgressClass.getProgressInstance().showDialog(NotificationsActivity.this);
        retrofit2.Call<AllNotificationRespnse> call = apiInterface.allNotificationService(token);
        call.enqueue(new Callback<AllNotificationRespnse>() {
            @Override
            public void onResponse(retrofit2.Call<AllNotificationRespnse> call, Response<AllNotificationRespnse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if(response.isSuccessful()){
                    AllNotificationRespnse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")){

                        List<GetAllNotificaiton> list = serverResponse.getGetAllNotificaitons();
                        if(list.size() > 0){

                            parserNotificationData(list);
                        }else {
                            showDialog(NotificationsActivity.this, "Notification Alert", "No Notification Received");
                        }

                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AllNotificationRespnse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                ToastDialogMessage.getInstance().showToast(NotificationsActivity.this, AppMessage.SOME_ERROR_INFO);
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(NotificationsActivity.this);
        }

    }

    public void parserNotificationData(List<GetAllNotificaiton> list){

        NotificationsAdapter adapter = new NotificationsAdapter(this, list);
        mRecyclerViewChatUser.setAdapter(adapter);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    public void showDialog(Context activity, String title, String msg){
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_single_click);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTxt = dialog.findViewById(R.id.txt_file_path);
        titleTxt.setText(title);
        TextView msgTxt = dialog.findViewById(R.id.idMsg);
        msgTxt.setText(msg);

        Button dialogBtn_cancel = dialog.findViewById(R.id.btn_cancel);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                Navigator.getClassInstance().navigateToActivity(NotificationsActivity.this, PaidSubscriberDashboardActivity.class);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    /*    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(ChatMessagesActivity.this, "Stop click hua", Toast.LENGTH_LONG).show();
        NotificationsActivity.this.finish();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Navigator.getClassInstance().navigateToActivity(NotificationsActivity.this, PaidSubscriberDashboardActivity.class);
    }
}
