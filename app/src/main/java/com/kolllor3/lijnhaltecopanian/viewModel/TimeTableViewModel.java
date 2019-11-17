package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kolllor3.lijnhaltecopanian.App;
import com.kolllor3.lijnhaltecopanian.R;
import com.kolllor3.lijnhaltecopanian.adapter.TimeTableAdapter;
import com.kolllor3.lijnhaltecopanian.database.TimeTableRepository;
import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;

import java.util.Calendar;
import java.util.List;

public class TimeTableViewModel extends AndroidViewModel implements Constants {

    private TimeTableRepository mRepository;
    private TimeTableAdapter adapter;


    public TimeTableViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TimeTableRepository(application);
    }

    public void init(){
        adapter = new TimeTableAdapter(this, R.layout.timeline_list_item);
    }

    public TimeTableAdapter getAdapter() {
        return adapter;
    }

    public LiveData<List<TimeTableItem>> getDienstRegeling(int haltenummer, int halteentiteit){
        Calendar c = Calendar.getInstance();
        return mRepository.getDienstRegeling(haltenummer, halteentiteit, c.get(Calendar.DAY_OF_WEEK));
    }

    public void updateDienstRegelingManul(int haltenummer, int halteentiteit){
        mRepository.updateDienstRegelingManual(haltenummer, halteentiteit);
    }

    public LiveData<List<TimeTableItem>> getAll(){
        return mRepository.getAll();
    }

    public void cancelGetDienstregelingRequest(){
        App.getInstance().cancelPendingRequests(GET_DIENSTREGELING_TAG);
    }
}