package com.senzecit.iitiimshaadi.payment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.SubscriptionPlanActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MakePaymentActivity extends BaseActivity {

    public static final String TAG = "MakePaymentActivity : ";

    private String userMobile, userEmail;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private SharedPreferences userDetailsPreference;
    private EditText email_et, mobile_et, amount_et;
    private TextInputLayout email_til, mobile_til;
    private RadioGroup radioGroup_color_theme, radioGroup_select_env;
    private SwitchCompat switch_disable_wallet, switch_disable_netBanks, switch_disable_cards;
    private TextView logoutBtn;
    private AppCompatRadioButton radio_btn_default;
    private AppPreference mAppPreference;
    private AppCompatRadioButton radio_btn_theme_purple, radio_btn_theme_pink, radio_btn_theme_green, radio_btn_theme_grey;
    ImageView mBackButton;

    private Button payNowButton;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    AppPrefs prefs;
    boolean isPlanPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAppPreference = new AppPreference();
        prefs = AppController.getInstance().getPrefs();
        isPlanPage = getIntent().getExtras().getBoolean(CONSTANTS.PLAN_STATUS);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.app_name));
        settings = getSharedPreferences("settings", MODE_PRIVATE);
        logoutBtn = findViewById(R.id.logout_button);
        email_et = findViewById(R.id.email_et);
        mobile_et = findViewById(R.id.mobile_et);
        amount_et = findViewById(R.id.amount_et);
        amount_et.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});
        email_til = findViewById(R.id.email_til);
        mobile_til = findViewById(R.id.mobile_til);
        radioGroup_color_theme = findViewById(R.id.radio_grp_color_theme);
        radio_btn_default = findViewById(R.id.radio_btn_theme_default);
        radio_btn_theme_pink = findViewById(R.id.radio_btn_theme_pink);
        radio_btn_theme_purple = findViewById(R.id.radio_btn_theme_purple);
        radio_btn_theme_green = findViewById(R.id.radio_btn_theme_green);
        radio_btn_theme_grey = findViewById(R.id.radio_btn_theme_grey);

        mBackButton = findViewById(R.id.idBackIV);

        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(View.GONE);
        }

        switch_disable_wallet = findViewById(R.id.switch_disable_wallet);
        switch_disable_netBanks = findViewById(R.id.switch_disable_netbanks);
        switch_disable_cards = findViewById(R.id.switch_disable_cards);
        AppCompatRadioButton radio_btn_sandbox = findViewById(R.id.radio_btn_sandbox);
        AppCompatRadioButton radio_btn_production = findViewById(R.id.radio_btn_production);
        radioGroup_select_env = findViewById(R.id.radio_grp_env);

        payNowButton = findViewById(R.id.pay_now_button);

        initListeners();

        //Set Up SharedPref
        setUpUserDetails();

        if (settings.getBoolean("is_prod_env", false)) {
            ((AppController) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);
            radio_btn_production.setChecked(true);
        } else {
            ((AppController) getApplication()).setAppEnvironment(AppEnvironment.SANDBOX);
            radio_btn_sandbox.setChecked(true);
        }
        setupCitrusConfigs();


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(isPlanPage == true) {
            isPlanPage = false;
            launchPayUMoneyFlow();
        }else {

            if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
                PayUmoneyFlowManager.logoutUser(getApplicationContext());
            }
            removeSharedPref();
            Intent i = new Intent(MakePaymentActivity.this, SubscriptionPlanActivity.class);
            startActivity(i);
            finishActivity(0);

        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
                    PayUmoneyFlowManager.logoutUser(getApplicationContext());
                }
                removeSharedPref();
                Intent i = new Intent(MakePaymentActivity.this, SubscriptionPlanActivity.class);
                startActivity(i);
                finishActivity(0);

            }
        });
    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }


    private void setUpUserDetails() {
        /*userDetailsPreference = getSharedPreferences(AppPreference.USER_DETAILS, MODE_PRIVATE);
        userEmail = userDetailsPreference.getString(AppPreference.USER_EMAIL, mAppPreference.getDummyEmail());
        userMobile = userDetailsPreference.getString(AppPreference.USER_MOBILE, mAppPreference.getDummyMobile());
        email_et.setText(userEmail);
        mobile_et.setText(userMobile);
        amount_et.setText(mAppPreference.getDummyAmount());*/
        restoreAppPref();


    }

    private void restoreAppPref() {
        //Set Up Disable Options
        switch_disable_wallet.setChecked(mAppPreference.isDisableWallet());
        switch_disable_cards.setChecked(mAppPreference.isDisableSavedCards());
        switch_disable_netBanks.setChecked(mAppPreference.isDisableNetBanking());

        //Set Up saved theme pref
        switch (AppPreference.selectedTheme) {
            case -1:
                radio_btn_default.setChecked(true);
                break;
            case R.style.AppTheme_pink:
                radio_btn_theme_pink.setChecked(true);
                break;
            case R.style.AppTheme_Grey:
                radio_btn_theme_grey.setChecked(true);
                break;
            case R.style.AppTheme_purple:
                radio_btn_theme_purple.setChecked(true);
                break;
            case R.style.AppTheme_Green:
                radio_btn_theme_green.setChecked(true);
                break;
            default:
                radio_btn_default.setChecked(true);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        payNowButton.setEnabled(true);

        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(View.GONE);
        }
    }

    /**
     * This function sets the mode to PRODUCTION in Shared Preference
     */
    private void selectProdEnv() {

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((AppController) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);
                editor = settings.edit();
                editor.putBoolean("is_prod_env", true);
                editor.apply();

                setupCitrusConfigs();
            }
        }, AppPreference.MENU_DELAY);
    }


    private void setupCitrusConfigs() {
        AppEnvironment appEnvironment = ((AppController) getApplication()).getAppEnvironment();
        if (appEnvironment == AppEnvironment.PRODUCTION) {
//            Toast.makeText(MakePaymentActivity.this, "Environment Set to Production", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(MakePaymentActivity.this, "Environment Set to SandBox", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This function sets the layout for activity
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_make_payment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                } else {
                    //Failure Transaction
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();

                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();

            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }
        }
    }


    private void initListeners() {

        radioGroup_color_theme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                mAppPreference.setOverrideResultScreen(false);

                switch (i) {
                    case R.id.radio_btn_theme_default:
                        AppPreference.selectedTheme = -1;
                        break;
                    case R.id.radio_btn_theme_pink:
                        AppPreference.selectedTheme = R.style.AppTheme_pink;
                        break;
                    case R.id.radio_btn_theme_grey:
                        AppPreference.selectedTheme = R.style.AppTheme_Grey;
                        break;
                    case R.id.radio_btn_theme_purple:
                        AppPreference.selectedTheme = R.style.AppTheme_purple;
                        break;
                    case R.id.radio_btn_theme_green:
                        AppPreference.selectedTheme = R.style.AppTheme_Green;
                        break;
                    default:
                        AppPreference.selectedTheme = -1;
                        break;
                }
            }
        });

        selectProdEnv();

    }

    /**
     * This function prepares the data for payment and launches payumoney plug n play sdk
     */
    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText(((EditText) findViewById(R.id.status_page_et)).getText().toString());

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle(((EditText) findViewById(R.id.activity_title_et)).getText().toString());

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
//            amount = Double.parseDouble(amount_et.getText().toString());
            String sAmount = getIntent().getExtras().getString(CONSTANTS.AMOUNT_PAY);
