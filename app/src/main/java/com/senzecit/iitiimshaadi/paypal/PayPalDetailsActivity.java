package com.senzecit.iitiimshaadi.paypal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

import org.json.JSONException;
import org.json.JSONObject;


    public class PayPalDetailsActivity extends AppCompatActivity {

        TextView txtId, txtAmount, txtStatus;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pay_pal_details);

            txtId = (TextView)findViewById(R.id.txtId);
            txtAmount = (TextView)findViewById(R.id.txtAmount);
            txtStatus = (TextView)findViewById(R.id.txtStatus);

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

            try {
                txtId.setText(response.getString("id"));
                txtStatus.setText(response.getString("state"));
                txtAmount.setText("$"+paymentAmount);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
