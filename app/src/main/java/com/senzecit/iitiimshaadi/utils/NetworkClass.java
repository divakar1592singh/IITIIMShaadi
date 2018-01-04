package com.senzecit.iitiimshaadi.utils;

    import android.content.Context;
    import android.net.ConnectivityManager;
    import android.view.View;
    import android.widget.Toast;

/**
 * Created by Cyber Matrix3 on 12/13/2016.
 */

public class NetworkClass {

    Context context;

    public NetworkClass(Context context)
    {
        this.context = context;
    }

    public boolean checkInternet() {
        ConnectivityManager connec =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
          //  Toast.makeText(context, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
          //  Toast.makeText(context, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;

    }

}