//            String sAmount = "1";

            amount = Double.parseDouble(sAmount);


        } catch (Exception e) {
            e.printStackTrace();
        }
        String txnId = System.currentTimeMillis() + "";
        String phone = prefs.getString(CONSTANTS.LOGGED_MOB);
//        String phone = mobile_til.getEditText().getText().toString().trim();
//        String productName = mAppPreference.getProductInfo();
        String productName = "IITIIMShadi Payment";
        String firstName = prefs.getString(CONSTANTS.LOGGED_USERNAME);
//        String firstName = mAppPreference.getFirstName();
        String email = prefs.getString(CONSTANTS.LOGGED_EMAIL);
//        String email = email_til.getEditText().getText().toString().trim();
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";

        AppEnvironment appEnvironment = ((AppController) getApplication()).getAppEnvironment();
        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)

                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());

        try {
            mPaymentParams = builder.build();

            /*
            * Hash should always be generated from your server side.
            * */
//            generateHashFromServer(mPaymentParams);

/*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * */
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            if (AppPreference.selectedTheme != -1) {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,MakePaymentActivity.this, AppPreference.selectedTheme,mAppPreference.isOverrideResultScreen());
            } else {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,MakePaymentActivity.this, R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());
            }
        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            payNowButton.setEnabled(true);
        }
    }

    /**
     * Thus function calculates the hash for transaction
     * @param paymentParam payment params of transaction
     * @return payment params along with calculated merchant hash
     */
    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();

        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        AppEnvironment appEnvironment = ((AppController) getApplication()).getAppEnvironment();
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }

    public void removeSharedPref(){
        editor.remove("is_prod_env");
        editor.clear();
        editor.commit();
    }

}