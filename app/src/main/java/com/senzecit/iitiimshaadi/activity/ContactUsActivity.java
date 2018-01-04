package com.senzecit.iitiimshaadi.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.senzecit.iitiimshaadi.R;

import java.util.List;
import java.util.Locale;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;

    private GoogleMap mMap;
    private static final Double latitude = 28.692467;
    private static final Double longitude = 77.17796399999997;
    private static final int TAG_CODE_PERMISSION_LOCATION = 3;
    EditText mFullNameET, mEmailET, mPhoneET, mSubjectET, mMessageET, mVerifyCaptchaET;
    Button mCaptchaBtn, mSendBtn;
    ImageView mRefreshIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_contact_us);
        init();
        handleView();
        mBack.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Contact Us");

        mFullNameET = (EditText)findViewById(R.id.fullNameET);
        mEmailET = (EditText)findViewById(R.id.emailET);
        mPhoneET = (EditText)findViewById(R.id.phoneET);
        mSubjectET = (EditText)findViewById(R.id.subjectET);
        mMessageET = (EditText)findViewById(R.id.messageET);
        mVerifyCaptchaET = (EditText)findViewById(R.id.verifyCaptchaET);

        mRefreshIV = (ImageView)findViewById(R.id.refreshIV);
        mCaptchaBtn = (Button)findViewById(R.id.captchaBtn) ;
        mSendBtn = (Button)findViewById(R.id.sendBtn) ;
    }

    public void handleView(){
        mRefreshIV.setOnClickListener(this);
        mCaptchaBtn.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backIV:
                ContactUsActivity.this.finish();
                break;
            case R.id.captchaBtn:

                break;
            case R.id.sendBtn:

                break;
             case R.id.refreshIV:

                break;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
            mMap.clear();
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    TAG_CODE_PERMISSION_LOCATION);
        }
        LatLng locationA = new LatLng(latitude, longitude);
        // Changing marker icon
        mMap.addMarker(new MarkerOptions()
                .position(locationA)
                .title("IITIIMShadi")
                .snippet(getCompleteAddressString(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_a_36)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationA));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

//                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current address", strReturnedAddress.toString());
            } else {
                Log.w("My Current address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current address", "Canont get Address!");
        }
        return strAdd;
    }


}
