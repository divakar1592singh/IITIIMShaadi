package com.senzecit.iitiimshaadi.utils.alert;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

/**
 * Created by senzec on 1/12/17.
 */

public class AlertDialogTwoClick {

    private static AlertDialogTwoClick alertClass = null;
    private Dialog dialog;

    private AlertDialogTwoClick()
    {

    }
    public static AlertDialogTwoClick getInstance()
    {
        if(alertClass == null)
        {
            alertClass = new AlertDialogTwoClick();
        }
        return alertClass;
    }


        public void showDialog(Activity activity, String title, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_two_click);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView titleTxt = dialog.findViewById(R.id.txt_file_path);
            titleTxt.setText(title);
            TextView msgTxt = dialog.findViewById(R.id.idMsg);
            msgTxt.setText(msg);

            Button dialogBtn_cancel = dialog.findViewById(R.id.btn_cancel);
            dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            Button dialogBtn_okay = dialog.findViewById(R.id.btn_okay);
            dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            dialog.show();
        }
    }