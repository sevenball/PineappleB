package com.wangshiqi.pineappleb.model.net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dllo on 16/10/28.
 */
public class NetHttpJudge {
    public boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        if (isWifiConn == true && isMobileConn == false) {
            return true;
        } else if (isWifiConn == false && isMobileConn == true) {
            return false;
        } else {
            return false;
        }
    }
}
