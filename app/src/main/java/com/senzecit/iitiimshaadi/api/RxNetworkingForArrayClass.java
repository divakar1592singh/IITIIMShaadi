package com.senzecit.iitiimshaadi.api;

import android.app.Activity;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by senzec on 6/3/18.
 */


public class RxNetworkingForArrayClass {

//    ------------------SINGLETON
    private static RxNetworkingForArrayClass instance = null;

    CompletionHandler handler;
    public void setCompletionHandler(CompletionHandler handler){
        this.handler = handler;
    }

    private RxNetworkingForArrayClass(){ }

    public static RxNetworkingForArrayClass getInstance()
    {
        if(instance == null)
        { instance = new RxNetworkingForArrayClass(); }
        return instance;
    }

//    -----------------------------------------------

    public void callWebServiceForRxNetworking(Activity context, String relativePath, Object paramClass, String methodName ) {
        ProgressClass.getProgressInstance().showDialog(context);
        AndroidNetworking.post(Constants.BASE_URL+relativePath)
                .addBodyParameter(paramClass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        handler.handles(response, methodName);
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(context, "Alert", "Error to Retrive Data");

                    }
                });
                /*.getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });*/
    }

    public void callWebServiceForRxNetworking(Context context, String relativePath, Object paramClass, String methodName ) {
        ProgressClass.getProgressInstance().showDialog(context);
        AndroidNetworking.post(Constants.BASE_URL+relativePath)
                .addBodyParameter(paramClass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        System.out.println(response);
                        ProgressClass.getProgressInstance().stopProgress();
                        handler.handles(response, methodName);
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(context, "Alert", "Error to Retrive Data");

                    }
                });
                /*.getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        handler.handles(response, methodName);
                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(context, "Alert", "Error to Retrive Data");
                    }
                });*/
    }

    public interface CompletionHandler {
        void handles(JSONArray jsonArray, String methodName);
    }

}
