package com.car.tracking.drivers.context;

import com.car.tracking.commons.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

@Repository
public interface DriverDao extends BaseRepository<Driver> {

    Optional<Driver> findByNationalId(String username);

    Driver getByNationalId(String username);

    boolean existsByNationalId(String username);

    List<Driver> findByUser_Id(long userId);

}
