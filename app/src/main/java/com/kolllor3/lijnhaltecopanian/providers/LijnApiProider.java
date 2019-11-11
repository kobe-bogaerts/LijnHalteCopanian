package com.kolllor3.lijnhaltecopanian.providers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kolllor3.lijnhaltecopanian.App;
import com.kolllor3.lijnhaltecopanian.constants.Constants;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.LijnCustomRequest;
import com.kolllor3.lijnhaltecopanian.util.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LijnApiProider implements Constants {

    LiveData<List<TimeTableItem>> timeTable = new MutableLiveData<>();

    public void getDienstRegeling(int halteentiteit, int haltenummer){
        LijnCustomRequest request = new LijnCustomRequest(API_HALE_URL.concat(String.valueOf(halteentiteit)).concat("/").concat(String.valueOf(haltenummer)).concat(DIENSTREGELING_PATH), null, response -> {
            try {
                JSONArray doorkomstenArray = response.getJSONArray("doorkomsten");
                LogUtils.logE("response", doorkomstenArray.toString());
                //todo: create timetable
                for (int i = 0; i < doorkomstenArray.length(); i++) {
                    JSONObject object = doorkomstenArray.getJSONObject(i);
                    
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            LogUtils.logE("error", error.toString());
        });
        App.getInstance().addTorequestQueue(request);
    }

    public LiveData<List<TimeTableItem>> getTimeTable() {
        return timeTable;
    }
}
