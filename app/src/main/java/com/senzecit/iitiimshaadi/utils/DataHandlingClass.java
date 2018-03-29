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
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

/**
 * Created by senzec on 1/12/17.
 */

public class DataHandlingClass {

    private static DataHandlingClass alertClass = null;
    AppPrefs prefs;

    private DataHandlingClass()
    {

    }
    public static DataHandlingClass getInstance()
    {
        if(alertClass == null)
        {
            alertClass = new DataHandlingClass();
        }
        return alertClass;
    }

    public int getProfilePicName(Activity activity){
        prefs = AppController.getInstance().getPrefs();

        String gender = prefs.getString(CONSTANTS.GENDER_TYPE);

        if(gender.equalsIgnoreCase("Male")){
            return R.drawable.ic_male_default;
        }else {
            return R.drawable.ic_female_default;
        }


    }

    public int getProfilePicName(Context activity){
        prefs = AppController.getInstance().getPrefs();

        String gender = prefs.getString(CONSTANTS.GENDER_TYPE);

        if(gender.equalsIgnoreCase("Male")){
            return R.drawable.ic_male_default;
        }else {
            return R.drawable.ic_female_default;
        }


    }





}