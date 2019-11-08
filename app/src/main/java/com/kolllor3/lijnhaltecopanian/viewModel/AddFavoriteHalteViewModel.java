package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Activity;
import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.kolllor3.lijnhaltecopanian.adapter.HalteListAdapter;
import com.kolllor3.lijnhaltecopanian.database.DataBaseRepository;
import com.kolllor3.lijnhaltecopanian.model.Halte;

import java.util.List;

public class AddFavoriteHalteViewModel extends BaseHalteViewModel {

    public AddFavoriteHalteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataBaseRepository(application);
    }

    public void init(Activity activity){
        halteListAdapter = new HalteListAdapter(this, activity);
        mRepository.setActivity(activity);
    }

    public HalteListAdapter getHalteListAdapter() {
        return halteListAdapter;
    }

    public MutableLiveData<List<Halte>> getNearbyHaltes(){
        return mRepository.getNearbyHaltes();
    }

    public void setCurrentLocation(Location location){
        mRepository.setLocation(location);
    }
}
