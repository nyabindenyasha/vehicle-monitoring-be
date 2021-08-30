package com.car.tracking.drivers.context;

import com.car.tracking.commons.exceptions.InvalidRequestException;
import com.car.tracking.commons.jpa.BaseServiceImpl;
import com.car.tracking.users.context.UserAccountService;
import com.car.tracking.vehicles.vehicle.context.Vehicle;
import com.car.tracking.vehicles.vehicle.context.VehicleService;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

@Service
public class DriverServiceImpl extends BaseServiceImpl<Driver, DriverCreateContext, Driver> implements DriverService {

    private final DriverDao driverDao;

    private final UserAccountService userAccountService;

    private final VehicleService vehicleService;

    public DriverServiceImpl(DriverDao driverDao, UserAccountService userAccountService, VehicleService vehicleService) {
        super(driverDao);
        this.driverDao = driverDao;
        this.userAccountService = userAccountService;
        this.vehicleService = vehicleService;
    }

    @Override
    protected Class<Driver> getEntityClass() {
        return Driver.class;
    }

    @Override
    public Driver create(DriverCreateContext request) {

        boolean detailsExists = driverDao.existsByNationalId(request.getNationalId());

        if (detailsExists) {
            throw new InvalidRequestException("Driver with the same national id already exists");
        }

        val userAccountCreateContext = request.getUserAccountCreateContext();
        userAccountCreateContext.setAdmin(false);

        val user = userAccountService.create(userAccountCreateContext);

        request.setUserAccount(user);

        val vehicles = vehicleService.createItems(request.getVehicleCreateContext());

        request.setVehicles(vehicles);

        Driver driver = Driver.fromCommand(request);

        return driverDao.save(driver);

    }


    @Override
    public Driver update(Driver request) {

        Driver driver = findById(request.getId());

//        driver.update(request);

        return driverDao.save(driver);
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
    public Driver findByVehicle(long vehicleId) {

        val drivers = findAll();

        return drivers.parallelStream()
                .filter(driver -> (containsVehicle(driver, vehicleId)))
                .findAny()
                .orElse(null);

    }

    @Override
    public List<Vehicle> findByUser(long userId) {
        val drivers = driverDao.findByUser_Id(userId);

        drivers.parallelStream().forEach(driver -> {
                    if (driver.getUser().isAdmin())
                        throw new InvalidRequestException("The user is not a driver");
                }
        );

        if (!drivers.isEmpty())
            return (List<Vehicle>) drivers.get(0).getDriversVehicles();

        return new ArrayList<>();
    }

    boolean containsVehicle(Driver driver, long vehicleId) {

        AtomicBoolean containsVehicle = new AtomicBoolean(false);

        driver.getDriversVehicles().stream().forEach(vehicle -> {
            if (vehicle.getId() == vehicleId)
                containsVehicle.set(true);
        });
        return containsVehicle.get();
    }

}

