package com.zhuyingcong.orders.service;

import com.zhuyingcong.orders.config.WebConfig;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.zhuyingcong.orders.config.GoogleMapKey.GOOGLE_MAP_API_KEY;
import static com.zhuyingcong.orders.config.WebConfig.*;

@Service
public class DistanceService {

    private static final Logger logger = LoggerFactory.getLogger(DistanceService.class);
    private static final MediaType MEDIA_TYPE_JSON =
            MediaType.parse("application/json; charset=utf-8");

    @Autowired
    private WebConfig webConfig;

    public int getDistance(List<String> origin, List<String> destination) {
        String str = "{" +
                "\"origin\": { \"location\": { \"latLng\": {" +
                "\"latitude\": " + origin.get(0) + ", " +
                "\"longitude\": " + origin.get(1) + "} } }," +
                "\"destination\": {\"location\": { \"latLng\": {" +
                "\"latitude\": " + destination.get(0) + ", " +
                "\"longitude\": " + destination.get(1) + "} } }" +
                "}";
        JSONObject bodyObject = new JSONObject(str);
        logger.info("body:{}", bodyObject);
        RequestBody body = RequestBody.create(str, MEDIA_TYPE_JSON);
        OkHttpClient client = webConfig.httpClient();
        Request request = new Request.Builder()
                .url(MAP_API_URL)
                .header("Content-Type", "application/json")
                .header(API_KEY_HEADER, GOOGLE_MAP_API_KEY)
                .header(FIELD_MASK_HEADER, FIELD_MASK_VALUE)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            logger.info("response:{}", responseBody);
            JSONObject respObj = new JSONObject(responseBody);
            JSONArray routes = respObj.getJSONArray("routes");
            JSONObject distanceMeters = routes.getJSONObject(0);
            return Integer.parseInt(distanceMeters.get("distanceMeters").toString());
        } catch (Exception ex) {
            logger.error("get distance fail! {}", ex.getMessage());
            return 0;
        }
    }
}
