package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kolllor3.lijnhaltecopanian.adapter.HalteListAdapter;
import com.kolllor3.lijnhaltecopanian.database.DataBaseRepository;
import com.kolllor3.lijnhaltecopanian.model.Halte;

public abstract class BaseHalteViewModel extends AndroidViewModel {

    HalteListAdapter halteListAdapter;
    DataBaseRepository mRepository;
    private MutableLiveData<Halte> selectedHalte = new MutableLiveData<>();

    BaseHalteViewModel(@NonNull Application application) {
        super(application);
    }

    public void setSelectedHalte(Halte halte){
        selectedHalte.setValue(halte);
    }

    public MutableLiveData<Halte> getSelectedHalte(){
        return selectedHalte;
    }

    public void addFavoriteHalte(int halteNummer){
        mRepository.addFavoriteHalte(halteNummer);
    }

    public void removeFavoriteHalte(int halteNummer){
        mRepository.removeFavoriteHalte(halteNummer);
    }
}
