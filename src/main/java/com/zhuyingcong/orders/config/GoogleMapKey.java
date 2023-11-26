package com.zhuyingcong.orders.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GoogleMapKey {

    private static final Logger logger = LoggerFactory.getLogger(GoogleMapKey.class);

    public static final String MAP_API_URL = "https://routes.googleapis.com/directions/v2:computeRoutes";
    public static final String API_KEY_HEADER = "X-Goog-Api-Key";
    public static final String FIELD_MASK_HEADER = "X-Goog-FieldMask";
    public static final String FIELD_MASK_VALUE = "routes.distanceMeters";

    private static final String API_KEY_ENV = "GOOGLE_MAP_API_KEY";

    public static String getGoogleMapApiKey() {
        Map<String, String> env = System.getenv();
        return env.getOrDefault(API_KEY_ENV, "");
    }
}
