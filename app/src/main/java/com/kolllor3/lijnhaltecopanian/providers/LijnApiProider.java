package com.kolllor3.lijnhaltecopanian.providers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kolllor3.lijnhaltecopanian.App;
import com.kolllor3.lijnhaltecopanian.constants.Constants;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.LijnCustomRequest;
import com.kolllor3.lijnhaltecopanian.util.LogUtils;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LijnApiProider implements Constants {

    private MutableLiveData<List<TimeTableItem>> timeTable = new MutableLiveData<>();

    public void getDienstRegeling(int haltenummer, int halteentiteit){
        LijnCustomRequest request = new LijnCustomRequest(API_HALE_URL.concat(String.valueOf(halteentiteit)).concat("/").concat(String.valueOf(haltenummer)).concat(DIENSTREGELING_PATH), null, response -> {
            try {
                List<TimeTableItem> timeTableForHalte = new ArrayList<>();
                JSONArray doorkomstenArray = response.getJSONArray("halteDoorkomsten").getJSONObject(0).getJSONArray("doorkomsten");
                LogUtils.logE("response", doorkomstenArray.toString());

                SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd'T'kk:mm:ss", Locale.getDefault());
                Calendar c = Calendar.getInstance();

                for (int i = 0; i < doorkomstenArray.length(); i++) {
                    JSONObject object = doorkomstenArray.getJSONObject(i);
                    Date date = dateFormat.parse(object.getString("dienstregelingTijdstip"));
                    if (Utilities.isNotNull(date)) {
                        c.setTime(date);
                        timeTableForHalte.add(new TimeTableItem(haltenummer, object.getInt("lijnnummer"), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.DAY_OF_WEEK), object.getString("bestemming"), false));
                    }
                }

                timeTable.setValue(timeTableForHalte);
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }, error -> LogUtils.logE("error", error.toString()));
        App.getInstance().addTorequestQueue(request);
    }

    public LiveData<List<TimeTableItem>> getTimeTable() {
        return timeTable;
    }
}
