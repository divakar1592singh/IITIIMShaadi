package com.senzecit.iitiimshaadi.paypal2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.paypal.PayPalDetailsActivity;
import com.senzecit.iitiimshaadi.paypal.PayPalHomeActivity;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.alert.ToastDialogMessage;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PayPalHome2Activity extends Activity implements RxNetworkingForObjectClass.CompletionHandler{
    private static final String TAG = "paymentExample";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
//    private static final String CONFIG_CLIENT_ID = "credentials from developer.paypal.com";
    private static final String CONFIG_CLIENT_ID = "Afe5KfwhrE1lLpYyBRD79SMQQRxLuzJdlsV1oQruvRcxD-5TU09bb2MuwoQBeNHQz7zBUHYH8zVJ0Rfd";
    private static final int REQUEST_CODE_PAYMENT = 1;
    //
    RxNetworkingForObjectClass networkingClass;
    AppPrefs prefs;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Alma Mater Matters Pvt Ltd")
            .merchantPrivacyPolicyUri(Uri.parse("https://iitiimshaadi.com/users/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://iitiimshaadi.com/users/disclaimer"));

    TextView mTxnIDTV, mDurationTV, mAmountTV;
    ConstraintLayout mPaySuccessLayout;
    Button mBuyBtn, mContinueBtn;
    FrameLayout mBuyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_home);

        prefs = AppPrefs.getInstance(PayPalHome2Activity.this);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        //
        networkingClass = RxNetworkingForObjectClass.getInstance();
        networkingClass.setCompletionHandler(this);

        handleView();
        onBuy();
    }

    private void handleView() {

        //
        mTxnIDTV = (TextView)findViewById(R.id.idTxnId) ;
        mDurationTV = (TextView)findViewById(R.id.idDurationTV) ;
        mAmountTV = (TextView)findViewById(R.id.idAmountTV) ;
        mBuyBtn = (Button)findViewById(R.id.buyItBtn) ;
        mContinueBtn = (Button)findViewById(R.id.idContinue) ;

        mPaySuccessLayout = (ConstraintLayout)findViewById(R.id.idPaySuccessLayout);
        mBuyLayout = (FrameLayout)findViewById(R.id.payNowLayout);

        mBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyPressed();
            }
        });

        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinuePressed();
            }
        });
    }


    public void onBuy() {
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PayPalHome2Activity.this, PaymentActivity.class);
        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    public void onBuyPressed() {
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PayPalHome2Activity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    public void onContinuePressed(){
        Navigator.getClassInstance().navigateToActivity(PayPalHome2Activity.this, SplashActivity.class);
        finish();
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        String sAmount = getIntent().getExtras().getString(CONSTANTS.AMOUNT_PAY);
        return new PayPalPayment(new BigDecimal(sAmount), "USD", "Amount",
                paymentIntent);
    }

    protected void displayResultText(String result) {
        ((TextView)findViewById(R.id.txtResult)).setText("Result : " + result);
        /*Toast.makeText(
                getApplicationContext(),
                result, Toast.LENGTH_LONG)
                .show();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));

                        displayResultText("PaymentConfirmation info received from PayPal");

                        JSONObject responseObject = confirm.toJSONObject();
                        JSONObject paymentObject = confirm.getPayment().toJSONObject();

                        showDetails(responseObject, paymentObject);


                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    //UPLOAD TO Server

    private void showDetails(JSONObject userResp, JSONObject payRes) {

        String extDate = "";
        try {
            String sDate = getIntent().getExtras().getString(CONSTANTS.PLAN_EXP);
            if( sDate.equalsIgnoreCase("1 Month")){
                extDate = getExpDate(1);
            }else if( sDate.equalsIgnoreCase("6 Month")){
                extDate = getExpDate(6);
            }else if( sDate.equalsIgnoreCase("12 Month")){
                extDate = getExpDate(12);
            }else if( sDate.equalsIgnoreCase("Lifetime")){
                extDate = "Lifetime";
            }else {
                extDate = "";
            }

            String transactionId = userResp.getJSONObject("response").getString("id");
            String amount = payRes.getString("amount");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", prefs.getString(CONSTANTS.LOGGED_USERID));
            jsonObject.put("transaction_id", transactionId);
            jsonObject.put("payment_mode", "PayPal");
            jsonObject.put("amount", amount);
            jsonObject.put("payment_date", getCurrentDate());
            jsonObject.put("exp_date", extDate);

            RxNetworkingForObjectClass.getInstance().callWebServiceForJSONParsing(PayPalHome2Activity.this, CONSTANTS.PAYPAL_SUCCESS_URL, jsonObject, CONSTANTS.METHOD_1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getCurrentDate(){
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String sdate = formatter.format(date);
        System.out.println("Today : " + sdate);
        return sdate;


    }
    public String getExpDate(int month){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, month);
        Date date = cal.getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String sdate = formatter.format(date);
        System.out.println("Today : " + sdate);
        return sdate;

    }


    @Override
    public void handle(JSONObject object, String methodName) {
        try {
            if(object.getInt("responseCode") == 200){
//                AlertDialogSingleClick.getInstance().showDialog(PayPalDetailsActivity.this, "Alert", "Payment Successfull");
//                ToastDialogMessage.getInstance().showToast(PayPalHome2Activity.this, "Payment Successfull");
                mPaySuccessLayout.setVisibility(View.VISIBLE);
                mBuyLayout.setVisibility(View.GONE);

                mTxnIDTV.setText(object.getJSONObject("data").getString("transaction_id"));
                mDurationTV.setText(formatDate(object.getJSONObject("data").getString("exp_date")));
                mAmountTV.setText("$ "+object.getJSONObject("data").getString("amount"));


            }else {
                mPaySuccessLayout.setVisibility(View.GONE);
                mBuyLayout.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mPaySuccessLayout.setVisibility(View.GONE);
            mBuyLayout.setVisibility(View.VISIBLE);
        }

    }

    private String formatDate(String dateString){
//        String dateString="2015-04-22 19:31:15";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println("date:"+date);
        df=new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("Formated Date:"+df.format(date));
        return df.format(date);
    }

}
