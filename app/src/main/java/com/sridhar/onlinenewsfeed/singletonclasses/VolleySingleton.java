package com.sridhar.onlinenewsfeed.singletonclasses;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sridhar.onlinenewsfeed.application.MyApplication;

/**
 * Created by 2136 on 11/7/2017.
 */

public class VolleySingleton
{
    private static VolleySingleton volley_singleton_instance = null;
    private RequestQueue requestQueue;

    private VolleySingleton()
    {
        requestQueue = Volley.newRequestQueue(MyApplication.getMyApplicationContext());
    }

    public static VolleySingleton getVolleySingletonInstance()
    {
        if (volley_singleton_instance==null)
        {
            volley_singleton_instance = new VolleySingleton();
        }
        return volley_singleton_instance;
    }

    public RequestQueue getRequestQueue()
    {
        return requestQueue;
    }
}
