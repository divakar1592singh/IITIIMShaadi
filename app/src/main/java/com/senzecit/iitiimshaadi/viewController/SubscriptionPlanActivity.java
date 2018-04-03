package com.senzecit.iitiimshaadi.viewController;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.payment.MakePaymentActivity;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

public class SubscriptionPlanActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    Toolbar mToolbar;
    TextView mTitle, mSubTotalAmountTV, mTotalAmountTV, mTotalSignTV,mSubSignTV;
    ImageView mBack;

    RadioGroup mRadioGroup, mCountryRadioGroup;
    RadioButton mInterNationalRB, mIndianRB, mOneMonthRB, mSixMonthRB, mTwelveMonthRB,
            mLifetimeRB, mPaymentModeOneRB, mPaymentModeTwoRB;

    Button mPayBtn;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_subscription_plan);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        prefs = AppController.getInstance().getPrefs();
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
//        SubscriptionPlanActivity.this.finish();
        startActivity(new Intent(SubscriptionPlanActivity.this, SubscriptionActivity.class));
        finishActivity(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backIV:
//                SubscriptionPlanActivity.this.finish();
                startActivity(new Intent(SubscriptionPlanActivity.this, SubscriptionActivity.class));
                finishActivity(0);
                break;
            case R.id.idPayBtn:

                if(NetworkClass.getInstance().checkInternet(SubscriptionPlanActivity.this) == true){
//                    showPaymentAlert();
                    if(mIndianRB.isChecked() == true && mPaymentModeOneRB.isChecked() == true){

                        alertPaymentSummary();
                    }else if(mInterNationalRB.isChecked() == true) {

                    }
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

    public String getPaymentMode(){
        if(mIndianRB.isChecked() == true ){
            return "Indian";
        }else if(mInterNationalRB.isChecked() == true ){
            return "International";
        }
        return null;
    }

    public String getDuration(){

        if( mOneMonthRB.isChecked() == true){
            return "1 Month";
        }else if( mSixMonthRB.isChecked() == true){
            return "6 Month";
        }else if( mTwelveMonthRB.isChecked() == true){
            return "12 Month";
        }else if( mLifetimeRB.isChecked() == true){
            return "Lifetime";
        }
        return null;

    }

    public void showPaymentAlert(){
//        showDialogPayment();
    }

    private void alertPaymentSummary(){

        final TextView mNameTV,mEmailTV,mMobileTV,mSubsAmountTV,mSubsDurationTV,mSubsTypeTV;

        ;
        final Button mCloseBtn;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final AlertDialog dialog = dialogBuilder.create();
        View dialogView = inflater.inflate(R.layout.toast_payment_layout,null);

        mNameTV = dialogView.findViewById(R.id.idNameTV);
        mEmailTV = dialogView.findViewById(R.id.idEmailTV);
        mMobileTV = dialogView.findViewById(R.id.idMobileTV);
        mSubsTypeTV = dialogView.findViewById(R.id.idSubsTypeTV);
        mSubsDurationTV = dialogView.findViewById(R.id.idSubsDurationTV);
        mSubsAmountTV = dialogView.findViewById(R.id.idSubsAmountTV);

        mCloseBtn = dialogView.findViewById(R.id.idCloseBtn);

        mNameTV.setText(prefs.getString(CONSTANTS.LOGGED_USERNAME));
        mEmailTV.setText(prefs.getString(CONSTANTS.LOGGED_EMAIL));
        mMobileTV.setText(prefs.getString(CONSTANTS.LOGGED_MOB));

        mSubsTypeTV.setText(getPaymentMode());
        mSubsDurationTV.setText(getDuration());
        if(getPaymentMode().equalsIgnoreCase("Indian")){
            mSubsAmountTV.setText("");
            mSubsAmountTV.setText(R.string.Rs);
            mSubsAmountTV.append(" "+mTotalAmountTV.getText().toString());
        }else if (getPaymentMode().equalsIgnoreCase("International")){
            mSubsAmountTV.setText("");
            mSubsAmountTV.append("$ "+mTotalAmountTV.getText().toString());
        }

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                callWebServiceForEmailVerification();
                transactPayment();
                dialog.dismiss();
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }


    public void transactPayment(){

//            Toast.makeText(SubscriptionPlanActivity.this, mTotalAmountTV.getText().toString(), Toast.LENGTH_LONG).show();
            boolean status = true;
            Intent intent = new Intent(SubscriptionPlanActivity.this, MakePaymentActivity.class);
            intent.putExtra(CONSTANTS.AMOUNT_PAY, mTotalAmountTV.getText().toString());
            intent.putExtra(CONSTANTS.PLAN_STATUS, status);
            startActivity(intent);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }


}
