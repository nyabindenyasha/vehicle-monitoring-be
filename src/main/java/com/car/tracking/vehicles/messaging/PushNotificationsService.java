package com.car.tracking.vehicles.messaging;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author nyabindenyasha
 * @Created 7/28/2021
 * @project car-monitoring-sys
 */

@Service
public class PushNotificationsService {

    private static final String FIREBASE_SERVER_KEY = "AAAA_1S04p0:APA91bFn-IGsI8NBqf2X0oAiZ22kC1MQipzvFuGLMFQzj6_F02riywmObJepWjjE9rkDw_xcrbqVdCwKSKslixvKj3A4y-wbg-duLzaxrnRz1ZFcs-OrTEeB6AWfWDZ5nAUGZw156Unw";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Async
    public CompletableFuture send(HttpEntity entity) {

        RestTemplate restTemplate = new RestTemplate();

        /**
         https://fcm.googleapis.com/fcm/send
         Content-Type:application/json
         Authorization:key=FIREBASE_SERVER_KEY*/

        ArrayList interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }

}
