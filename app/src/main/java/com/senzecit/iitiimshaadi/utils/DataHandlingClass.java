package com.senzecit.iitiimshaadi.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.alert.ToastDialogMessage;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

/**
 * Created by senzec on 1/12/17.
 */

public class DataHandlingClass {

    private static String TAG = "DataHandlingClass";
    private static DataHandlingClass instances = null;
    AppPrefs prefs;

    private DataHandlingClass()
    { }

    public static DataHandlingClass getInstance()
    {
        if(instances == null)
        {
            return instances = new DataHandlingClass();
        }
        return instances;
    }

    public String token(){
        prefs = AppController.getInstance().getPrefs();
        String sToken = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        if(sToken == null){
            ToastDialogMessage.getInstance().showToast(AppController.context, "Login Again");
            return "";
        }else {
            return sToken;
        }
    }

    public int getProfilePicName(){
        prefs = AppController.getInstance().getPrefs();

        String gender = prefs.getString(CONSTANTS.GENDER_TYPE);

        if(gender.equalsIgnoreCase("Male")){
            return R.drawable.ic_male_default;
        }else {
            return R.drawable.ic_female_default;
        }
    }



    public int getOtherProfile(String gender){

        try {
            if (gender.equalsIgnoreCase("Male")) {
                return R.drawable.ic_male_default;
            } else {
                return R.drawable.ic_female_default;
            }
        }catch (NullPointerException npe){
            Log.e(TAG, "#Error : "+npe, npe);
            return R.drawable.ic_male_default;
        }
    }

    public int getProgressId(){
        return R.drawable.progress4;
    }

    public String checkNullString(Object input){
        try{
            if(input != null){
                return input.toString();
            }else {
                return "";
            }
        }catch (NullPointerException npe){
            return "";
        }
    }






}