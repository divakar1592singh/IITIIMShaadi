package com.senzecit.iitiimshaadi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.AlbumAdapter;

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack,mAlbumLogo;
    GridView mGridView;
    LinearLayout mAddBtnLL,mAddImage;
    FrameLayout mNoImageFoundFL,mImageFoundFL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_album);
        init();
        mBack.setOnClickListener(this);
        AlbumAdapter albumAdapter = new AlbumAdapter(this);
        mGridView.setAdapter(albumAdapter);
        mAddBtnLL.setOnClickListener(this);
        mAddImage.setOnClickListener(this);
    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mAlbumLogo = (ImageView) findViewById(R.id.albumLogoIV);
        mBack.setVisibility(View.VISIBLE);
        mAlbumLogo.setVisibility(View.VISIBLE);
        mTitle.setText("Album");

        mGridView = (GridView) findViewById(R.id.gridView);
        mAddBtnLL = (LinearLayout) findViewById(R.id.idAddBtnLL);
        mAddImage = (LinearLayout) findViewById(R.id.idAddImgLL);
        mNoImageFoundFL = (FrameLayout) findViewById(R.id.idAlbumNoImageFL);
        mImageFoundFL = (FrameLayout) findViewById(R.id.idAlbumImageFoundFL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
//                startActivity(new Intent(AlbumActivity.this, PaidSubscriberDashboardActivity.class));
                AlbumActivity.this.finish();
                break;
            case R.id.idAddBtnLL:
                mNoImageFoundFL.setVisibility(View.GONE);
                mImageFoundFL.setVisibility(View.VISIBLE);
                break;
            case R.id.idAddImgLL:
//                mNoImageFoundFL.setVisibility(View.GONE);
//                mImageFoundFL.setVisibility(View.VISIBLE);
                showToast("Functionality Development Part");
                break;
        }
    }

    public void showToast(String msg){

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_dialog,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.logo_main);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
    @Override
    protected void onStop() {
        super.onStop();
        AlbumActivity.this.finish();
    }
}
