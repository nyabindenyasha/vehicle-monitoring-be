package com.car.tracking.vehicles.vehicle.context;

import com.car.tracking.commons.jpa.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

@Data
@Entity
public class Vehicle extends BaseEntity {

    @NotNull
    private String regNumber;

    private String make;

    private String model;

    @Column(columnDefinition = "boolean default false")
    private boolean isOutOfRange;

    public static Vehicle fromCommand(VehicleCreateContext request) {

        if (Objects.isNull(request))
            return null;

        Vehicle vehicle = new Vehicle();
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setRegNumber(request.getRegNumber());
        vehicle.setOutOfRange(false);

        return vehicle;

    }

}
