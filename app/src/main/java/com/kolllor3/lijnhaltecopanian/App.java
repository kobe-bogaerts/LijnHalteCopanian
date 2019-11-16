package com.kolllor3.lijnhaltecopanian;

import android.app.Application;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kolllor3.lijnhaltecopanian.backgroundTasks.DienstRegelingBackgroundWorker;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.concurrent.TimeUnit;

public class App extends Application {
    private static App mInstance;
    private RequestQueue mRequestQueue;
    public static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresBatteryNotLow(true).build();
        PeriodicWorkRequest req = new PeriodicWorkRequest.Builder(DienstRegelingBackgroundWorker.class, 1, TimeUnit.DAYS).setConstraints(constraints).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("getDienstregelingFavHalte", ExistingPeriodicWorkPolicy.KEEP, req);
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
