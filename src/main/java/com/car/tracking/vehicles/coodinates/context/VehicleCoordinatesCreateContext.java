package com.car.tracking.vehicles.coodinates.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.car.tracking.vehicles.vehicle.context.Vehicle;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

@Data
public class VehicleCoordinatesCreateContext {

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @JsonIgnore
    private Date date;

    @NotBlank(message = "Vehicle is required.")
    private long vehicleId;

    @JsonIgnore
    private Vehicle vehicle;

    @JsonIgnore
    private String uuid;

}
