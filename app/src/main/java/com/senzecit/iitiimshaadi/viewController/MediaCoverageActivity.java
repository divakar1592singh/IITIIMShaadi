package com.senzecit.iitiimshaadi.viewController;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.MediaAdapter;
import com.senzecit.iitiimshaadi.utils.DataHandlingClass;
import com.senzecit.iitiimshaadi.utils.Navigator;

import java.util.ArrayList;

public class MediaCoverageActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    GridView mGridView;
    ArrayList<String> imageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_media_coverage);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        init();
        mBack.setOnClickListener(this);

        imageItem = new ArrayList<>();
        for(int i = 1; i<=20; i++){
            imageItem.add("img_media"+i);
        }

        MediaAdapter adapter = new MediaAdapter(this, imageItem);
        mGridView.setAdapter(adapter);
    }
    private void init(){
        mToolbar= findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Media Coverage");

        mGridView = findViewById(R.id.gridView);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position != 14) {
                    String URL = DataHandlingClass.getInstance().getWebURL(position);
                    Bundle bundleWeb = new Bundle();
                    bundleWeb.putString("url", URL);
                    Navigator.getClassInstance().navigateToActivityWithBundleData(MediaCoverageActivity.this, WebActivity.class, bundleWeb);
                }else {
//                    Toast.makeText(MediaCoverageActivity.this, "Webs", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                MediaCoverageActivity.this.finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

}
