package com.example.casesyncservice.client;

import java.util.Map;

public class URLConstants {
    public static final String FETCH_CASES_DATA = "/api/v1/case-service/cases";
    public static final String UPDATE_CASES_DATA = "/api/v1/case-service/cases/{caseId}";

    public static String finalURL(final String url, final Map<String, String> argValue) {
        String finalURL = url;
        for (Map.Entry<String, String> p : argValue.entrySet()) {
            finalURL = finalURL.replace(p.getKey(), p.getValue());
        }
        return finalURL;
    }
}
