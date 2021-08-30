package com.car.tracking.drivers.api;

import com.car.tracking.commons.ResponseMessage;
import com.car.tracking.drivers.context.Driver;
import com.car.tracking.drivers.context.DriverCreateContext;
import com.car.tracking.drivers.context.DriverService;
import com.car.tracking.vehicles.vehicle.context.Vehicle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * @Author nyabindenyasha
 * @Created 7/24/2021
 * @project car-monitoring-sys
 */

@CrossOrigin
@RestController
@Api(tags = "Driver")
@RequestMapping("v1/driver")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("")
    @ApiOperation("Get Driver")
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<Driver> drivers = driverService.findAll(pageable, search);
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All Driver")
    public ResponseEntity<?> getAll() {
        try {
            Collection<Driver> driver = driverService.findAll();
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a Driver by Id")
    public ResponseEntity<?> getDriver(@PathVariable long id) {
        try {
            Driver driver = driverService.findById(id);
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findVehicles/byUser/{userId}")
    @ApiOperation("Get a Driver by Id")
    public ResponseEntity<?> getVehiclesByUser(@PathVariable long userId) {
        try {
            List<Vehicle> vehicles = driverService.findByUser(userId);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a Driver by Id")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            driverService.delete(id);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create Driver")
    public ResponseEntity<?> create(@Valid @RequestBody DriverCreateContext request) {
        try {
            Driver driverCreated = driverService.create(request);
            return new ResponseEntity<>(driverCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
