package com.hak.hacktest.activities.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionManager {

    private static boolean networkAvailable = false;
    private final String LOG_TAG = this.getClass().getSimpleName();
    private static ConnectionManager ourInstance = new ConnectionManager();
    private NetworkChangeReceiver  broadCast;



    public static ConnectionManager getInstant(){
        return ourInstance;
    }


    private ConnectionManager() {

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
          broadCast = new NetworkChangeReceiver();
        AppClass.getAppContext().registerReceiver( broadCast, filter);

    }

     void init(){
        checkConnection();
    }

    private static void checkConnection(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) AppClass.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        networkAvailable =  activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isNetworkAvailable() {
        return networkAvailable;
    }




    public  class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            checkConnection();
        }
    }




}
