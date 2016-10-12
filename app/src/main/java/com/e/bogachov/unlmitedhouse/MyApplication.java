package com.e.bogachov.unlmitedhouse;

import android.app.Application;
import android.content.Context;

/**
 * Created by Bogachov on 30.09.16.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}