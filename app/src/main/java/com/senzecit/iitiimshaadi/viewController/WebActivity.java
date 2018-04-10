package com.senzecit.iitiimshaadi.viewController;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appdatasearch.Feature;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;


public class WebActivity extends AppCompatActivity {

    WebView jcbWebpage;
    String url;
//    ProgressDialog progressDialog;
    String category_name = null;
    int cate_id = 0;

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_web);

        initView();
//        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + "JCB" + "</font>"));
        jcbWebpage = (WebView) findViewById(R.id.idChevyWebPage);

        Bundle bundleWeb = getIntent().getExtras();

        // --- CHECK NETWORK STATE
//        NetworkClass networkClass = new NetworkClass(WebActivity.this);
        boolean network_state = NetworkClass.getInstance().checkInternet(WebActivity.this);
        if(network_state == true)
        {
            try {
                //url = getIntent().getExtras().getString("url").toString();
                url = bundleWeb.getString("url").toString();

            }catch (NullPointerException npe)
            {
                Log.e("TAG", "Error" + npe);
            }
            WebSettings webSettings = jcbWebpage.getSettings();
            webSettings.setJavaScriptEnabled(true);

            jcbWebpage.getSettings().setBuiltInZoomControls(true);
            jcbWebpage.getSettings().setUseWideViewPort(true);
            jcbWebpage.getSettings().setLoadWithOverviewMode(true);

            /*progressDialog = new ProgressDialog(WebActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();*/

            ProgressClass.getProgressInstance().showDialog(WebActivity.this);
            jcbWebpage.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    /*if (progressDialog.isShowing()) {
                        progressDialog.dism iss();
                    }*/
                    ProgressClass.getProgressInstance().stopProgress();
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                    ProgressClass.getProgressInstance().stopProgress();
                }
            });
            jcbWebpage.loadUrl(url);
        }
        else if(network_state == false) {
            new AlertDialog.Builder(WebActivity.this)
                    .setTitle("Internet Connection")
                    .setMessage("Oops! Check your internet")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Navigator.getClassInstance().navigateToActivity(WebActivity.this, WebActivity.class);
                        }
                    })
                    .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Navigator.getClassInstance().navigateToActivity(WebActivity.this, MediaCoverageActivity.class);
                        }
                    })
                    .show();
        }
    }

    private void initView() {

        mToolbar= (Toolbar) findViewById(R.id.toolbar);

        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Media Coverage");
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.getClassInstance().navigateToActivity(WebActivity.this, MediaCoverageActivity.class);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.backIV) {
            Navigator.getClassInstance().navigateToActivity(WebActivity.this, MediaCoverageActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

