package com.kolllor3.lijnhaltecopanian.constants;

import java.util.HashMap;
import java.util.Map;

public interface Constants {

    String API_KEY = "9269fdb9cff44305a56fa665545a11cc";
    String API_URL = "https://api.delijn.be/DLKernOpenData/api/v1/";

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

    String ASK_LOCATION_RETURN_ACTION = "GetData";

}
