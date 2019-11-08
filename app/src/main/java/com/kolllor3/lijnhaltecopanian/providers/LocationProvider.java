package com.kolllor3.lijnhaltecopanian.providers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.kolllor3.lijnhaltecopanian.AskPermissionActivity;
import com.kolllor3.lijnhaltecopanian.constants.Constants;

public class LocationProvider implements Constants {

    private Activity context;
    private LocationManager locationManager;
    private LocationListener listener;

    public LocationProvider(Activity context, LocationListener listener) {
        this.context = context;
        this.listener = listener;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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
        if(checkLocationPermission())
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, listener);
    }

    public void turnOnLcation()
    {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

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
