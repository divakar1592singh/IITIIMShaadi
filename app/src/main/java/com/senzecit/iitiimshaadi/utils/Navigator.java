package com.senzecit.iitiimshaadi.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.senzecit.iitiimshaadi.R;


/**
 * Created by Divakar on 4/21/2017.
 */

public class Navigator {

    private static Navigator instanceClass = null;

    private Navigator()
    { }

    public static Navigator getClassInstance()
    {
        if( instanceClass == null)
        {   instanceClass = new Navigator();    }

        return instanceClass;
    }


    public void navigateToActivity(Activity activity, Class<?> toClass) {
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void navigateToActivityWithData(Activity activity, Class<?> toClass, String data, int num) {
        Intent intent = new Intent(activity, toClass);
        intent.putExtra("data", data);
        intent.putExtra("num", num);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void navigateToActivityWithBundleData(Activity activity, Class<?> toClass, Bundle bundle) {
        Intent intent = new Intent(activity, toClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
