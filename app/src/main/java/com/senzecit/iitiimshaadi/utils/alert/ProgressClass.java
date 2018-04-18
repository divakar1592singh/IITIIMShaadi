package com.senzecit.iitiimshaadi.utils.alert;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.DataHandlingClass;

/**
 * Created by senzec on 1/12/17.
 */

public class ProgressClass {

    private static final String TAG = "ProgressClass";
    private static ProgressClass progressClass = null;
    private Dialog dialog;

    private ProgressClass()
    {

    }
    public static ProgressClass getProgressInstance()
    {
        if(progressClass == null) {
            synchronized (ProgressClass.class) {
                if (progressClass == null) {
                    progressClass = new ProgressClass();
                }
            }
        }
        return progressClass;
    }

        public void showDialog(Activity activity){
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.layout_progress_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            ImageView progressImage = dialog.findViewById(R.id.idPregress);
            try {
                Glide.with(activity)
                        .load(DataHandlingClass.getInstance().getProgressId())
                        .into(progressImage);


                if(dialog.isShowing()){
                    dialog.dismiss();
                }else {
                    dialog.show();
                }
            }catch (IllegalArgumentException e){
                Log.e(TAG, "#Errro : "+e, e);
            }
        }

    public void showDialog(Context activity){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView progressImage = dialog.findViewById(R.id.idPregress);
        Glide.with(activity)
                .load(DataHandlingClass.getInstance().getProgressId())
                .into(progressImage);

            dialog.show();
    }


    public void stopProgress(){
        if(dialog.isShowing())
            dialog.dismiss();
        }

    }