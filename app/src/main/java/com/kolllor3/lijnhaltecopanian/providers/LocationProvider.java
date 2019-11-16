package com.kolllor3.lijnhaltecopanian.providers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.kolllor3.lijnhaltecopanian.AskPermissionActivity;
import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

public class LocationProvider implements Constants {

    private FusedLocationProviderClient mFusedLocationClient;

    private Activity context;
    private LocationCallback listener;
    private LocationRequest locationRequest;

    public LocationProvider(Activity context, LocationCallback listener) {
        this.context = context;
        this.listener = listener;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
    }

    private boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Intent i = new Intent(context, AskPermissionActivity.class);
            context.startActivityForResult(i, LOCATION_PERMISSION_RESULT);
            return false;
        }
        else {
            return true;
        }
    }

    public void getLocation(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(context, location -> {
                    if (location != null) {
                        List<Location> locations = new ArrayList<>();
                        locations.add(location);
                        listener.onLocationResult(LocationResult.create(locations));
                    } else {
                        turnOnLcation();
                        mFusedLocationClient.requestLocationUpdates(locationRequest, listener, Looper.myLooper());
                    }
                });
            }
        }else {
           mFusedLocationClient.requestLocationUpdates(locationRequest, listener, Looper.myLooper());
        }
    }

    public void removeListener(){
        if(Utilities.isNotNull(mFusedLocationClient) && Utilities.isNotNull(listener))
            mFusedLocationClient.removeLocationUpdates(listener);
    }

    private void turnOnLcation()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(context, locationSettingsResponse -> getLocation());

        task.addOnFailureListener(context, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(context, LOCATION_PERMISSION_SET);
                } catch (IntentSender.SendIntentException ignored) {}
            }
        });

    }

}
