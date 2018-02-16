package com.senzecit.iitiimshaadi.utils.alert;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.Constants;

/**
 * Created by senzec on 1/12/17.
 */

public class AlertDialogSingleClick {

    private static AlertDialogSingleClick alertClass = null;
    private Dialog dialog;

    private AlertDialogSingleClick()
    {

    }
    public static AlertDialogSingleClick getInstance()
    {
        if(alertClass == null)
        {
            alertClass = new AlertDialogSingleClick();
        }
        return alertClass;
    }

    public void showDialog(Activity activity, String title, String msg){
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_single_click);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView titleTxt = (TextView) dialog.findViewById(R.id.txt_file_path);
            titleTxt.setText(title);
            TextView msgTxt = (TextView) dialog.findViewById(R.id.idMsg);
            msgTxt.setText(msg);

            Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
            dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
            dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            dialog.show();
        }

    public void showDialog(Context activity, String title, String msg){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_single_click);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTxt = (TextView) dialog.findViewById(R.id.txt_file_path);
        titleTxt.setText(title);
        TextView msgTxt = (TextView) dialog.findViewById(R.id.idMsg);
        msgTxt.setText(msg);

        Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        dialog.show();
    }


    public void closeDialog(){
        dialog.dismiss();
    }

    }