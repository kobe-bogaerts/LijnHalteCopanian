package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kolllor3.lijnhaltecopanian.adapter.TimeTableAdapter;
import com.kolllor3.lijnhaltecopanian.database.TimeTableRepository;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;

import java.util.Calendar;
import java.util.List;

public class TimeTableViewModel extends AndroidViewModel {

    private TimeTableRepository mRepository;
    private TimeTableAdapter adapter;


    public TimeTableViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TimeTableRepository(application);
    }

    public void init(Activity activity){
        adapter = new TimeTableAdapter(this, activity);
    }

    public TimeTableAdapter getAdapter() {
        return adapter;
    }

    public LiveData<List<TimeTableItem>> getDienstRegeling(int haltenummer, int halteentiteit){
        Calendar c = Calendar.getInstance();
        return mRepository.getDienstRegeling(haltenummer, halteentiteit, c.get(Calendar.DAY_OF_WEEK));
    }
}