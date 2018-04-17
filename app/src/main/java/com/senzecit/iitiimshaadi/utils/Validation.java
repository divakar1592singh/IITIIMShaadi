package com.senzecit.iitiimshaadi.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.senzecit.iitiimshaadi.utils.alert.ToastDialogMessage;

/**
 * Created by divakar on 01/04/18.
 */

public class Validation {

    private static Validation instance = null;

    private Validation(){}

    public static Validation handler(){
        if(instance == null){
            synchronized (Validation.class){
                if(instance == null){
                    return instance = new Validation();
                }
            }
        }
        return instance;
    }
//  EMPTY
    public boolean isNotEmptyString(Activity activity, String val, String errMessage){
        if(!TextUtils.isEmpty(val)){
            return true;
        }else {
            ToastDialogMessage.getInstance().showToast(activity, errMessage);
            return false;
        }
    }
    public boolean isNotEmptyString(Context activity, String val, String errMessage){
        if(!TextUtils.isEmpty(val)){
            return true;
        }else {
            ToastDialogMessage.getInstance().showToast(activity, errMessage);
            return false;
        }
    }

    public boolean isNotEmpty(Activity activity, String val, String errMessage){
        if(!TextUtils.isEmpty(val)){
            return true;
        }else {
            return false;
        }
    }
    // EMAIL


}
