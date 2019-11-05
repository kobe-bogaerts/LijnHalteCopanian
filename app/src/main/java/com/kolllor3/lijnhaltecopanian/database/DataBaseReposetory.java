package com.kolllor3.lijnhaltecopanian.database;

import android.app.Application;

import com.kolllor3.lijnhaltecopanian.util.ReturnInterface;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

public class DataBaseReposetory {

    private HalteDao halteDao;
    private ReturnInterface listener;

    public DataBaseReposetory(Application application, ReturnInterface listener) {
        HalteDataBase db = HalteDataBase.getDatabase(application);
        halteDao = db.getHalteDao();
        this.listener = listener;
    }

    public void getAllHaltesFromEntiteit(int entiteitId) {
        Utilities.doInBackground(()-> listener.getResult(halteDao.getAllHaltesByEntiteit(entiteitId)));
    }
}
