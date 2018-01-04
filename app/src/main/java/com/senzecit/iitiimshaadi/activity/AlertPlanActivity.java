package com.senzecit.iitiimshaadi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

public class AlertPlanActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    TextView mUpgrade,mFeature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_alert_plan);
        init();
        mBack.setOnClickListener(this);
        mUpgrade.setOnClickListener(this);
        mFeature.setOnClickListener(this);
    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Subscription Alert");

        mUpgrade = (TextView) findViewById(R.id.idtextUpgrade);
        mFeature = (TextView) findViewById(R.id.idTextFeature);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                AlertPlanActivity.this.finish();
                break;
        }
    }
}
