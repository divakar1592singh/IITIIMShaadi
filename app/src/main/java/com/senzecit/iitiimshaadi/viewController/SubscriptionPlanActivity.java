package com.senzecit.iitiimshaadi.viewController;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.payment.MakePaymentActivity;
import com.senzecit.iitiimshaadi.utils.Constants;

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
                transactPayment();
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

    public void transactPayment(){

        if(mIndianRB.isChecked() == true && mPaymentModeOneRB.isChecked() == true){

            boolean status = true;
            Intent intent = new Intent(SubscriptionPlanActivity.this, MakePaymentActivity.class);
            intent.putExtra(Constants.AMOUNT_PAY, mTotalAmountTV.getText().toString());
            intent.putExtra(Constants.PLAN_STATUS, status);
            startActivity(intent);

        }else if(mInterNationalRB.isChecked() == true) {

        }

    }

}
