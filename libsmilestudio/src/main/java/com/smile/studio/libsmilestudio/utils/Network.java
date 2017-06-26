package com.smile.studio.libsmilestudio.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by admin on 05/06/2016.
 */
public class Network {
    public static boolean isNetwork(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean val = false;
        Debug.d("Checking for WI-FI Network");
        final android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi != null && wifi.isAvailable() && wifi.isConnected()) {
            Debug.d("Found WI-FI Network");
            val = true;
        } else {
            Debug.e("WI-FI Network not Found");
        }
        Debug.e("Checking for Mobile Internet Network");
        final android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile != null && mobile.isAvailable() && mobile.isConnected()) {
            Debug.d("Found Mobile Internet Network");
            val = true;
        } else {
            Debug.e("Mobile Internet Network not Found");
        }
        return val;
    }
}
