package com.sridhar.onlinenewsfeed.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by 2136 on 11/7/2017.
 */

public class MyApplication extends Application
{
    private static MyApplication my_application_instance = null;
    @Override
    public void onCreate()
    {
        super.onCreate();

        my_application_instance = this;
    }

    public static MyApplication getMyApplicationInstance()
    {
        return my_application_instance;
    }

    public static Context getMyApplicationContext()
    {
        return my_application_instance.getApplicationContext();
    }
}
