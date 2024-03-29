package com.kolllor3.lijnhaltecopanian.backgroundTasks;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

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
    private MutableLiveData<List<RealTimeItem>> realTimeHolder;

    public LijnApiRealTimeBackgroundTask(MutableLiveData<List<RealTimeItem>> realTimeHolder) {
        this.realTimeHolder = realTimeHolder;
    }

    @Override
    protected List<RealTimeItem> doInBackground(JSONObject... params) {
        List<RealTimeItem> realTimeItems = new ArrayList<>();
        try{
            JSONArray doorkomstenArray = params[0].getJSONArray("halteDoorkomsten").getJSONObject(0).getJSONArray("doorkomsten");
            if(doorkomstenArray.length() > 0) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss", Locale.getDefault());

                for (int i = 0; i < doorkomstenArray.length(); i++) {
                    JSONObject object = doorkomstenArray.getJSONObject(i);
                    String predictionStatus = "";
                    if (object.has("predictionStatussen"))
                        predictionStatus = object.getJSONArray("predictionStatussen").getString(0);
                    Date date = dateFormat.parse(object.getString("dienstregelingTijdstip"));
                    Date dateRealTime = new Date();

                    if (!predictionStatus.equals(GESCHRAPT) && predictionStatus.equals(REAL_TIME))
                        dateRealTime = dateFormat.parse(object.getString("real-timeTijdstip"));

                    if (Utilities.isNotNull(date) && Utilities.isNotNull(dateRealTime)) {
                        Calendar c = Calendar.getInstance();
                        Calendar cRealTime = Calendar.getInstance();
                        c.setTime(date);
                        cRealTime.setTime(dateRealTime);
                        boolean isGeschreapt = predictionStatus.equals(GESCHRAPT);
                        boolean isRealTime = predictionStatus.equals(REAL_TIME);
                        realTimeItems.add(new RealTimeItem(object.getInt("lijnnummer"), isRealTime, isGeschreapt, c, cRealTime, object.getString("bestemming")));
                    }
                }
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return realTimeItems;
    }

    @Override
    protected void onPostExecute(List<RealTimeItem> realTimeItems) {
        realTimeHolder.setValue(realTimeItems);
    }
}