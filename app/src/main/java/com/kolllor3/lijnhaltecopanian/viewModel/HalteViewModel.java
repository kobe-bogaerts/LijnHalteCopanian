package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.kolllor3.lijnhaltecopanian.adapter.HalteListAdapter;
import com.kolllor3.lijnhaltecopanian.database.HalteRepository;
import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.model.Halte;

import java.util.List;

public class HalteViewModel extends BaseHalteViewModel implements Constants {

    public HalteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HalteRepository(application);
    }

    public void init(){
        halteListAdapter = new HalteListAdapter(this);
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
