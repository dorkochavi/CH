package com.hak.hacktest.activities.util;

import android.app.Application;
import android.content.Context;

import com.hak.hacktest.activities.retroFit.API;



public class AppClass extends Application {

    private static Context context;


    public static Context getAppContext(){
        return context;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        ConnectionManager.getInstant().init();
        API.init();

    }
}
