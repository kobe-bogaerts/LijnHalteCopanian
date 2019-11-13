package com.kolllor3.lijnhaltecopanian.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.providers.LijnApiProider;

import java.util.List;

public class TimeTableRepository {

    private LijnApiProider lijnApiProider;

    public TimeTableRepository(Application application) {
        lijnApiProider = new LijnApiProider();
    }

    public LiveData<List<TimeTableItem>> getDienstRegeling(int haltenummer, int halteentiteit){
        lijnApiProider.getDienstRegeling(haltenummer, halteentiteit);
        return lijnApiProider.getTimeTable();
    }
}
