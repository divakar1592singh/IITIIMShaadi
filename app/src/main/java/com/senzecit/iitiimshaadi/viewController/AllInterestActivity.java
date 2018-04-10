package com.senzecit.iitiimshaadi.viewController;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.AllInterestAdapter;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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

        if(NetworkClass.getInstance().checkInternet(AllInterestActivity.this) == true){
        callWebServiceForSubscribeDashboard();
        }else {
            NetworkDialogHelper.getInstance().showDialog(AllInterestActivity.this);
        }
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
                            if(jsonArray.length()>0) {
                                parseInterestData(jsonArray);
                            }else {
                                showDialog(AllInterestActivity.this, "Alert", "Np Interest found");
                            }
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

    public void showDialog(Context activity, String title, String msg){
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_single_click);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTxt = (TextView) dialog.findViewById(R.id.txt_file_path);
        titleTxt.setText(title);
        TextView msgTxt = (TextView) dialog.findViewById(R.id.idMsg);
        msgTxt.setText(msg);

        Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                Navigator.getClassInstance().navigateToActivity(AllInterestActivity.this, PaidSubscriberDashboardActivity.class);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }




}
