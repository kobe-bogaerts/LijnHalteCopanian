package com.kolllor3.lijnhaltecopanian.providers;

import com.kolllor3.lijnhaltecopanian.App;
import com.kolllor3.lijnhaltecopanian.backgroundTasks.LijnApiDienstRegelingBackgroundTask;
import com.kolllor3.lijnhaltecopanian.constants.Constants;
import com.kolllor3.lijnhaltecopanian.database.TimeTableDao;
import com.kolllor3.lijnhaltecopanian.util.LijnCustomRequest;
import com.kolllor3.lijnhaltecopanian.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LijnApiProider implements Constants {

    private TimeTableDao timeTableDao;

    public LijnApiProider(TimeTableDao timeTableDao) {
        this.timeTableDao = timeTableDao;
    }

    public void getDienstRegeling(int haltenummer, int halteentiteit){
        LijnCustomRequest request = new LijnCustomRequest(API_HALE_URL.concat(String.valueOf(halteentiteit)).concat("/").concat(String.valueOf(haltenummer)).concat(DIENSTREGELING_PATH), null, response -> {
            try {
                LijnApiDienstRegelingBackgroundTask task = new LijnApiDienstRegelingBackgroundTask(timeTableDao);
                task.execute(response, new JSONObject().put("haltenummer", haltenummer));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> LogUtils.logE("error", error.toString()));
        App.getInstance().addTorequestQueue(request, GET_DIENSTREGELING_TAG);
    }
}
