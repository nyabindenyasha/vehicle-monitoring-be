package com.car.tracking.vehicles.messaging;

import com.car.tracking.drivers.context.Driver;
import com.car.tracking.vehicles.vehicle.context.Vehicle;
import lombok.Data;
import lombok.val;

import java.util.Objects;

/**
 * @Author nyabindenyasha
 * @Created 7/27/2021
 * @project car-monitoring-sys
 */

@Data
public class FCMRequest {

    private String to;

    private Notification notification;

    private DataObj data;

    public static FCMRequest fromCommand(Vehicle vehicle, Driver driver) {

        if (Objects.isNull(vehicle) || Objects.isNull(driver))
            return null;

        FCMRequest fcmRequest = new FCMRequest();

        String to = (driver.getToken() == null) ? "/topics/all" : driver.getToken();

        val notification = new Notification();
        notification.setClick_action("FLUTTER_NOTIFICATION_CLICK");
        notification.setContent_available(true);
        notification.setPriority("high");
        notification.setTitle("Vehicle Alert");
        notification.setBody("Vehicle with reg number " + vehicle.getRegNumber() + "  is now out of range.");

        val data = new DataObj();
        data.setClick_action("FLUTTER_NOTIFICATION_CLICK");
        data.setPriority("high");
        data.setBodyText("Vehicle with reg number " + vehicle.getRegNumber() + "  is now out of range.");

        fcmRequest.setTo(to);
        fcmRequest.setNotification(notification);
        fcmRequest.setData(data);
        return fcmRequest;

    }

}

@Data
class Notification {

    private String body;
    private boolean content_available;
    private String click_action;
    private String priority;
    private String title;

}

@Data
class DataObj {

    private String bodyText;
    private String click_action;
    private String priority;

}
