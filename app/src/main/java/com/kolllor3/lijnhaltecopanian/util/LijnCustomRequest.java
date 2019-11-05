package com.kolllor3.lijnhaltecopanian.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.kolllor3.lijnhaltecopanian.constants.Constants;

import org.json.JSONObject;

import java.util.Map;

public class LijnCustomRequest extends CustomRequest implements Constants {
    public LijnCustomRequest(String url, Map<String, String> params, Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(url, params, reponseListener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = super.getParams();
        params.put("Ocp-Apim-Subscription-Key", API_KEY);
        return params;
    }
}
