package com.senzecit.iitiimshaadi.viewController;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.Navigator;

public class IntroSliderWebActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "IntroSliderWebActivity";
    Button mLogin, mRegistration;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_intro_slider_web);

    }

    @Override
    protected void onStart() {
        super.onStart();

        init();
        handleWeb();
    }

    private void init() {
        mWebView = (WebView) findViewById(R.id.idIntroWeb);
        mLogin = (Button) findViewById(R.id.idLoginBtn);
        mRegistration = (Button) findViewById(R.id.idRegisterBtn);

    }

    public void handleWeb()
    {
        mLogin.setOnClickListener(this);
        mRegistration.setOnClickListener(this);

        mWebView.setBackgroundColor(Color.TRANSPARENT);
        mWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        if (Build.VERSION.SDK_INT >= 11) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
//        mWebView.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            public void onLoadResource(WebView view, String url) {
//                // Check to see if there is a progress dialog
//                if (progressDialog == null) {
//                    // If no progress dialog, make one and set message
//                    progressDialog = new ProgressClass(IntroSliderWebActivity.this);
//                    progressDialog.setMessage("Loading please wait...");
//                    progressDialog.show();
//
//                    // Hide the webview while loading
//                    mWebView.setEnabled(false);
//                }
//            }
//
//            public void onPageFinished(WebView view, String url) {
//                // Page is done loading;
//                // hide the progress dialog and show the webview
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                    mWebView.setEnabled(true);
//                }
//            }
//
//        });
        mWebView.loadUrl("file:///android_asset/IITM2.html");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idLoginBtn:

//                Navigator.getClassInstance().navigateToActivity(this, SubscriberDashboardActivity.class);
                startActivity(new Intent(IntroSliderWebActivity.this, LoginActivity.class));
                break;
            case R.id.idRegisterBtn:
                Navigator.getClassInstance().navigateToActivity(this, QuickRegistrationActivity.class);
//                startActivity(new Intent(IntroSliderWebActivity.this, QuickRegistrationActivity.class));
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        ProgressDialog progressDialog;
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return false;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            if (progressDialog == null) {
                // If no progress dialog, make one and set message
                progressDialog = new ProgressDialog(IntroSliderWebActivity.this);
                progressDialog.setMessage("Loading please wait...");
                progressDialog.show();
                progressDialog.setCancelable(false);ProgressDialog progressDialog;

                // Hide the webview while loading
                mWebView.setEnabled(false);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
           try {
               if (progressDialog != null) {
                   if (progressDialog.isShowing()) {
                       progressDialog.dismiss();
                       progressDialog = null;
                       mWebView.setEnabled(true);
//                }
                   }
               }
           }catch (NullPointerException npe){
               Log.e(TAG, "#Error"+npe, npe);
           }

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        IntroSliderWebActivity.this.finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit!")
                .setMessage("Are you sure?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        IntroSliderWebActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
