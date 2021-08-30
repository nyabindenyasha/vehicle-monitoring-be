package com.car.tracking.drivers.context;

import com.car.tracking.users.context.UserAccount;
import com.car.tracking.users.context.UserAccountCreateContext;
import com.car.tracking.vehicles.vehicle.context.Vehicle;
import com.car.tracking.vehicles.vehicle.context.VehicleCreateContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Collection;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

@Data
public class DriverCreateContext {

    private UserAccountCreateContext userAccountCreateContext;

    private String nationalId;

    private Collection<VehicleCreateContext> vehicleCreateContext;

    @JsonIgnore
    private UserAccount userAccount;

    @JsonIgnore
    private Collection<Vehicle> vehicles;

}
