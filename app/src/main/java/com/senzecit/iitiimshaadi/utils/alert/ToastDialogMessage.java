package com.senzecit.iitiimshaadi.utils.alert;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;

/**
 * Created by senzec on 1/12/17.
 */

public class ToastDialogMessage {

    private static ToastDialogMessage alertClass = null;
    private Dialog dialog;

    private ToastDialogMessage()
    {

    }
    public static ToastDialogMessage getInstance()
    {
        if(alertClass == null)
        {
            alertClass = new ToastDialogMessage();
        }
        return alertClass;
    }

    public void showToast(Context activity, String msg){

        LayoutInflater inflater = LayoutInflater.from(activity);
        View layout = inflater.inflate(R.layout.toast_dialog, null);

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.logo_main);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void showToast(Activity activity, String msg){

        LayoutInflater inflater = LayoutInflater.from(activity);
        View layout = inflater.inflate(R.layout.toast_dialog, null);

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.logo_main);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }



}