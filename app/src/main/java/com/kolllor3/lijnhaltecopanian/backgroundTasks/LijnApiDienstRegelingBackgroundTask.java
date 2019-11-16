package com.kolllor3.lijnhaltecopanian.backgroundTasks;

import android.os.AsyncTask;

import com.kolllor3.lijnhaltecopanian.database.TimeTableDao;
import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.interfaces.WorkerCallback;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LijnApiDienstRegelingBackgroundTask extends AsyncTask<JSONObject, Void, Void> implements Constants {

    private TimeTableDao timeTableDao;
    private WorkerCallback callback;

    public LijnApiDienstRegelingBackgroundTask(TimeTableDao timeTableDao) {
        this.timeTableDao = timeTableDao;
    }

    public LijnApiDienstRegelingBackgroundTask(TimeTableDao timeTableDao, WorkerCallback callback) {
        this.timeTableDao = timeTableDao;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(JSONObject... params) {
        try {
            List<TimeTableItem> timeTableForHalte = timeTableDao.getTimeTableFromDayOfWeekToList(params[1].getInt("haltenummer"), params[1].getInt("dayofweek"));

            boolean isEmpty = (timeTableForHalte.size() == 0);

            JSONArray doorkomstenArray = params[0].getJSONArray("halteDoorkomsten").getJSONObject(0).getJSONArray("doorkomsten");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss", Locale.getDefault());
            Calendar c = Calendar.getInstance();

            for (int i = 0; i < doorkomstenArray.length(); i++) {
                JSONObject object = doorkomstenArray.getJSONObject(i);
                Date date = dateFormat.parse(object.getString("dienstregelingTijdstip"));
                if (Utilities.isNotNull(date)) {
                    c.setTime(date);
                    TimeTableItem newItem = new TimeTableItem(params[1].getInt("haltenummer"), object.getInt("lijnnummer"), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.DAY_OF_WEEK), object.getString("bestemming"));
                    if(isEmpty)
                        timeTableForHalte.add(newItem);
                    else
                        timeTableForHalte.set(i, updateTimeLineItem(timeTableForHalte.get(i), newItem));
                }
            }

            if(isEmpty)
                timeTableDao.insert(timeTableForHalte.toArray(new TimeTableItem[0]));
            else
                timeTableDao.update(timeTableForHalte.toArray(new TimeTableItem[0]));

            if(Utilities.isNotNull(callback)){
                callback.onComplete();
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            if(Utilities.isNotNull(callback)){
                callback.onFailure();
            }
        }
        return null;
    }

    private TimeTableItem updateTimeLineItem(TimeTableItem oldItem, TimeTableItem newItem){
        newItem.setId(oldItem.getId());
        return newItem;
    }
}
