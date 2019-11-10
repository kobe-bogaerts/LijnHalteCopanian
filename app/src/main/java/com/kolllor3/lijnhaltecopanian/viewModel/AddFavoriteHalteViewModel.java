package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Activity;
import android.app.Application;
import android.location.Location;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.kolllor3.lijnhaltecopanian.adapter.HalteListAdapter;
import com.kolllor3.lijnhaltecopanian.database.DataBaseRepository;
import com.kolllor3.lijnhaltecopanian.model.Halte;

import java.util.ArrayList;
import java.util.List;

public class AddFavoriteHalteViewModel extends BaseHalteViewModel {

    private List<Halte> currentSelectedFavoriteHaltes = new ArrayList<>();

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

    @Override
    public void onCheckboxClicked(View view, Halte halte){
        if(view instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) view;
            if(checkBox.isChecked()){
                currentSelectedFavoriteHaltes.add(halte);
            }else{
                currentSelectedFavoriteHaltes.remove(halte);
            }
        }
    }

    public List<Halte> getCurrentSelectedFavoriteHaltes() {
        return currentSelectedFavoriteHaltes;
    }
}
