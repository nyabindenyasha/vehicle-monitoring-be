package com.car.tracking.vehicles.coodinates.context;

import com.car.tracking.commons.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

@Repository
public interface VehicleCoordinatesDao extends BaseRepository<VehicleCoordinates> {

    List<VehicleCoordinates> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    Collection<VehicleCoordinates> findByVehicle_Id(long vehicleId);

}


