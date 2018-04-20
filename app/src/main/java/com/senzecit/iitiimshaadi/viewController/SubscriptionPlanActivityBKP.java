package com.senzecit.iitiimshaadi.viewController;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.model.commons.PostAuthWebRequest;
import com.senzecit.iitiimshaadi.payment.MakePaymentActivity;
import com.senzecit.iitiimshaadi.paypal2.PayPalHome2Activity;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONException;
import org.json.JSONObject;

public class SubscriptionPlanActivityBKP extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, RxNetworkingForObjectClass.CompletionHandler {

    Toolbar mToolbar;
    TextView mTitle, mSubTotalAmountTV, mTotalAmountTV, mTotalSignTV,mSubSignTV;
    ImageView mBack;

    RadioGroup mRadioGroup, mCountryRadioGroup;
    RadioButton mInterNationalRB, mIndianRB, mOneMonthRB, mSixMonthRB, mTwelveMonthRB,
            mLifetimeRB, mPaymentModeOneRB, mPaymentModeTwoRB;

    Button mPayBtn;
    AppPrefs prefs;
    RxNetworkingForObjectClass rxNetworkingClass;
    PostAuthWebRequest request;


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

        rxNetworkingClass = RxNetworkingForObjectClass.getInstance();
        rxNetworkingClass.setCompletionHandler(this);
        request = new PostAuthWebRequest();

    }

    private void init(){
        mToolbar = findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Plans");

        mCountryRadioGroup = findViewById(R.id.idCountryGB);
        mRadioGroup = findViewById(R.id.idRadioGroup);

        mInterNationalRB = findViewById(R.id.idInterNationalRB);
        mIndianRB = findViewById(R.id.idIndianRB);
        mOneMonthRB = findViewById(R.id.idOneMonthRB);
        mSixMonthRB = findViewById(R.id.idSixMonthRB);
        mTwelveMonthRB = findViewById(R.id.idTwelveMonthRB);
        mLifetimeRB = findViewById(R.id.idLifetimeRB);
        mPaymentModeOneRB = findViewById(R.id.idPaymentModeOneRB);
        mPaymentModeTwoRB = findViewById(R.id.idPaymentModeTwoRB);

        mSubSignTV = findViewById(R.id.idSubSignTV);
        mTotalSignTV = findViewById(R.id.idTotalSignTV);

        mSubTotalAmountTV = findViewById(R.id.idSubTotalAmountTV);
        mTotalAmountTV = findViewById(R.id.idTotalAmountTV);

        mPayBtn = findViewById(R.id.idPayBtn);
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
        startActivity(new Intent(SubscriptionPlanActivityBKP.this, SubscriptionActivity.class));
        finishActivity(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backIV:
//                SubscriptionPlanActivity.this.finish();
                startActivity(new Intent(SubscriptionPlanActivityBKP.this, SubscriptionActivity.class));
                finishActivity(0);
                break;
            case R.id.idPayBtn:

//                if(mPaymentModeTwoRB.isChecked() == true){
//                    callWebServiceForResendEmail();
//                }

                if(NetworkClass.getInstance().checkInternet(SubscriptionPlanActivityBKP.this) == true){
                    showPaymentAlert();

                 /*   if(mIndianRB.isChecked() == true && mPaymentModeOneRB.isChecked() == true){

                        alertPaymentSummary();
                    }else if(mInterNationalRB.isChecked() == true) {

                    }*/
                }else {
                    NetworkDialogHelper.getInstance().showDialog(SubscriptionPlanActivityBKP.this);
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

/*        TextView mTitle, mSubTotalAmountTV, mTotalAmountTV, mTotalSignTV,mSubSignTV;
        ImageView mBack;

        RadioGroup mRadioGroup, mCountryRadioGroup;
        RadioButton mInterNationalRB, mIndianRB, mOneMonthRB, mSixMonthRB, mTwelveMonthRB,
                mLifetimeRB, mPaymentModeOneRB, mPaymentModeTwoRB;*/

        if(mPaymentModeOneRB.isChecked() == true){
            alertPaymentSummary();
        }

        if(mPaymentModeTwoRB.isChecked() == true){
            callWebServiceForResendEmail();
        }
    }

    private void alertPaymentSummary(){

        final TextView mNameTV,mEmailTV,mMobileTV,mSubsAmountTV,mSubsDurationTV,mSubsTypeTV;

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
                if(mIndianRB.isChecked() == true){
                    transactPayUMoney();
                }else if(mInterNationalRB.isChecked() == true){
                    transactPayPal();
                }
                dialog.dismiss();
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }


    public void transactPayUMoney(){
            boolean status = true;
            Intent intent = new Intent(SubscriptionPlanActivityBKP.this, MakePaymentActivity.class);
            intent.putExtra(CONSTANTS.AMOUNT_PAY, mTotalAmountTV.getText().toString());
            intent.putExtra(CONSTANTS.PLAN_STATUS, status);
            startActivity(intent);


    }

    public void transactPayPal(){
            boolean status = true;
            Intent intent = new Intent(SubscriptionPlanActivityBKP.this, PayPalHome2Activity.class);
            intent.putExtra(CONSTANTS.AMOUNT_PAY, mTotalAmountTV.getText().toString());
            intent.putExtra(CONSTANTS.PLAN_STATUS, status);
            intent.putExtra(CONSTANTS.PLAN_EXP, getDuration());
            startActivity(intent);

    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    public void callWebServiceForResendEmail(){

        request.token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(SubscriptionPlanActivityBKP.this, CONSTANTS.RTGS_PAYMENT_URL, request, CONSTANTS.METHOD_1);

    }


    @Override
    public void handle(JSONObject object, String methodName) {

        System.out.println(object);

        try {
            if(object.getJSONObject("message").getInt("response_code") == 200){
                AlertDialogSingleClick.getInstance().showDialog(SubscriptionPlanActivityBKP.this, "Alert", "An email is sent with the bank details.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
