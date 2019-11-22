package com.kolllor3.lijnhaltecopanian.database;

import android.app.Application;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kolllor3.lijnhaltecopanian.model.LijnItem;
import com.kolllor3.lijnhaltecopanian.model.RealTimeItem;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.providers.LijnApiProider;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.ArrayList;
import java.util.List;

public class TimeTableRepository {

    private LijnApiProider lijnApiProider;
    private TimeTableDao timeTableDao;
    private LijnKleurenDao lijnKleurenDao;

    public TimeTableRepository(Application application) {
        TimeTableDataBase db = TimeTableDataBase.getDatabase(application);
        timeTableDao = db.getTimeTableDao();
        lijnKleurenDao = db.getLijnKleurenDao();
        lijnApiProider = new LijnApiProider(timeTableDao, lijnKleurenDao);
    }

    public LiveData<List<TimeTableItem>> getDienstRegeling(int haltenummer, int halteentiteit, int dayOfWeek){
        lijnApiProider.getDienstRegeling(haltenummer, halteentiteit);
        return timeTableDao.getTimeTableFromDayOfWeek(haltenummer, dayOfWeek);
    }

    public void updateDienstRegelingManual(int haltenummer, int halteentiteit){
        lijnApiProider.getDienstRegeling(haltenummer, halteentiteit);
    }

    public LiveData<List<TimeTableItem>> getAll(){
        return timeTableDao.getAll();
    }

    public LiveData<List<RealTimeItem>> getRealTimeDate(int haltenummer, int halteentiteit){
        return lijnApiProider.getRealTimeDate(haltenummer, halteentiteit);
    }

    public LiveData<List<LijnItem>> getLijnItems(int lijn, int entiteit){
        return getLijnItems(new Integer[]{lijn}, entiteit);
    }

    public LiveData<List<LijnItem>> getLijnItems(Integer[] lijnen, int entiteit){
        final MutableLiveData<List<LijnItem>> holder = new MutableLiveData<>();
        final Handler h = new Handler();
        final int[] lijnNummers = new int[lijnen.length];
        for (int i = 0; i < lijnen.length; i++) {
            lijnNummers[i] = lijnen[i];
        }
        Utilities.doInBackground(()->{
            List<LijnItem> lijnItems = lijnKleurenDao.getLijnItems(lijnNummers, entiteit);
            if(lijnItems.size() > 0){
                h.post(()->holder.setValue(lijnItems));
            }else{
                List<LijnItem> params = new ArrayList<>();
                for (int lijn : lijnNummers) {
                    params.add(new LijnItem(lijn, entiteit));
                }
                lijnApiProider.getKleurenArrayVoorLijnen(params, holder);
            }
        });
        return holder;
    }
}
