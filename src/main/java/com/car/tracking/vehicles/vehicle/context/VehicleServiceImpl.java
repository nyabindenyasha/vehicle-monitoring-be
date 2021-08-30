package com.car.tracking.vehicles.vehicle.context;

import com.car.tracking.commons.jpa.BaseServiceImpl;
import com.car.tracking.commons.exceptions.InvalidRequestException;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

@Service
public class VehicleServiceImpl extends BaseServiceImpl<Vehicle, VehicleCreateContext, Vehicle> implements VehicleService {

    private final VehicleDao vehicleDao;

    public VehicleServiceImpl(VehicleDao vehicleDao) {
        super(vehicleDao);
        this.vehicleDao = vehicleDao;
    }

    @Override
    protected Class<Vehicle> getEntityClass() {
        return Vehicle.class;
    }

    @Override
    public Vehicle create(VehicleCreateContext request) {

        boolean detailsExists = existsByRegNumber(request.getRegNumber());

        if (detailsExists) {
            throw new InvalidRequestException("User Account with the same regNumber already exists");
        }

        Vehicle vehicle = Vehicle.fromCommand(request);

        return vehicleDao.save(vehicle);

    }


    @Override
    public Vehicle update(Vehicle request) {

        Vehicle vehicle = findById(request.getId());

//        vehicle.update(request);

        return vehicleDao.save(vehicle);
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
    public boolean existsByRegNumber(String regNumber) {
        return vehicleDao.existsByRegNumber(regNumber);
    }

    @Override
    public Collection<Vehicle> createItems(Collection<VehicleCreateContext> vehicleCreateContextCollection) {

        Collection<Vehicle> vehicleCollection = new ArrayList<>();

        vehicleCreateContextCollection.parallelStream().forEach(request -> {
            val vehicle = create(request);
            vehicleCollection.add(vehicle);
        });

        return vehicleCollection;
    }

    @Override
    public Collection<Vehicle> findByIds(Collection<Long> ids) {
        return vehicleDao.findAllById(ids);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleDao.save(vehicle);
    }

}

