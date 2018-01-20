package org.paramedic.homeless.currenciestest.service.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

/**
 * Created by codesanitar on 17/01/18.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    @SuppressWarnings("deprecation")
    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return false;

            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && (netInfo.isConnectedOrConnecting() || netInfo.isAvailable())) {
                return true;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Network[] networks = cm.getAllNetworks();
                NetworkInfo networkInfo;
                for (Network mNetwork : networks) {
                    networkInfo = cm.getNetworkInfo(mNetwork);
                    if ((networkInfo.getType() == ConnectivityManager.TYPE_MOBILE
                            || networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                            || networkInfo.getType() == ConnectivityManager.TYPE_WIMAX
                            || networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET
                    ) && networkInfo.isConnectedOrConnecting()) {
                        return true;
                    }
                }
            }
            else {

                NetworkInfo[] info = cm.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if ((anInfo.getType() == ConnectivityManager.TYPE_MOBILE
                                || anInfo.getType() == ConnectivityManager.TYPE_WIFI
                                || anInfo.getType() == ConnectivityManager.TYPE_WIMAX
                                || anInfo.getType() == ConnectivityManager.TYPE_ETHERNET
                        ) && anInfo.isConnectedOrConnecting()) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return true;
        }
        return false;

    }
}
