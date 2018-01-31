package com.senzecit.iitiimshaadi.viewController;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

public class SubscriptionPlanActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    Toolbar mToolbar;
    TextView mTitle, mSubTotalAmountTV, mTotalAmountTV;
    ImageView mBack;

    RadioGroup mRadioGroup, mCountryRadioGroup;
    RadioButton mInterNationalRB, mIndianRB, mOneMonthRB, mSixMonthRB, mTwelveMonthRB,
            mLifetimeRB, mPaymentModeOneRB, mPaymentModeTwoRB;

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

        mSubTotalAmountTV = (TextView) findViewById(R.id.idSubTotalAmountTV);
        mTotalAmountTV = (TextView) findViewById(R.id.idTotalAmountTV);

    }

    public void handleView(){

        mCountryRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);

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
        mSubTotalAmountTV.setText("$");
        mTotalAmountTV.setText("$");
        if(mInterNationalRB.isChecked() == true && mOneMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("100");
            mTotalAmountTV.append("100");

        }else if(mInterNationalRB.isChecked() == true && mSixMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("200");
            mTotalAmountTV.append("200");
        }else if(mInterNationalRB.isChecked() == true && mTwelveMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("300");
            mTotalAmountTV.append("300");
        }else if(mInterNationalRB.isChecked() == true && mLifetimeRB.isChecked() == true){

            mSubTotalAmountTV.append("500");
            mTotalAmountTV.append("500");
        }

    }
    public void setIndianPrice(){

        mSubTotalAmountTV.setText("");
        mTotalAmountTV.setText("");
        mSubTotalAmountTV.setText(R.string.Rs);
        mTotalAmountTV.setText(R.string.Rs);

        if(mIndianRB.isChecked() == true && mOneMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("5900");
            mTotalAmountTV.append("5900");

        }else if(mIndianRB.isChecked() == true && mSixMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("11800");
            mTotalAmountTV.append("11800");
        }else if(mIndianRB.isChecked() == true && mTwelveMonthRB.isChecked() == true){

            mSubTotalAmountTV.append("17700");
            mTotalAmountTV.append("17700");
        }else if(mIndianRB.isChecked() == true && mLifetimeRB.isChecked() == true){

            mSubTotalAmountTV.append("29500");
            mTotalAmountTV.append("29500");
        }

    }



}
