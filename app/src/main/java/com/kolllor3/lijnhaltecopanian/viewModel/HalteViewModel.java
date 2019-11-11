package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.kolllor3.lijnhaltecopanian.adapter.HalteListAdapter;
import com.kolllor3.lijnhaltecopanian.constants.Constants;
import com.kolllor3.lijnhaltecopanian.database.DataBaseRepository;
import com.kolllor3.lijnhaltecopanian.model.Halte;

import java.util.List;

public class HalteViewModel extends BaseHalteViewModel implements Constants {

    public HalteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataBaseRepository(application);
    }

    public void init(Activity activity){
        halteListAdapter = new HalteListAdapter(this, activity);
    }

    public HalteListAdapter getHalteListAdapter() {
        return halteListAdapter;
    }

    public LiveData<List<Halte>> getFavoriteHaltes(){
        return mRepository.getFavoriteHaltes();
    }

    public void setIsCheckboxVisible(boolean isCheckboxVisible){
        this.isCheckboxVisible = isCheckboxVisible;
    }
}
