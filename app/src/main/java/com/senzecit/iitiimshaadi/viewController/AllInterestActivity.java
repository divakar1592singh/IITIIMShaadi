package com.senzecit.iitiimshaadi.viewController;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.AllInterestAdapter;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllInterestActivity extends AppCompatActivity implements View.OnClickListener {
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
        mTitle.setText("Interest Received");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewChatUser.setLayoutManager(layoutManager);

        callWebServiceForSubscribeDashboard();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                break;
        }
    }



    /** API INTEGRATION */

    /* Subscriber Dashboard*/
    public void callWebServiceForSubscribeDashboard(){

//        String token = CONSTANTS.Token_Paid;
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(AllInterestActivity.this);
        AndroidNetworking.post("https://iitiimshaadi.com/api/paid_subscriber.json")
                .addBodyParameter("token", token)
//                .addBodyParameter("religion", preferred_Religion)
//                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
//                       System.out.println(response);

                        try {
                            JSONArray jsonArray = response.getJSONArray("allInterestReceived");
                            parseInterestData(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            reTryMethod();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        reTryMethod();
                    }
                });


    /*    ProgressClass.getProgressInstance().showDialog(AllInterestActivity.this);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<PaidDashboardResponse> call = apiInterface.subscribeDashoardPaid(token);
        call.enqueue(new Callback<PaidDashboardResponse>() {
            @Override
            public void onResponse(Call<PaidDashboardResponse> call, Response<PaidDashboardResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    PaidDashboardResponse serverResponse = response.body();
                    if(serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

                            Toast.makeText(AllInterestActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            List<AllInterestReceived> list = serverResponse.getAllInterestReceived();
//                            setPaidSubs(serverResponse);
                            parseInterestData(list);

                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(AllInterestActivity.this, "OTP Alert", "Confuse");
                        }
                    }else {
                        Toast.makeText(AllInterestActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaidDashboardResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(AllInterestActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });*/
    }

    public void parseInterestData(JSONArray jsonArray){

        AllInterestAdapter adapter = new AllInterestAdapter(this, jsonArray);
        mRecyclerViewChatUser.setAdapter(adapter);

    }


/*    @Override
    public void onBackPressed() {

    }
    */

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(ChatMessagesActivity.this, "Stop click hua", Toast.LENGTH_LONG).show();
        AllInterestActivity.this.finish();
    }

    public void reTryMethod(){

        new AlertDialog.Builder(AllInterestActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert")
                .setMessage("Something went wrong!\n Please Try Again!")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        callWebServiceForSubscribeDashboard();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();


    }

}
