package com.kolllor3.lijnhaltecopanian;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

public class App extends Application {
    private static App mInstance;
    private RequestQueue mRequestQueue;
    public static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if(Utilities.isNull(mRequestQueue)){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addTorequestQueue(Request<T> req, String tag){
        req.setTag(Utilities.isNotNullOrNotEmpty(tag) ? tag : TAG);
        getRequestQueue().add(req);
    }

    public <T> void addTorequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag){
        getRequestQueue().cancelAll(tag);
    }
}
