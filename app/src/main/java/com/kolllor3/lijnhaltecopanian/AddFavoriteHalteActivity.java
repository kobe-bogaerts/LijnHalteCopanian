package com.kolllor3.lijnhaltecopanian;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.kolllor3.lijnhaltecopanian.constants.Constants;
import com.kolllor3.lijnhaltecopanian.providers.LocationProvider;
import com.kolllor3.lijnhaltecopanian.util.LogUtils;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.AddFavoriteHalteViewModel;

import java.util.Locale;

public class AddFavoriteHalteActivity extends AppCompatActivity implements Constants {

    private AddFavoriteHalteViewModel favoriteHalteViewModel;
    private LocationProvider locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorite_halte);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        favoriteHalteViewModel = ViewModelProviders.of(this).get(AddFavoriteHalteViewModel.class);

        if(Utilities.isNull(savedInstanceState))
            favoriteHalteViewModel.init(this);

        findViewById(R.id.get_nearby_button).setOnClickListener(v -> locationProvider.getLocation());

        RecyclerView halteList = findViewById(R.id.halteList);
        halteList.setLayoutManager(new LinearLayoutManager(this));
        halteList.setAdapter(favoriteHalteViewModel.getHalteListAdapter());

        favoriteHalteViewModel.getNearbyHaltes().observe(this, haltes-> favoriteHalteViewModel.getHalteListAdapter().setHalteItems(haltes));

        locationProvider = new LocationProvider(this, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        favoriteHalteViewModel.setCurrentLocation(location);
                        LogUtils.logI("location stuff", String.format(Locale.US, "%s -- %s", location.getLatitude(), location.getLongitude()));
                    }
                }
            }
        });

        favoriteHalteViewModel.getSelectedHalte().observe(this, halte -> {
            //todo: start halte activity met realtime data
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationProvider.removeListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOCATION_PERMISSION_SET:
                switch (resultCode) {
                    case RESULT_OK:
                        locationProvider.getLocation();
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
            case LOCATION_PERMISSION_RESULT:
                if(Utilities.isNotNull(data) && Utilities.isNotNull(data.getAction()) && data.getAction().equals(ASK_LOCATION_RETURN_ACTION)){
                    locationProvider.getLocation();
                }
                break;
        }
    }
}
