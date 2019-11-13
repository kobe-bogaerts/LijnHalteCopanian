package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kolllor3.lijnhaltecopanian.database.TimeTableRepository;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;

import java.util.List;

public class TimeTableViewModel extends AndroidViewModel {

    private TimeTableRepository mRepository;


    public TimeTableViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TimeTableRepository(application);
    }

    public LiveData<List<TimeTableItem>> getDienstRegeling(int haltenummer, int halteentiteit){
        return mRepository.getDienstRegeling(haltenummer, halteentiteit);
    }
}