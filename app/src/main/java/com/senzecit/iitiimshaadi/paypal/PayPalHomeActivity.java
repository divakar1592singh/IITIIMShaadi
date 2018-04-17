package com.senzecit.iitiimshaadi.paypal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;

import org.json.JSONException;

import java.math.BigDecimal;

  public class PayPalHomeActivity extends AppCompatActivity {

  private static final String TAG = "PayPalHomeActivity";
  public static final int PAYPAL_REQUEST_CODE = 7171;
  private static PayPalConfiguration config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PayPalConfig.PAYPAL_CLIENT_ID);

        Button btnPayNow;
        EditText edtAmount;
        String amount = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pay_pal_home);

            btnPayNow = (Button)findViewById(R.id.btnPayNow);
            edtAmount = (EditText)findViewById(R.id.edtAmount);

            try {
                String sAmount = getIntent().getExtras().getString(CONSTANTS.AMOUNT_PAY);
                edtAmount.setText(sAmount);
            }catch (NullPointerException npe){
                Log.e(TAG, " #Error : "+npe, npe);
                edtAmount.setText("0");
            }


            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent);

            processPayment();
            btnPayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    processPayment();
                }
            });

        }

        public void processPayment(){
            amount = edtAmount.getText().toString();

            PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "" +
                    "Pay to Alma Mater Matters", PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
            startActivityForResult(intent, PAYPAL_REQUEST_CODE);

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == PAYPAL_REQUEST_CODE){
                if(resultCode == RESULT_OK){
                    PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if(confirmation != null){

                        try {
                            String paymentDetails = confirmation.toJSONObject().toString(4);
                            startActivity(new Intent(this, PayPalDetailsActivity.class)
                                    .putExtra("paymentDetails", paymentDetails)
                                    .putExtra("PaymentAmount", amount)
                                    .putExtra(CONSTANTS.PLAN_EXP, getIntent().getExtras().getString(CONSTANTS.PLAN_EXP))
                            );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else if(resultCode == Activity.RESULT_CANCELED){
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                }
            }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            stopService(new Intent(this, PayPalService.class));
        }

}
