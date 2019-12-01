package com.kolllor3.lijnhaltecopanian.interfaces;

import java.util.HashMap;
import java.util.Map;

public interface Constants {

    String API_KEY = "9269fdb9cff44305a56fa665545a11cc";
    String API_URL = "https://api.delijn.be/DLKernOpenData/api/v1/";
    String API_HALE_URL = "https://api.delijn.be/DLKernOpenData/api/v1/haltes/";
    String DIENSTREGELING_PATH = "/dienstregelingen";
    String LIJNEN_LIJST_PATH = "lijnen/lijst";
    String DIENSTREGELING_PATH_DATE = "/dienstregelingen?datum=";
    String REALTIME_PATH = "/real-time";
    String REAL_TIME = "REALTIME";
    String GESCHRAPT = "GESCHRAPT";
    String KLEUREN_PATH = "/lijnkleuren";

    Map<String, Integer> ENTITEITEN = new HashMap<String, Integer>(){{
        put("Antwerpen", 1);
        put("Oost-Vlaanderen", 2);
        put("Vlaams-Brabant", 3);
        put("Limburg", 4);
        put("West-Vlaanderen", 5);
    }};

    int LOCATION_PERMISSION_ASK = 9000;
    int LOCATION_PERMISSION_SET = 9001;
    int LOCATION_PERMISSION_RESULT = 9002;
    int CAMERA_PERMISSION_ASK = 9003;
    int CAMERA_PERMISSION_RESULT = 9004;

    String ASK_LOCATION_RETURN_ACTION = "GetData";
    String GET_DIENSTREGELING_TAG = "getDienstregeling";
    String GET_REALTIME_TAG = "getRealTime";
    String GET_LIJN_KLEUREN_TAG = "getLijnKleuren";

}
