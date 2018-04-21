package com.senzecit.iitiimshaadi.api;

import android.app.Activity;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;

import org.json.JSONObject;

/**
 * Created by senzec on 6/3/18.
 */


public class RxObjectWebService {

//    ------------------SINGLETON
    private static RxObjectWebService instance = null;
    CompletionHandler handler;

    public void setCompletionHandler(CompletionHandler handler){
        this.handler = handler;
    }

    private RxObjectWebService(){ }

    public static RxObjectWebService getInstance()
    {
        if(instance == null)
        { instance = new RxObjectWebService(); }
        return instance;
    }

//    -----------------------------------------------

    public void callWebServiceForRxNetworking(Activity activity, String relativePath, Object paramClass, String methodName) {

        if(NetworkClass.getInstance().checkInternet(activity) == true){

        ProgressClass.getProgressInstance().showDialog(activity);
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
//                        AlertDialogSingleClick.getInstance().showDialog(activity, "Alert", "Error to Retrive Data. \nPlease, Try Again!");
                        AlertDialogSingleClick.getInstance().showDialog(activity, "Alert", " "+ AppMessage.SOME_ERROR_INFO);

                    }
                });

        }else {
            NetworkDialogHelper.getInstance().showDialog(activity);
        }
    }

    public void callWebServiceForRxNetworking(Context context, String relativePath, Object paramClass, String methodName ) {

        System.out.print("--------> Path : "+relativePath);
        if(NetworkClass.getInstance().checkInternet(context) == true){

            AndroidNetworking.post(CONSTANTS.BASE_URL+relativePath)
                .addBodyParameter(paramClass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        handler.handle(response, methodName);
                    }

                    @Override
                    public void onError(ANError error) {
                        AlertDialogSingleClick.getInstance().showDialog(context, "Alert", AppMessage.SOME_ERROR_INFO);
                    }
                });

        }else {
            NetworkDialogHelper.getInstance().showDialog(context);
        }
    }

    //WITHOUT PROGRESS
    public void callWebServiceForRxNetworking(Activity activity, String relativePath, Object paramClass, String methodName, boolean progress ) {

        if(NetworkClass.getInstance().checkInternet(activity) == true){

            AndroidNetworking.post(CONSTANTS.BASE_URL+relativePath)
                .addBodyParameter(paramClass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        handler.handle(response, methodName);
                    }

                    @Override
                    public void onError(ANError error) {
                        AlertDialogSingleClick.getInstance().showDialog(activity, "Alert", AppMessage.SOME_ERROR_INFO);
                    }
                });

        }else {
            NetworkDialogHelper.getInstance().showDialog(activity);
        }
    }

    public void callWebServiceForRxNetworking(Context context, String relativePath, Object paramClass, String methodName, boolean progress  ) {

        if(NetworkClass.getInstance().checkInternet(context) == true){

            AndroidNetworking.post(CONSTANTS.BASE_URL+relativePath)
                .addBodyParameter(paramClass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        handler.handle(response, methodName);
                    }

                    @Override
                    public void onError(ANError error) {
                        AlertDialogSingleClick.getInstance().showDialog(context, "Alert", AppMessage.SOME_ERROR_INFO);
                    }
                });

        }else {
            NetworkDialogHelper.getInstance().showDialog(context);
        }
    }

    public void callWebServiceForJSONParsing(Activity activity, String relativePath, JSONObject paramClass, String methodName) {

        if(NetworkClass.getInstance().checkInternet(activity) == true){

            ProgressClass.getProgressInstance().showDialog(activity);
            AndroidNetworking.post(relativePath)
                    .addJSONObjectBody(paramClass)
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
//                        AlertDialogSingleClick.getInstance().showDialog(activity, "Alert", "Error to Retrive Data. \nPlease, Try Again!");
                            AlertDialogSingleClick.getInstance().showDialog(activity, "Alert", AppMessage.SOME_ERROR_INFO);

                        }
                    });

        }else {
            NetworkDialogHelper.getInstance().showDialog(activity);
        }


    }

    public void callWebServiceForJSONParsingDashboard(Activity activity, String relativePath, JSONObject paramClass, String methodName) {

        if(NetworkClass.getInstance().checkInternet(activity) == true){

            AndroidNetworking.post(relativePath)
                    .addJSONObjectBody(paramClass)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            handler.handle(response, methodName);
                        }

                        @Override
                        public void onError(ANError error) {
//                        AlertDialogSingleClick.getInstance().showDialog(activity, "Alert", "Error to Retrive Data. \nPlease, Try Again!");
                            AlertDialogSingleClick.getInstance().showDialog(activity, "Alert", AppMessage.SOME_ERROR_INFO);

                        }
                    });

        }else {
            NetworkDialogHelper.getInstance().showDialog(activity);
        }


    }



    public void callWebServiceForJSONSearchPtnr(Activity activity, String relativePath, JSONObject paramClass, String methodName) {

        if(NetworkClass.getInstance().checkInternet(activity) == true){

            ProgressClass.getProgressInstance().showDialog(activity);
            AndroidNetworking.post(relativePath)
                    .addJSONObjectBody(paramClass)
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

                        }
                    });

        }else {
            NetworkDialogHelper.getInstance().showDialog(activity);
        }


    }

    public interface CompletionHandler {
        void handle(JSONObject object, String methodName);
        void onServiceError(JSONObject object, String methodName);

    }

}
