package com.kolllor3.lijnhaltecopanian.providers;

import com.kolllor3.lijnhaltecopanian.App;
import com.kolllor3.lijnhaltecopanian.backgroundTasks.LijnApiDienstRegelingBackgroundTask;
import com.kolllor3.lijnhaltecopanian.database.TimeTableDao;
import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.interfaces.WorkerCallback;
import com.kolllor3.lijnhaltecopanian.util.LijnCustomRequest;
import com.kolllor3.lijnhaltecopanian.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

public class LijnApiProider implements Constants {

    private TimeTableDao timeTableDao;
    private Calendar c;
    private int currentDayOfWeek;
    private int nextDayOfWeek;

    public LijnApiProider(TimeTableDao timeTableDao) {
        this.timeTableDao = timeTableDao;
        c = Calendar.getInstance();
        currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_YEAR, 1);
        nextDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    }

    public void getDienstRegeling(int haltenummer, int halteentiteit){
        LijnCustomRequest request = new LijnCustomRequest(API_HALE_URL.concat(String.valueOf(halteentiteit)).concat("/").concat(String.valueOf(haltenummer)).concat(DIENSTREGELING_PATH), null, response -> {
            try {
                LijnApiDienstRegelingBackgroundTask task = new LijnApiDienstRegelingBackgroundTask(timeTableDao);
                task.execute(response, new JSONObject().put("haltenummer", haltenummer).put("dayofweek", currentDayOfWeek));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> LogUtils.logE("error", error.toString()));
        App.getInstance().addTorequestQueue(request, GET_DIENSTREGELING_TAG);
    }

    public void getDienstRegeling(int haltenummer, int halteentiteit ,WorkerCallback callback){
        LijnCustomRequest request = new LijnCustomRequest(API_HALE_URL.concat(String.valueOf(halteentiteit)).concat("/").concat(String.valueOf(haltenummer)).concat(DIENSTREGELING_PATH_DATE).concat(String.format(Locale.getDefault(),"%d-%d-%d", c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))), null, response -> {
            try {
                LijnApiDienstRegelingBackgroundTask task = new LijnApiDienstRegelingBackgroundTask(timeTableDao, callback);
                task.execute(response, new JSONObject().put("haltenummer", haltenummer).put("halteentiteit", halteentiteit).put("dayofweek", nextDayOfWeek));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> LogUtils.logE("error", error.toString()));
        App.getInstance().addTorequestQueue(request, GET_DIENSTREGELING_TAG);
    }
}
