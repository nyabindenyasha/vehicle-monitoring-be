package com.car.tracking.vehicles.vehicle.context;

import com.car.tracking.commons.jpa.BaseService;

import java.util.Collection;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

public interface VehicleService extends BaseService<Vehicle, VehicleCreateContext, Vehicle> {

    boolean existsByRegNumber(String regNumber);

    Collection<Vehicle> createItems(Collection<VehicleCreateContext> vehicleCreateContextCollection);

    Collection<Vehicle> findByIds(Collection<Long> ids);

    Vehicle save(Vehicle vehicle);

}
