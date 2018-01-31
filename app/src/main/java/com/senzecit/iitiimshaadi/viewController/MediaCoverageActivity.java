package com.senzecit.iitiimshaadi.viewController;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.MediaAdapter;

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
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Media Coverage");

        mGridView = (GridView) findViewById(R.id.gridView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                MediaCoverageActivity.this.finish();
                break;
        }
    }

}
