package com.car.tracking.vehicles.coodinates.context;

import com.car.tracking.commons.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.car.tracking.vehicles.vehicle.context.Vehicle;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project TransformerIntrusion-app
 */

@Data
@Entity
public class VehicleCoordinates extends BaseEntity {

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotNull
    private Date date;

    @ManyToOne
    private Vehicle vehicle;

    @JsonIgnore
    @NotBlank
    private String uuid;

    public static VehicleCoordinates fromCommand(VehicleCoordinatesCreateContext request) {

        if (request == null) {
            return null;
        }

        VehicleCoordinates vehicleCoordinates = new VehicleCoordinates();
        vehicleCoordinates.setLatitude(request.getLatitude());
        vehicleCoordinates.setLongitude(request.getLongitude());
        vehicleCoordinates.setDate(new Date());
        vehicleCoordinates.setVehicle(request.getVehicle());
        vehicleCoordinates.setUuid(request.getUuid());

        return vehicleCoordinates;
    }

}
