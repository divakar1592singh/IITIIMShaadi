package com.senzecit.iitiimshaadi.utils.alert;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

/**
 * Created by senzec on 1/12/17.
 */

public class NetworkDialogHelper {

    private static NetworkDialogHelper alertClass = null;
    private Dialog dialog;

    private NetworkDialogHelper()
    {

    }
    public static NetworkDialogHelper getInstance()
    {
        if(alertClass == null)
        {
            alertClass = new NetworkDialogHelper();
        }
        return alertClass;
    }

    public void showDialog(Activity activity){
            new AlertDialog.Builder(activity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No Network")
                    .setMessage("Check Your Internet")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    })
                    .show();
        }

    public void showDialog(Context activity){
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("No Network")
                .setMessage("Check Your Internet")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    public void closeDialog(){
        try {
            dialog.dismiss();
        }catch (NullPointerException npe){
            Log.e("TAG", "#Error : "+npe, npe);
        }
    }

    }