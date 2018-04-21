package com.senzecit.iitiimshaadi.viewController;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.model.common.contact_us.ContactUsRequest;
import com.senzecit.iitiimshaadi.utils.CaptchaClass;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, RxNetworkingForObjectClass.CompletionHandler {

    private static final Double latitude = 28.692467;
    private static final Double longitude = 77.17796399999997;
    private static final int TAG_CODE_PERMISSION_LOCATION = 3;
    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    RxNetworkingForObjectClass rxNetworkingClass;
    EditText mFullNameET, mEmailET, mPhoneET, mSubjectET, mMessageET, mVerifyCaptchaET;
    Button mCaptchaBtn, mSendBtn;
    ImageView mRefreshIV;
    private GoogleMap mMap;
    AppPrefs prefs;

    /**
     * Helping Method Section
     */
    public final static boolean isValidEmail(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_contact_us);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        prefs = AppPrefs.getInstance(this);
        init();
        handleView();
        mBack.setOnClickListener(this);

        rxNetworkingClass = RxNetworkingForObjectClass.getInstance();
        rxNetworkingClass.setCompletionHandler(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.toolbar_title);
        mBack = findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Contact Us");

        mFullNameET = findViewById(R.id.fullNameET);
        mEmailET = findViewById(R.id.emailET);
        mPhoneET = findViewById(R.id.phoneET);
        mSubjectET = findViewById(R.id.subjectET);
        mMessageET = findViewById(R.id.messageET);
        mVerifyCaptchaET = findViewById(R.id.verifyCaptchaET);

        mRefreshIV = findViewById(R.id.refreshIV);
        mCaptchaBtn = findViewById(R.id.captchaBtn);
        mSendBtn = findViewById(R.id.sendBtn);
    }

    public void handleView() {
        mRefreshIV.setOnClickListener(this);
        mCaptchaBtn.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);

        showCaptcha();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backIV:
                onBackNavigation();
                break;
            case R.id.captchaBtn:

                break;
            case R.id.sendBtn:
                if(NetworkClass.getInstance().checkInternet(ContactUsActivity.this) == true){
                checkNewUserValidation();
                }else {
                    NetworkDialogHelper.getInstance().showDialog(ContactUsActivity.this);
                }
                break;
            case R.id.refreshIV:
                showCaptcha();
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

    public void showCaptcha(){

        CaptchaClass captcha = new CaptchaClass();
        String str = captcha.generateCaptcha();
        System.out.println(str);
        mCaptchaBtn.setText(str);
    }

    /**
     * Check Validation Section
     */
    public void checkNewUserValidation() {

        String sName = mFullNameET.getText().toString().trim();
        String sEmail = mEmailET.getText().toString().trim();
        String sPhone = mPhoneET.getText().toString().trim();
        String sSubject = mSubjectET.getText().toString().trim();
        String sMessage = mMessageET.getText().toString().trim();

        String userEnterCaptcha = mVerifyCaptchaET.getText().toString().trim();
        String serverSideCaptcha = mCaptchaBtn.getText().toString().trim();
//        String sCollege = mCollegeNameET.getText().toString().trim();

        if (!sName.isEmpty()) {
            if (isValidEmail(sEmail)) {
                if (isValidMobile(sPhone)) {
                    if (!sSubject.isEmpty()) {
                        if (serverSideCaptcha.equals(userEnterCaptcha)) {

                            callWebServiceForContactUs();
                        } else {
                            mVerifyCaptchaET.requestFocus();
                            mVerifyCaptchaET.setText("");
                            AlertDialogSingleClick.getInstance().showDialog(ContactUsActivity.this, "Alert!", "Captcha didn't match");
                        }
                    } else {
                        mSubjectET.requestFocus();
                        AlertDialogSingleClick.getInstance().showDialog(ContactUsActivity.this, "Alert!", "Subject  can't Empty");
                    }
                } else {
                    mPhoneET.requestFocus();
                    AlertDialogSingleClick.getInstance().showDialog(ContactUsActivity.this, "Alert!", "Phone not correct");
                }
            } else {
                mEmailET.requestFocus();
                AlertDialogSingleClick.getInstance().showDialog(ContactUsActivity.this, "Alert!", "Email not correct");
            }
        } else {
            mFullNameET.requestFocus();
            AlertDialogSingleClick.getInstance().showDialog(ContactUsActivity.this, "Alert!", "Name can't Empty");
        }


    }

    /** API Section */

    public void callWebServiceForContactUs() {

        String sUsername = mFullNameET.getText().toString().trim();
        String sEmail = mEmailET.getText().toString().trim();
        String sPhone = mPhoneET.getText().toString().trim();
        String sSubject = mSubjectET.getText().toString().trim();
        String sMessage = mMessageET.getText().toString().trim();

        ContactUsRequest request = new ContactUsRequest();
        request.name = sUsername;
        request.email = sEmail;
        request.phone = sPhone;
        request.subject = sSubject;
        request.message = sMessage;

        RxNetworkingForObjectClass.getInstance().callWebServiceForRxNetworking(ContactUsActivity.this, CONSTANTS.CONTACT_US_PATH, request, null);

    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    @Override
    public void handle(JSONObject object, String methodName) {

            try {
                String success = object.getJSONObject("message").getString("success");
                AlertDialogSingleClick.getInstance().showDialog(ContactUsActivity.this, "Alert", success);

            } catch (JSONException e) {
                e.printStackTrace();
                AlertDialogSingleClick.getInstance().showDialog(ContactUsActivity.this, "Alert", "Something went wrong!");
            }
    }

    @Override
    public void onServiceError(ANError error, String methodName) {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackNavigation();
    }

    public void onBackNavigation(){

        try{
            String userType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
            if (userType.equalsIgnoreCase("paid_subscriber_viewer")) {

                Intent intent = new Intent(this, PaidSubscriberDashboardActivity.class);
                startActivity(intent);
            } else if (userType.equalsIgnoreCase("subscriber_viewer")) {

                Intent intent = new Intent(this, SubscriberDashboardActivity.class);
                startActivity(intent);
            } else if (userType.equalsIgnoreCase("subscriber")) {

                Intent intent = new Intent(this, SubscriberDashboardActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, IntroSliderWebActivity.class);
                startActivity(intent);
            }
        }catch (NullPointerException npe){

            Log.e("TAG", "#Error : "+npe, npe);
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();

        }
    }


}
