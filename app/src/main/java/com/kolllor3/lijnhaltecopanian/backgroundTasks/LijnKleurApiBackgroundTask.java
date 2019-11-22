package com.kolllor3.lijnhaltecopanian.backgroundTasks;

import android.os.AsyncTask;

import com.kolllor3.lijnhaltecopanian.database.LijnKleurenDao;
import com.kolllor3.lijnhaltecopanian.model.LijnItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LijnKleurApiBackgroundTask extends AsyncTask<JSONObject, Void, List<LijnItem>> {
    private LijnKleurenDao lijnKleurenDao;

    public LijnKleurApiBackgroundTask(LijnKleurenDao lijnKleurenDao) {
        this.lijnKleurenDao = lijnKleurenDao;
    }

    @Override
    protected List<LijnItem> doInBackground(JSONObject... params) {
        List<LijnItem> lijnen = new ArrayList<>();
        try {
            JSONArray lijnenArray = params[0].getJSONArray("lijnLijnkleurCodesLijst");
            for (int i = 0; i < lijnenArray.length(); i++) {
                JSONObject object = lijnenArray.getJSONObject(i);
                JSONObject kleurObject = object.getJSONObject("lijnkleurCodes");
                JSONObject lijnObject = object.getJSONObject("lijn");
                lijnen.add(new LijnItem(lijnObject.getInt("lijnnummer"),lijnObject.getInt("entiteitnummer"), kleurObject.getJSONObject("voorgrond").getString("code"), kleurObject.getJSONObject("achtergrond").getString("code")));
            }
            lijnKleurenDao.insert(lijnen.toArray(new LijnItem[0]));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lijnen;
    }
}
