package com.kolllor3.lijnhaltecopanian;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kolllor3.lijnhaltecopanian.constants.Constants;
import com.kolllor3.lijnhaltecopanian.providers.LocationProvider;
import com.kolllor3.lijnhaltecopanian.util.LogUtils;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.AddFavoriteHalteViewModel;
import com.kolllor3.lijnhaltecopanian.viewModel.HalteViewModel;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Constants {

    private HalteViewModel halteViewModel;
    private LocationProvider locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        halteViewModel = ViewModelProviders.of(this).get(HalteViewModel.class);

        if(Utilities.isNull(savedInstanceState))
            halteViewModel.init(this);

//        halteViewModel.getNearbyHaltes().observe(this, haltes-> halteViewModel.getHalteListAdapter().setHalteItems(haltes));

        RecyclerView halteList = findViewById(R.id.halteList);
        halteList.setLayoutManager(new LinearLayoutManager(this));
        halteList.setAdapter(halteViewModel.getHalteListAdapter());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(this, AddFavoriteHalteActivity.class);
            startActivity(i);
        });

//        locationProvider = new LocationProvider(this, new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    if (location != null) {
//                        double wayLatitude = location.getLatitude();
//                        double wayLongitude = location.getLongitude();
//                        halteViewModel.setCurrentLocation(location);
//                        LogUtils.logI("location stuff", String.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude));
//                    }
//                }
//            }
//        });

        halteViewModel.getSelectedHalte().observe(this, halte -> {
            //todo: start halte activity met realtime data
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
