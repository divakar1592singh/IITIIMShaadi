package com.senzecit.iitiimshaadi.viewController;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.senzecit.iitiimshaadi.R;

public class PremierServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_premier_services);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

}
