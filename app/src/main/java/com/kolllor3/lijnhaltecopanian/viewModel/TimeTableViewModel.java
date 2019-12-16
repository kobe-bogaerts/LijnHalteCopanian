package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kolllor3.lijnhaltecopanian.App;
import com.kolllor3.lijnhaltecopanian.R;
import com.kolllor3.lijnhaltecopanian.adapter.RealTimeTableAdapter;
import com.kolllor3.lijnhaltecopanian.adapter.TimeTableAdapter;
import com.kolllor3.lijnhaltecopanian.database.TimeTableRepository;
import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.model.LijnItem;
import com.kolllor3.lijnhaltecopanian.model.RealTimeItem;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.Calendar;
import java.util.List;

public class TimeTableViewModel extends AndroidViewModel implements Constants {

    private TimeTableRepository mRepository;
    private TimeTableAdapter adapter;
    private RealTimeTableAdapter realTimeAdapter;


    public TimeTableViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TimeTableRepository(application);
    }

    public void init(){
        adapter = new TimeTableAdapter(this, R.layout.timeline_list_item);
        realTimeAdapter = new RealTimeTableAdapter(this, R.layout.real_timeline_list_item);
    }

    public TimeTableAdapter getAdapter() {
        return adapter;
    }

    public RealTimeTableAdapter getRealTimeAdapter() {
        return realTimeAdapter;
    }

    public LiveData<List<TimeTableItem>> getDienstRegeling(int haltenummer, int halteentiteit){
        Calendar c = Calendar.getInstance();
        return mRepository.getDienstRegeling(haltenummer, halteentiteit, c.get(Calendar.DAY_OF_WEEK));
    }

    public LiveData<List<RealTimeItem>> getRealTimeData(int haltenummer, int halteentiteit){
        return mRepository.getRealTimeDate(haltenummer, halteentiteit);
    }

    public void updateDienstRegelingManul(int haltenummer, int halteentiteit){
        Utilities.doInBackground(()->{
            mRepository.deleteTimeTableToday(haltenummer);
            mRepository.updateDienstRegelingManual(haltenummer, halteentiteit);
        });
    }

    public LiveData<List<TimeTableItem>> getAll(){
        return mRepository.getAll();
    }

    public void cancelGetDienstregelingRequest(){
        App.getInstance().cancelPendingRequests(GET_DIENSTREGELING_TAG);
    }

    public LiveData<List<LijnItem>> getLijnItems(int lijn, int entiteit){
        return mRepository.getLijnItems(lijn, entiteit);
    }

    public LiveData<List<LijnItem>> getLijnItems(Integer[] lijnen, int entiteit){
        return mRepository.getLijnItems(lijnen, entiteit);
    }
}