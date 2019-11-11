package com.kolllor3.lijnhaltecopanian.viewModel;

import android.app.Application;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kolllor3.lijnhaltecopanian.adapter.HalteListAdapter;
import com.kolllor3.lijnhaltecopanian.database.HalteRepository;
import com.kolllor3.lijnhaltecopanian.model.Halte;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHalteViewModel extends AndroidViewModel {

    HalteListAdapter halteListAdapter;
    HalteRepository mRepository;
    private MutableLiveData<Halte> selectedHalte = new MutableLiveData<>();
    private List<Halte> currentSelectedFavoriteHaltes = new ArrayList<>();
    boolean isCheckboxVisible;
    private MutableLiveData<Integer> currentSelectedFavoriteHaltesCount = new MutableLiveData<>(0);

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

    public void onCheckboxClicked(View view, Halte halte){
        if(view instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) view;
            if(checkBox.isChecked()){
                currentSelectedFavoriteHaltes.add(halte);
            }else{
                currentSelectedFavoriteHaltes.remove(halte);
            }
        }
        currentSelectedFavoriteHaltesCount.setValue(currentSelectedFavoriteHaltes.size());
    }

    public List<Halte> getCurrentSelectedFavoriteHaltes() {
        return currentSelectedFavoriteHaltes;
    }

    public void resetCurrentSelectedFavoriteHaltes(){
        currentSelectedFavoriteHaltes.clear();
        currentSelectedFavoriteHaltesCount.setValue(0);
    }

    public MutableLiveData<Integer> getCurrentSelectedFavoriteHaltesCount() {
        return currentSelectedFavoriteHaltesCount;
    }

    public int isCheckboxVisible() {
        return isCheckboxVisible ? View.VISIBLE : View.GONE;
    }

    public boolean getDefaultCheckCondition(){
        return false;
    }
}
