package com.car.tracking.vehicles.vehicle.api;

import com.car.tracking.commons.ResponseMessage;
import com.car.tracking.vehicles.vehicle.context.Vehicle;
import com.car.tracking.vehicles.vehicle.context.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */


@CrossOrigin
@RestController
@Api(tags = "Vehicle")
@RequestMapping("v1/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("")
    @ApiOperation("Get Vehicle")
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<Vehicle> vehicles = vehicleService.findAll(pageable, search);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All Vehicle")
    public ResponseEntity<?> getAll() {
        try {
            Collection<Vehicle> vehicle = vehicleService.findAll();
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a Vehicle by Id")
    public ResponseEntity<?> getVehicle(@PathVariable long id) {
        try {
            Vehicle vehicle = vehicleService.findById(id);
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a Vehicle by Id")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            vehicleService.delete(id);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
