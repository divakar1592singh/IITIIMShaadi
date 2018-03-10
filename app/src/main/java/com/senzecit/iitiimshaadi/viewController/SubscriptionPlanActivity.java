package com.senzecit.iitiimshaadi.viewController;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.customdialog.CustomListAdapterDialog;
import com.senzecit.iitiimshaadi.customdialog.Model;
import com.senzecit.iitiimshaadi.payment.MakePaymentActivity;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SubscriptionPlanActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    Toolbar mToolbar;
    TextView mTitle, mSubTotalAmountTV, mTotalAmountTV, mTotalSignTV,mSubSignTV;
    ImageView mBack;

    RadioGroup mRadioGroup, mCountryRadioGroup;
    RadioButton mInterNationalRB, mIndianRB, mOneMonthRB, mSixMonthRB, mTwelveMonthRB,
            mLifetimeRB, mPaymentModeOneRB, mPaymentModeTwoRB;

    Button mPayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_subscription_plan);

        init();
        handleView();
        mBack.setOnClickListener(this);

    }

    private void init(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Plans");

        mCountryRadioGroup = (RadioGroup) findViewById(R.id.idCountryGB);
        mRadioGroup = (RadioGroup) findViewById(R.id.idRadioGroup);

        mInterNationalRB = (RadioButton)findViewById(R.id.idInterNationalRB);
        mIndianRB = (RadioButton)findViewById(R.id.idIndianRB);
        mOneMonthRB = (RadioButton)findViewById(R.id.idOneMonthRB);
        mSixMonthRB = (RadioButton)findViewById(R.id.idSixMonthRB);
        mTwelveMonthRB = (RadioButton)findViewById(R.id.idTwelveMonthRB);
        mLifetimeRB = (RadioButton)findViewById(R.id.idLifetimeRB);
        mPaymentModeOneRB = (RadioButton)findViewById(R.id.idPaymentModeOneRB);
        mPaymentModeTwoRB = (RadioButton)findViewById(R.id.idPaymentModeTwoRB);

        mSubSignTV = (TextView) findViewById(R.id.idSubSignTV);
        mTotalSignTV = (TextView) findViewById(R.id.idTotalSignTV);

        mSubTotalAmountTV = (TextView) findViewById(R.id.idSubTotalAmountTV);
        mTotalAmountTV = (TextView) findViewById(R.id.idTotalAmountTV);

        mPayBtn = (Button)findViewById(R.id.idPayBtn);
    }

    public void handleView(){

        mBack.setOnClickListener(this);
        mCountryRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mPayBtn.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SubscriptionPlanActivity.this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backIV:
                SubscriptionPlanActivity.this.finish();
                break;
            case R.id.idPayBtn:

                if(NetworkClass.getInstance().checkInternet(SubscriptionPlanActivity.this) == true){
//                    showPaymentAlert();
                    alertPaymentSummary();
                }else {
                    NetworkDialogHelper.getInstance().showDialog(SubscriptionPlanActivity.this);
                }
//                transactPayment();
                break;

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(mInterNationalRB.isChecked() == true){
            setIntenationalPrice();
        }else if(mIndianRB.isChecked() == true){
            setIndianPrice();
        }else {
            mSubTotalAmountTV.setText("");
            mTotalAmountTV.setText("");
        }

    }

    public void setIntenationalPrice(){

        mSubTotalAmountTV.setText("");
        mTotalAmountTV.setText("");
        mSubSignTV.setText("$");
        mTotalSignTV.setText("$");
        if(mInterNationalRB.isChecked() == true && mOneMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("90");
            mTotalAmountTV.append("90");

        }else if(mInterNationalRB.isChecked() == true && mSixMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("160");
            mTotalAmountTV.append("160");
        }else if(mInterNationalRB.isChecked() == true && mTwelveMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("250");
            mTotalAmountTV.append("250");
        }else if(mInterNationalRB.isChecked() == true && mLifetimeRB.isChecked() == true){

            mSubTotalAmountTV.append("350");
            mTotalAmountTV.append("350");
        }

    }
    public void setIndianPrice(){

        mSubTotalAmountTV.setText("");
        mTotalAmountTV.setText("");
        mSubSignTV.setText(R.string.Rs);
        mTotalSignTV.setText(R.string.Rs);

        if(mIndianRB.isChecked() == true && mOneMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("8260");
            mTotalAmountTV.append("8260");

        }else if(mIndianRB.isChecked() == true && mSixMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("12980");
            mTotalAmountTV.append("12980");
        }else if(mIndianRB.isChecked() == true && mTwelveMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("18880");
            mTotalAmountTV.append("18880");
        }else if(mIndianRB.isChecked() == true && mLifetimeRB.isChecked() == true){

            mSubTotalAmountTV.append("28320");
            mTotalAmountTV.append("28320");
        }

    }

    public void showPaymentAlert(){
//        showDialogPayment();

        transactPayment();
    }

/*
    public Vector<Dialog> dialogs = new Vector<Dialog>();
    private void showDialogPayment() {
        int d_width = 100;
        int d_height = 50;

        final Dialog dialog = new Dialog(this, R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        View view = getLayoutInflater().inflate(R.layout.toast_payment_layout, null);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.custom_list);
//		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ((Button) view.findViewById(R.id.button_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final CustomListAdapterDialog clad1 = new CustomListAdapterDialog(SubscriptionPlanActivity.this, null);
        recyclerView.setAdapter(clad1);

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialogs.add(dialog);
        dialog.show();

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Window window = dialog.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setLayout(width - d_width, height - d_height);

        clad1.setOnItemClickListener(new CustomListAdapterDialog.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String id) {

                dialog.dismiss();

//				showDialog(130,50);
            }
        });
    }
*/

    private void alertPaymentSummary(){

        final TextView mMessage;
        final Button mCloseBtn;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.toast_payment_layout,null);

        mMessage = dialogView.findViewById(R.id.tvEmail);
        mCloseBtn = dialogView.findViewById(R.id.idCloseBtn);

        mMessage.setText("Check Payment Detail");

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                callWebServiceForEmailVerification();
                dialog.dismiss();
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }


    public void transactPayment(){

        if(mIndianRB.isChecked() == true && mPaymentModeOneRB.isChecked() == true){

//            Toast.makeText(SubscriptionPlanActivity.this, mTotalAmountTV.getText().toString(), Toast.LENGTH_LONG).show();
            boolean status = true;
            Intent intent = new Intent(SubscriptionPlanActivity.this, MakePaymentActivity.class);
            intent.putExtra(CONSTANTS.AMOUNT_PAY, mTotalAmountTV.getText().toString());
            intent.putExtra(CONSTANTS.PLAN_STATUS, status);
            startActivity(intent);

        }else if(mInterNationalRB.isChecked() == true) {

        }

    }

}
