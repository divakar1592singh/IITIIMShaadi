package com.senzecit.iitiimshaadi.utils;

import android.util.Log;

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

    public int getProfilePicName(String gender){
        prefs = AppController.getInstance().getPrefs();

//        String gender = prefs.getString(CONSTANTS.GENDER_TYPE);

        if(gender.equalsIgnoreCase("Male")){
            return R.drawable.ic_male_default;
        }else {
            return R.drawable.ic_female_default;
        }
    }


/*
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
    }*/

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

    public String getWebURL(int pos){
        if(pos == 0){
            return CONSTANTS.MEDIA_URL_1;
        }else if(pos == 1){
            return CONSTANTS.MEDIA_URL_2;
        }else if(pos == 2){
            return CONSTANTS.MEDIA_URL_3;
        }else if(pos == 3){
            return CONSTANTS.MEDIA_URL_4;
        }else if(pos == 4){
            return CONSTANTS.MEDIA_URL_5;
        }else if(pos == 5){
            return CONSTANTS.MEDIA_URL_6;
        }else if(pos == 6){
            return CONSTANTS.MEDIA_URL_7;
        }else if(pos == 7){
            return CONSTANTS.MEDIA_URL_8;
        }else if(pos == 8){
            return CONSTANTS.MEDIA_URL_9;
        }else if(pos == 9){
            return CONSTANTS.MEDIA_URL_10;
        }else if(pos == 10){
            return CONSTANTS.MEDIA_URL_11;
        }else if(pos == 11){
            return CONSTANTS.MEDIA_URL_12;
        }else if(pos == 12){
            return CONSTANTS.MEDIA_URL_13;
        }else if(pos == 13){
            return CONSTANTS.MEDIA_URL_14;
        }else if(pos == 14){
            return CONSTANTS.MEDIA_URL_15;
        }else if(pos == 15){
            return CONSTANTS.MEDIA_URL_16;
        }else if(pos == 16){
            return CONSTANTS.MEDIA_URL_17;
        }else if(pos == 17){
            return CONSTANTS.MEDIA_URL_18;
        }else if(pos == 18){
            return CONSTANTS.MEDIA_URL_19;
        }else if(pos == 19){
            return CONSTANTS.MEDIA_URL_20;
        }else {
            return "https://iitiimshaadi.com";
        }
    }




}