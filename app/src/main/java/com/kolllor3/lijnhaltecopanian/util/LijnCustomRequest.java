package com.kolllor3.lijnhaltecopanian.util;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.kolllor3.lijnhaltecopanian.interfaces.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LijnCustomRequest extends CustomRequest implements Constants {
    public LijnCustomRequest(String url, Map<String, String> params, Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(url, params, reponseListener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> params = new HashMap<>();
        params.put("Ocp-Apim-Subscription-Key", API_KEY);
        return params;
    }
}
