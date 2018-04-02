package com.senzecit.iitiimshaadi.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;

import com.androidnetworking.AndroidNetworking;
import com.crashlytics.android.Crashlytics;
import com.senzecit.iitiimshaadi.payment.AppEnvironment;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import io.fabric.sdk.android.Fabric;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by senzec on 13/2/18.
 */


public class AppController extends Application {

    public static final String TAG =AppController.class.getSimpleName();
    public static Context context;
    private static AppController sInstance;
    private AppPrefs prefs;

    AppEnvironment appEnvironment;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        // initialize the singleton
        sInstance = this;
        prefs = new AppPrefs(this);

        appEnvironment = AppEnvironment.PRODUCTION;
        AndroidNetworking.initialize(getApplicationContext());
    }


    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized AppController getInstance() {
        return sInstance;
    }


    // Checking for all possible internet providers
    public boolean isConnectingToInternet() {

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public AppPrefs getPrefs() {
        return prefs;
    }

    //SOCKET


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(CONSTANTS.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public Socket getSocket() {
        return mSocket;
    }
}
