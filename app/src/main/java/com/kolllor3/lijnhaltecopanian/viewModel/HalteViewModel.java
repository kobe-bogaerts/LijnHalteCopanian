package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Activity;
import android.app.Application;
import android.location.Location;

import com.kolllor3.lijnhaltecopanian.adapter.HalteListAdapter;
import com.kolllor3.lijnhaltecopanian.constants.Constants;
import com.kolllor3.lijnhaltecopanian.database.DataBaseReposetory;
import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.util.ReturnInterface;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class HalteViewModel extends AndroidViewModel implements Constants, ReturnInterface {

    private HalteListAdapter halteListAdapter;
    private DataBaseReposetory mRepository;
    private List<Halte> halteList = new ArrayList<>();

    public HalteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataBaseReposetory(application, this);
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

    @Deprecated
    @SuppressWarnings("ConstantConditions")
    public void setCurrentEntiteitId(String entiteitName) {
        if(Utilities.isNotNull(entiteitName) && ENTITEITEN.containsKey(entiteitName)) {
            int currentEntiteitId = ENTITEITEN.get(entiteitName);
            mRepository.getAllHaltesFromEntiteit(currentEntiteitId);
        }
    }

    @Deprecated
    public List<Halte> getHaltesFromSelectedEntiteit(){
        return halteList;
    }


    @Override
    public void getResult(List<Halte> result) {
        halteList = result;
        //halteListAdapter.setHalteItems(halteList);
    }
}
