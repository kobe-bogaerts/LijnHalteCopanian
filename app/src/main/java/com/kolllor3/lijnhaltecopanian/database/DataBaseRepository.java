package com.kolllor3.lijnhaltecopanian.database;

import android.app.Activity;
import android.app.Application;
import android.location.Location;

import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DataBaseRepository {

    private HalteDao halteDao;
    private FavoriteHalteDao favoriteHalteDao;
    private MutableLiveData<List<Halte>> nearbyHalte = new MutableLiveData<>();
    private Location location = new Location("none");
    private Activity activity;

    public DataBaseRepository(Application application) {
        HalteDataBase db = HalteDataBase.getDatabase(application);
        halteDao = db.getHalteDao();
        favoriteHalteDao = db.getFavoriteHalteDao();
    }

    public void setActivity(Activity activity){
        this.activity = activity;
        setLocation(location);
    }

    public void setLocation(Location location) {
        this.location = location;
        Utilities.doInBackground(()->{
            List<Halte> haltes = halteDao.getClosestHaltes(location.getLatitude(), location.getLongitude());
            activity.runOnUiThread(()-> nearbyHalte.setValue(haltes));
        });
    }

    public LiveData<List<Halte>> getFavoriteHaltes(){
        return favoriteHalteDao.getFavoriteHaltes();
    }

    public void addFavoriteHalte(int halteNummer){
        Utilities.doInBackground(()->favoriteHalteDao.addFavoriteHalte(halteNummer));
    }

    public void removeFavoriteHalte(int halteNummer){
        Utilities.doInBackground(()->favoriteHalteDao.deleteFavoriteHalte(halteNummer));
    }

    public MutableLiveData<List<Halte>> getNearbyHaltes() {
        return nearbyHalte;
    }
}
