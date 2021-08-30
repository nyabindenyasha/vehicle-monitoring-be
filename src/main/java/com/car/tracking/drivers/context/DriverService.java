package com.car.tracking.drivers.context;

import com.car.tracking.commons.jpa.BaseService;
import com.car.tracking.vehicles.vehicle.context.Vehicle;

import java.util.List;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

public interface DriverService extends BaseService<Driver, DriverCreateContext, Driver> {

    Driver findByVehicle(long vehicleId);

    List<Vehicle> findByUser(long userId);

}
