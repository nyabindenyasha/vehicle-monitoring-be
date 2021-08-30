package com.car.tracking.vehicles.vehicle.context;

import com.car.tracking.commons.jpa.BaseRepository;
import com.car.tracking.users.context.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

@Repository
public interface VehicleDao extends BaseRepository<Vehicle> {

    Optional<UserAccount> findByRegNumber(String regNumber);

    UserAccount getByRegNumber(String regNumber);

    boolean existsByRegNumber(String regNumber);

}
