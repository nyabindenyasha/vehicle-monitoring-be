package com.car.tracking.vehicles.coodinates.context;

import com.car.tracking.commons.jpa.BaseService;

import java.util.Collection;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

public interface VehicleCoordinatesService extends BaseService<VehicleCoordinates, VehicleCoordinatesCreateContext, VehicleCoordinates> {

    VehicleCoordinates findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    String createFromGet(long vehicleId, double latitude, double longitude);

    Collection<VehicleCoordinates> findByVehicle(long vehicleId);

    Collection<VehicleCoordinates> findByUser(long userId);

    VehicleCoordinates findLatestByVehicle(long vehicleId);

    void setOutOfRange(long vehicleId);

    String hardwareCheckIfOutOfRange(long vehicleId);

}
