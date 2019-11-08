package com.kolllor3.lijnhaltecopanian.database;

import android.app.Activity;
import android.app.Application;
import android.location.Location;

import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.util.ReturnInterface;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class DataBaseReposetory {

    private HalteDao halteDao;
    private ReturnInterface listener;
    private MutableLiveData<List<Halte>> nearbyHalte = new MutableLiveData<>();
    private Location location = new Location("none");
    private Activity activity;

    public DataBaseReposetory(Application application, ReturnInterface listener) {
        HalteDataBase db = HalteDataBase.getDatabase(application);
        halteDao = db.getHalteDao();
        this.listener = listener;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
        setLocation(location);
    }

    @Deprecated
    public void getAllHaltesFromEntiteit(int entiteitId) {
        Utilities.doInBackground(()-> listener.getResult(halteDao.getAllHaltesByEntiteit(entiteitId)));
    }

    public void setLocation(Location location) {
        this.location = location;
        Utilities.doInBackground(()->{
            List<Halte> haltes = halteDao.getClosestHaltes(location.getLatitude(), location.getLongitude());
            activity.runOnUiThread(()-> nearbyHalte.setValue(haltes));
        });
    }

    public MutableLiveData<List<Halte>> getNearbyHaltes() {
        return nearbyHalte;
    }
}
