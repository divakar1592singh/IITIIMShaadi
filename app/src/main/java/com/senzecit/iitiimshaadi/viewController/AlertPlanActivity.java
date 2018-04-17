package com.senzecit.iitiimshaadi.viewController;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        init();
        mBack.setOnClickListener(this);
        mUpgrade.setOnClickListener(this);
        mFeature.setOnClickListener(this);
    }

    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Subscription Alert");

        mUpgrade = findViewById(R.id.idtextUpgrade);
        mFeature = findViewById(R.id.idTextFeature);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                AlertPlanActivity.this.finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

}
