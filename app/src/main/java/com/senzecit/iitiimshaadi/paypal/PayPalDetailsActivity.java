package com.senzecit.iitiimshaadi.paypal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.alert.ToastDialogMessage;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PayPalDetailsActivity extends AppCompatActivity implements RxNetworkingForObjectClass.CompletionHandler {

        TextView txtId, txtAmount, txtStatus;
        AppPrefs prefs;
        RxNetworkingForObjectClass networkingClass;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pay_pal_details);

            txtId = findViewById(R.id.txtId);
            txtAmount = findViewById(R.id.txtAmount);
            txtStatus = findViewById(R.id.txtStatus);

            prefs = new AppPrefs(PayPalDetailsActivity.this);
            networkingClass = RxNetworkingForObjectClass.getInstance();
            networkingClass.setCompletionHandler(this);

            //Get Intent

            Intent intent = getIntent();

            try {
                JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
                showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        private void showDetails(JSONObject response, String paymentAmount) {

            String currentDate = "";
            String extDate = "";
            try {
                txtId.setText(response.getString("id"));
                txtStatus.setText(response.getString("state"));
                txtAmount.setText("$"+paymentAmount);
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

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("user_id", prefs.getString(CONSTANTS.LOGGED_USERID));
                jsonObject.put("transaction_id", txtId.getText().toString());
                jsonObject.put("payment_mode", "PayPal");
                jsonObject.put("amount", txtAmount.getText().toString());
                jsonObject.put("payment_date", getCurrentDate());
                jsonObject.put("exp_date", extDate);

            RxNetworkingForObjectClass.getInstance().callWebServiceForJSONParsing(PayPalDetailsActivity.this, CONSTANTS.PAYPAL_SUCCESS_URL, jsonObject, CONSTANTS.METHOD_1);

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
                ToastDialogMessage.getInstance().showToast(PayPalDetailsActivity.this, "Payment Successfull");
                Navigator.getClassInstance().navigateToActivity(PayPalDetailsActivity.this, SplashActivity.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
