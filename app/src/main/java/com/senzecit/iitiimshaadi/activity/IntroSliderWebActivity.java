package com.senzecit.iitiimshaadi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.DepthPageTransformer;
import com.senzecit.iitiimshaadi.utils.FixedSpeedScroller;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.custom_view_pager.ViewPagerCustomDuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class IntroSliderWebActivity extends AppCompatActivity implements View.OnClickListener {

    Button mLogin, mRegistration;
    WebView mWebView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_intro_slider_web);
        init();
        handleWeb();

    }

    private void init() {
        mWebView = (WebView) findViewById(R.id.idIntroWeb);
        mLogin = (Button) findViewById(R.id.idLoginBtn);
        mRegistration = (Button) findViewById(R.id.idRegisterBtn);
        mLogin.setOnClickListener(this);
        mRegistration.setOnClickListener(this);
    }

    public void handleWeb() {
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
                Navigator.getClassInstance().navigateToActivity(this, LoginActivity.class);
//                startActivity(new Intent(IntroSliderWebActivity.this, LoginActivity.class));
                break;
            case R.id.idRegisterBtn:
                Navigator.getClassInstance().navigateToActivity(this, RegistrationQuickActivity.class);
//                startActivity(new Intent(IntroSliderWebActivity.this, RegistrationQuickActivity.class));
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {
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
                progressDialog.setCancelable(false);

                // Hide the webview while loading
                mWebView.setEnabled(false);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
                mWebView.setEnabled(true);
//                }
            }
        }

    }

}
