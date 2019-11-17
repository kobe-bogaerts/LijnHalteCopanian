package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.kolllor3.lijnhaltecopanian.adapter.HalteListAdapter;
import com.kolllor3.lijnhaltecopanian.database.HalteRepository;
import com.kolllor3.lijnhaltecopanian.model.Halte;

import java.util.List;

public class AddFavoriteHalteViewModel extends BaseHalteViewModel {

    public AddFavoriteHalteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HalteRepository(application);
        isCheckboxVisible = true;
    }

    public void init(){
        halteListAdapter = new HalteListAdapter(this);
    }

    public HalteListAdapter getHalteListAdapter() {
        return halteListAdapter;
    }

    public LiveData<List<Halte>> getNearbyHaltes(){
        return mRepository.getNearbyHaltes();
    }

    public void setCurrentLocation(Location location){
        mRepository.setLocation(location);
    }
}
