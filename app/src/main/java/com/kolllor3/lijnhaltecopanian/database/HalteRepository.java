package com.kolllor3.lijnhaltecopanian.database;

import android.app.Application;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.List;

public class HalteRepository {

    private HalteDao halteDao;
    private FavoriteHalteDao favoriteHalteDao;
    private MutableLiveData<Location> location = new MutableLiveData<>(new Location("none"));

    public HalteRepository(Application application) {
        HalteDataBase db = HalteDataBase.getDatabase(application);
        halteDao = db.getHalteDao();
        favoriteHalteDao = db.getFavoriteHalteDao();
    }

    public void setLocation(Location location) {
        this.location.setValue(location);
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

    public LiveData<List<Halte>> getNearbyHaltes() {
        //dit vormt een locatie die word geset om naar een lijst van nabijgelegen haltes
        return Transformations.switchMap(location, location -> halteDao.getClosestHaltes(location.getLatitude(), location.getLongitude()));
    }
}
