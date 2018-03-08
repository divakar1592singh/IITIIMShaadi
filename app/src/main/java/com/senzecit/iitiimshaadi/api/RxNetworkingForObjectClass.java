package com.senzecit.iitiimshaadi.api;

import android.app.Activity;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;

import org.json.JSONObject;

/**
 * Created by senzec on 6/3/18.
 */


public class RxNetworkingForObjectClass {

//    ------------------SINGLETON
    private static RxNetworkingForObjectClass instance = null;

    CompletionHandler handler;
    public void setCompletionHandler(CompletionHandler handler){
        this.handler = handler;
    }

    private RxNetworkingForObjectClass(){ }

    public static RxNetworkingForObjectClass getInstance()
    {
        if(instance == null)
        { instance = new RxNetworkingForObjectClass(); }
        return instance;
    }

//    -----------------------------------------------

    public void callWebServiceForRxNetworking(Activity context, String relativePath, Object paramClass, String methodName ) {
        ProgressClass.getProgressInstance().showDialog(context);
        AndroidNetworking.post(CONSTANTS.BASE_URL+relativePath)
                .addBodyParameter(paramClass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        handler.handle(response, methodName);
                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(context, "Alert", "Error to Retrive Data");
                    }
                });
    }

    public void callWebServiceForRxNetworking(Context context, String relativePath, Object paramClass, String methodName ) {
        ProgressClass.getProgressInstance().showDialog(context);
        AndroidNetworking.post(CONSTANTS.BASE_URL+relativePath)
                .addBodyParameter(paramClass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        handler.handle(response, methodName);
                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(context, "Alert", "Error to Retrive Data");
                    }
                });
    }

    public interface CompletionHandler {
        void handle(JSONObject object, String methodName);
    }

}
