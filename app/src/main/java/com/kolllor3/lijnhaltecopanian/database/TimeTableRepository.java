package com.kolllor3.lijnhaltecopanian.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.providers.LijnApiProider;

import java.util.List;

public class TimeTableRepository {

    private LijnApiProider lijnApiProider;
    private TimeTableDao timeTableDao;

    public TimeTableRepository(Application application) {
        lijnApiProider = new LijnApiProider();
        TimeTableDataBase db = TimeTableDataBase.getDatabase(application);
        timeTableDao = db.getTimeTableDao();
    }

    public LiveData<List<TimeTableItem>> getDienstRegeling(int haltenummer, int halteentiteit, int dayOfWeek){
        //return db, weekeleks update request sture
        lijnApiProider.getDienstRegeling(haltenummer, halteentiteit);
        return timeTableDao.getTimeTableFromDayOfWeek(dayOfWeek);
        //return lijnApiProider.getTimeTable();
    }
}
