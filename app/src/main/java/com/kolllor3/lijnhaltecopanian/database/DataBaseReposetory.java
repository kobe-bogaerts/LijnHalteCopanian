package com.kolllor3.lijnhaltecopanian.database;

import android.app.Application;
import android.location.Location;

import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.util.ReturnInterface;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DataBaseReposetory {

    private HalteDao halteDao;
    private ReturnInterface listener;
    private Location location = new Location("none");

    public DataBaseReposetory(Application application, ReturnInterface listener) {
        HalteDataBase db = HalteDataBase.getDatabase(application);
        halteDao = db.getHalteDao();
        this.listener = listener;
    }

    public void getAllHaltesFromEntiteit(int entiteitId) {
        Utilities.doInBackground(()-> listener.getResult(halteDao.getAllHaltesByEntiteit(entiteitId)));
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LiveData<List<Halte>> getNearbyHaltes() {
        return halteDao.getClosestHaltes(location.getLatitude(), location.getLongitude());
    }
}
