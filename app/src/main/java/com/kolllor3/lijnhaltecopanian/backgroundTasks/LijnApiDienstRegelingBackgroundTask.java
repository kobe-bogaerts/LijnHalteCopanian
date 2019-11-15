package com.kolllor3.lijnhaltecopanian.backgroundTasks;

import android.os.AsyncTask;

import com.kolllor3.lijnhaltecopanian.constants.Constants;
import com.kolllor3.lijnhaltecopanian.database.TimeTableDao;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
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

public class LijnApiDienstRegelingBackgroundTask extends  AsyncTask<JSONObject, Void, Void> implements Constants {

    private TimeTableDao timeTableDao;

    public LijnApiDienstRegelingBackgroundTask(TimeTableDao timeTableDao) {
        this.timeTableDao = timeTableDao;
    }

    @Override
    protected Void doInBackground(JSONObject... params) {
        try {
            List<TimeTableItem> timeTableForHalte = new ArrayList<>();
            JSONArray doorkomstenArray = params[0].getJSONArray("halteDoorkomsten").getJSONObject(0).getJSONArray("doorkomsten");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss", Locale.getDefault());
            Calendar c = Calendar.getInstance();

            for (int i = 0; i < doorkomstenArray.length(); i++) {
                JSONObject object = doorkomstenArray.getJSONObject(i);
                Date date = dateFormat.parse(object.getString("dienstregelingTijdstip"));
                if (Utilities.isNotNull(date)) {
                    c.setTime(date);
                    timeTableForHalte.add(new TimeTableItem(params[1].getInt("haltenummer"), object.getInt("lijnnummer"), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.DAY_OF_WEEK), object.getString("bestemming")));
                }
            }

            timeTableDao.insert(timeTableForHalte.toArray(new TimeTableItem[0]));
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
