package com.senzecit.iitiimshaadi.viewController;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.customdialog.CustomListAdapterDialog;
import com.senzecit.iitiimshaadi.customdialog.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView mTitle;
    ImageView mBack;
    ImageView mGeneralSettingIV,mNotificationSettingIV,mDeactiveAccountSettingIV;
    LinearLayout mGeneralSettingLL,mNotificationSettingLL,mDeactiveAccountLL;
    RelativeLayout mSendRL, mAddsRL;
    boolean generalSetting,notificationSetting,deactiveAccount = true;
    TextView mMemberSendTV, mMemberAddsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);
        init();

        mGeneralSettingIV.setOnClickListener(this);
        mNotificationSettingIV.setOnClickListener(this);
        mDeactiveAccountSettingIV.setOnClickListener(this);
        mBack.setOnClickListener(this);

        mSendRL.setOnClickListener(this);
        mAddsRL.setOnClickListener(this);

        mMemberSendTV.setOnClickListener(this);
        mMemberAddsTV.setOnClickListener(this);
    }

    private void init(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageView) findViewById(R.id.backIV);
        mBack.setVisibility(View.VISIBLE);
        mTitle.setText("Settings");

        mGeneralSettingIV = (ImageView) findViewById(R.id.generalSettingIV);
        mNotificationSettingIV = (ImageView) findViewById(R.id.notificationSttingIV);
        mDeactiveAccountSettingIV = (ImageView) findViewById(R.id.deactiveAccountIV);

        mGeneralSettingLL = (LinearLayout) findViewById(R.id.generalSettingLL);
        mNotificationSettingLL = (LinearLayout) findViewById(R.id.notificationSettingLL);
        mDeactiveAccountLL = (LinearLayout) findViewById(R.id.deactiveAccountLL);

        mSendRL = (RelativeLayout) findViewById(R.id.idSendRL);
        mAddsRL = (RelativeLayout) findViewById(R.id.idAddsRL);

        mMemberSendTV = (TextView) findViewById(R.id.idMemberSendTV);
        mMemberAddsTV = (TextView) findViewById(R.id.idMemberAddsTV);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.generalSettingIV:
                if(generalSetting){
                    mGeneralSettingLL.setVisibility(View.GONE);
                    generalSetting = false;
                }else{
                    mGeneralSettingLL.setVisibility(View.VISIBLE);
                    generalSetting = true;
                }
                break;
            case R.id.notificationSttingIV:
                if(notificationSetting){
                    mNotificationSettingLL.setVisibility(View.GONE);
                    notificationSetting = false;
                }else{
                    mNotificationSettingLL.setVisibility(View.VISIBLE);
                    notificationSetting = true;
                }
                break;
            case R.id.deactiveAccountIV:
                if(deactiveAccount){
                    mDeactiveAccountLL.setVisibility(View.GONE);
                    deactiveAccount = false;
                }else{
                    mDeactiveAccountLL.setVisibility(View.VISIBLE);
                    deactiveAccount = true;
                }
                break;
            case R.id.backIV:
                SettingsActivity.this.finish();
                break;
            case R.id.idSendRL:
                showBooleanData(mMemberSendTV);
                break;
            case R.id.idAddsRL:
                showBooleanData(mMemberAddsTV);
                break;
        }
    }

    public void showBooleanData(TextView textView){
        List<String> countryList = new ArrayList<>();
        countryList.add("Yes");
        countryList.add("No");

        showDialog(countryList, textView);
    }

    public Vector<Dialog> dialogs = new Vector<Dialog>();
    private void showDialog(List<String> dataList, final TextView textView) {
        int d_width = 100;
        int d_height = 50;
        final ArrayList<Model> models = new ArrayList<Model>();
        if(dataList.size()>0){
            for (int i = 0; i < dataList.size(); i++) {
                Model model = new Model();
                model.setName(dataList.get(i));
                models.add(model);
            }
        }else {
            for (int i = 0; i < 20; i++) {
                Model model = new Model();
                model.setName("senzecit " + i);
                models.add(model);
            }
        }

        final Dialog dialog = new Dialog(this, R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        View view = getLayoutInflater().inflate(R.layout.toast_layout, null);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.custom_list);
//		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ((Button) view.findViewById(R.id.button_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final CustomListAdapterDialog clad1 = new CustomListAdapterDialog(SettingsActivity.this, models);
        recyclerView.setAdapter(clad1);

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialogs.add(dialog);
        dialog.show();

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Window window = dialog.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setLayout(width - d_width, height - d_height);

        clad1.setOnItemClickListener(new CustomListAdapterDialog.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String id) {
                int positionInList = position % models.size();
                textView.setText(models.get(positionInList).getName());
                dialog.dismiss();

//				showDialog(130,50);
            }
        });
    }


}
