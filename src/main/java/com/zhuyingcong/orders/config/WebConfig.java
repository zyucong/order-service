package com.zhuyingcong.orders.config;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    public static final String MAP_API_URL = "https://routes.googleapis.com/directions/v2:computeRoutes";
    public static final String API_KEY_HEADER = "X-Goog-Api-Key";
    public static final String FIELD_MASK_HEADER = "X-Goog-FieldMask";
    public static final String FIELD_MASK_VALUE = "routes.distanceMeters";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean

    public OkHttpClient httpClient() {
        return new OkHttpClient.Builder()
                .build();
    }
}
