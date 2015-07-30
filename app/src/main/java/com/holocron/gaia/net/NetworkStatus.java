package com.holocron.gaia.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {

    public static boolean isConnected(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();

            // Se não existe nenhum tipo de conexão retorna false
            if (netInfo == null) {
                return false;
            }

            int netType = netInfo.getType();
            //Verifica conexão de dados moveis ou WIFI.
            return (netType == ConnectivityManager.TYPE_WIFI) || (netType == ConnectivityManager.TYPE_MOBILE);
        }
        return false;
    }

}
