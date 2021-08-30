package com.car.tracking.vehicles.coodinates.context;

import com.car.tracking.commons.exceptions.InvalidRequestException;
import com.car.tracking.commons.exceptions.ItemNotFoundException;
import com.car.tracking.commons.jpa.BaseServiceImpl;
import com.car.tracking.commons.utils.RandomUtils;
import com.car.tracking.commons.utils.Utils;
import com.car.tracking.drivers.context.Driver;
import com.car.tracking.drivers.context.DriverService;
import com.car.tracking.vehicles.messaging.FCMRequest;
import com.car.tracking.vehicles.messaging.PushNotificationsService;
import com.car.tracking.vehicles.vehicle.context.Vehicle;
import com.car.tracking.vehicles.vehicle.context.VehicleService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

@Slf4j
@Service
public class VehicleCoordinatesServiceImpl extends BaseServiceImpl<VehicleCoordinates, VehicleCoordinatesCreateContext, VehicleCoordinates> implements VehicleCoordinatesService {

    private final VehicleCoordinatesDao vehicleCoordinatesDao;

    private final VehicleService vehicleService;

    private final DriverService driverService;

    private final PushNotificationsService pushNotificationsService;

    public VehicleCoordinatesServiceImpl(VehicleCoordinatesDao vehicleCoordinatesDao, VehicleService vehicleService, DriverService driverService, PushNotificationsService pushNotificationsService) {
        super(vehicleCoordinatesDao);
        this.vehicleCoordinatesDao = vehicleCoordinatesDao;
        this.vehicleService = vehicleService;
        this.driverService = driverService;
        this.pushNotificationsService = pushNotificationsService;
    }

    protected Class<VehicleCoordinates> getEntityClass() {
        return VehicleCoordinates.class;
    }

    @Override
    public VehicleCoordinates create(VehicleCoordinatesCreateContext request) {

        val vehicle = vehicleService.findById(request.getVehicleId());

        request.setVehicle(vehicle);

        val validationString = request.getLatitude() + request.getLongitude() + request.getVehicleId();

        val uuid = RandomUtils.generateNameBasedUUID(String.valueOf(validationString));

        val detailsExists = vehicleCoordinatesDao.existsByUuid(uuid);

        if (detailsExists) {
            val todoFromDb = findByUuid(uuid);

            if ((new Date()).before(Utils.addMinutesToJavaUtilDate(todoFromDb.getCreatedDate(), 5)))

                return vehicleCoordinatesDao.findByUuid(uuid).get(0);

        }

        request.setUuid(uuid);

        VehicleCoordinates vehicleCoordinates = VehicleCoordinates.fromCommand(request);

        return vehicleCoordinatesDao.save(vehicleCoordinates);

    }


    @Override
    public VehicleCoordinates update(VehicleCoordinates request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        try {
            super.delete(id);
        } catch (ConstraintViolationException var3) {
            throw new InvalidRequestException("You can not delete this record is might be used by another record");
        }
    }

    @Override
    public VehicleCoordinates findByUuid(String uuid) {
        val todos = vehicleCoordinatesDao.findByUuid(uuid);
        if (!todos.isEmpty())
            return todos.get(todos.size() - 1);
        else
            throw new ItemNotFoundException("TransformerIntrusion record was not found.");
    }

    @Override
    public boolean existsByUuid(String uuid) {
        return vehicleCoordinatesDao.existsByUuid(uuid);
    }

    @Override
    public String createFromGet(long vehicleId, double latitude, double longitude) {
        VehicleCoordinatesCreateContext vehicleCoordinatesCreateContext = new VehicleCoordinatesCreateContext();
        vehicleCoordinatesCreateContext.setVehicleId(vehicleId);
        vehicleCoordinatesCreateContext.setLatitude(latitude);
        vehicleCoordinatesCreateContext.setLongitude(longitude);
        create(vehicleCoordinatesCreateContext);
        return "Y";
    }

    @Override
    public Collection<VehicleCoordinates> findByVehicle(long vehicleId) {
        return vehicleCoordinatesDao.findByVehicle_Id(vehicleId);
    }

    @Override
    public Collection<VehicleCoordinates> findByUser(long userId) {
        val vehicles = driverService.findByUser(userId);
        if(!vehicles.isEmpty())
                    return findByVehicle(vehicles.get(0).getId());
        else
            return new ArrayList<>();
    }

    @Override
    public VehicleCoordinates findLatestByVehicle(long vehicleId) {
        val vehicleCoordinatesList = (List<VehicleCoordinates>) sortById(vehicleCoordinatesDao.findByVehicle_Id(vehicleId));
        return vehicleCoordinatesList.get(vehicleCoordinatesList.size() - 1);
    }

    @Override
    public void setOutOfRange(long vehicleId) {

        val vehicle = vehicleService.findById(vehicleId);
        vehicle.setOutOfRange(true);
        vehicleService.save(vehicle);

        val driver = driverService.findByVehicle(vehicleId);

        val fcmRequest = FCMRequest.fromCommand(vehicle, driver);

        send(vehicle, driver);

    }

    @Override
    public String hardwareCheckIfOutOfRange(long vehicleId) {
        val vehicle = vehicleService.findById(vehicleId);
        return (vehicle.isOutOfRange()) ? "Y" : "N";
    }

    Collection<VehicleCoordinates> sortById(Collection<VehicleCoordinates> oldCollection) {
        Comparator<VehicleCoordinates> orderById = (item1, item2) -> {
            if (item1.getId() > (item2.getId())) return -1;
            else return 1;
        };

        Collection<VehicleCoordinates> newCollection = oldCollection.stream().sorted(orderById.reversed())
                .collect(Collectors.toList());

        return newCollection;
    }

    ResponseEntity send(Vehicle vehicle, Driver driver) throws JSONException {

        String to = (driver.getToken() == null) ? "/topics/all" : driver.getToken();

        JSONObject body = new JSONObject();
        body.put("to", to);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Vehicle Alert");
        notification.put("body", "Vehicle with reg number " + vehicle.getRegNumber() + "  is now out of range.");
        notification.put("content_available", true);
        notification.put("priority", "high");
        notification.put("click_action", "FLUTTER_NOTIFICATION_CLICK");

        JSONObject data = new JSONObject();
        data.put("bodyText", "Vehicle with reg number " + vehicle.getRegNumber() + "  is now out of range.");
        data.put("priority", "high");
        data.put("click_action", "FLUTTER_NOTIFICATION_CLICK");

        body.put("notification", notification);
        body.put("data", data);

        HttpEntity request = new HttpEntity<>(body.toString());

        CompletableFuture pushNotification = pushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = (String) pushNotification.get();

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }

}
