package com.kolllor3.lijnhaltecopanian.backgroundTasks;

import android.os.AsyncTask;

import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.model.RealTimeItem;
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

public class LijnApiRealTimeBackgroundTask extends AsyncTask<JSONObject, Void, List<RealTimeItem>> implements Constants {
    @Override
    protected List<RealTimeItem> doInBackground(JSONObject... params) {
        List<RealTimeItem> realTimeItems = new ArrayList<>();
        try{
            JSONArray doorkomstenArray = params[0].getJSONArray("halteDoorkomsten").getJSONObject(0).getJSONArray("doorkomsten");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss", Locale.getDefault());

            for (int i = 0; i < doorkomstenArray.length(); i++) {
                JSONObject object = doorkomstenArray.getJSONObject(i);
                Date date = dateFormat.parse(object.getString("dienstregelingTijdstip"));
                Date dateRealTime = dateFormat.parse(object.getString("real-timeTijdstip"));
                if (Utilities.isNotNull(date) && Utilities.isNotNull(dateRealTime)) {
                    Calendar c = Calendar.getInstance();
                    Calendar cRealTime = Calendar.getInstance();
                    c.setTime(date);
                    cRealTime.setTime(dateRealTime);
                    String predictionStatus = object.getJSONArray("predictionStatussen").getString(0);
                    realTimeItems.add(new RealTimeItem(object.getInt("lijnnummer"), predictionStatus.equals(REAL_TIME), c, cRealTime, object.getString("bestemming")));
                }
            }

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return realTimeItems;
    }
}