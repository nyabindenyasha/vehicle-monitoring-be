package com.car.tracking.vehicles.vehicle.context;

import lombok.Data;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

@Data
public class VehicleCreateContext {

    private String regNumber;

    private String make;

    private String model;

}
