package com.car.tracking.drivers.context;

import com.car.tracking.commons.jpa.BaseEntity;
import com.car.tracking.users.context.UserAccount;
import com.car.tracking.vehicles.vehicle.context.Vehicle;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

@Data
@Entity
public class Driver extends BaseEntity {

    @NotNull
    private String nationalId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "driver_vehicles",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id"))
    private Collection<Vehicle> driversVehicles;

    private String token;

    public static Driver fromCommand(DriverCreateContext driverCreateContext) {

        if (Objects.isNull(driverCreateContext))
            return null;

        Driver driver = new Driver();
        driver.setNationalId(driverCreateContext.getNationalId());
        driver.setUser(driverCreateContext.getUserAccount());
        driver.setDriversVehicles(driverCreateContext.getVehicles());

        return driver;

    }

}
