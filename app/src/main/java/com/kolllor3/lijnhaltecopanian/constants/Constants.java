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

}
