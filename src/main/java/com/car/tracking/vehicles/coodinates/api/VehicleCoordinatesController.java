package com.car.tracking.vehicles.coodinates.api;

import com.car.tracking.commons.ResponseMessage;
import com.car.tracking.vehicles.coodinates.context.VehicleCoordinates;
import com.car.tracking.vehicles.coodinates.context.VehicleCoordinatesCreateContext;
import com.car.tracking.vehicles.coodinates.context.VehicleCoordinatesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author nyabindenyasha
 * @created 05/06/2021
 * @project - vehicleCoordinates-app
 **/

@CrossOrigin
@RestController
@Api(tags = "VehicleCoordinates")
@RequestMapping("v1/vehicleCoordinates")
public class VehicleCoordinatesController {

    private final VehicleCoordinatesService vehicleCoordinatesService;

    public VehicleCoordinatesController(VehicleCoordinatesService vehicleCoordinatesService) {
        this.vehicleCoordinatesService = vehicleCoordinatesService;
    }

    @GetMapping("")
    @ApiOperation("Get VehicleCoordinates")
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<VehicleCoordinates> vehicleCoordinatess = vehicleCoordinatesService.findAll(pageable, search);
            return new ResponseEntity<>(vehicleCoordinatess, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All VehicleCoordinates")
    public ResponseEntity<?> getAll() {
        try {
            Collection<VehicleCoordinates> vehicleCoordinates = vehicleCoordinatesService.findAll();
            return new ResponseEntity<>(vehicleCoordinates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a VehicleCoordinates by Id")
    public ResponseEntity<?> getVehicleCoordinates(@PathVariable long id) {
        try {
            VehicleCoordinates vehicleCoordinates = vehicleCoordinatesService.findById(id);
            return new ResponseEntity<>(vehicleCoordinates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByVehicle/{vehicleId}")
    @ApiOperation("Get All Vehicle Coordinates By Vehicle")
    public ResponseEntity<?> findTransformerIntrusionsByUser(@PathVariable long vehicleId) {
        try {
            Collection<VehicleCoordinates> vehicleCoordinates = vehicleCoordinatesService.findByVehicle(vehicleId);
            return new ResponseEntity<>(vehicleCoordinates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByUser/{userId}")
    @ApiOperation("Get All Vehicle Coordinates By User")
    public ResponseEntity<?> findVehicleCoordinatesByUser(@PathVariable long userId) {
        try {
            Collection<VehicleCoordinates> vehicleCoordinates = vehicleCoordinatesService.findByUser(userId);
            return new ResponseEntity<>(vehicleCoordinates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/outOfRange/{vehicleId}")
    @ApiOperation("Trigger an out of range")
    public ResponseEntity<?> setOutOfRange(@PathVariable long vehicleId) {
        try {
            vehicleCoordinatesService.setOutOfRange(vehicleId);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("hardwareCheck/outOfRange/{vehicleId}")
    @ApiOperation("Hardware Check If Out Of range")
    public String hardwareCheckIfOutOfRange(@PathVariable long vehicleId) {
        return vehicleCoordinatesService.hardwareCheckIfOutOfRange(vehicleId);
    }

    @GetMapping("/latestCoord/findByVehicle/{vehicleId}")
    @ApiOperation("Get Latest Vehicle Coordinates By Vehicle")
    public ResponseEntity<?> findLatestByVehicle(@PathVariable long vehicleId) {
        try {
            VehicleCoordinates vehicleCoordinate = vehicleCoordinatesService.findLatestByVehicle(vehicleId);
            return new ResponseEntity<>(vehicleCoordinate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a VehicleCoordinates by Id")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            vehicleCoordinatesService.delete(id);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create VehicleCoordinates")
    public ResponseEntity<?> create(@Valid @RequestBody VehicleCoordinatesCreateContext request) {
        try {
            VehicleCoordinates vehicleCoordinatesCreated = vehicleCoordinatesService.create(request);
            return new ResponseEntity<>(vehicleCoordinatesCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/create-from-hardware")
    @ApiOperation("Create Coordinates record By Request Param")
    public String create(@RequestParam long vehicleId, @RequestParam double latitude, @RequestParam double longitude) {
        try {
            return vehicleCoordinatesService.createFromGet(vehicleId, latitude, longitude);
        } catch (Exception e) {
            return "N";
        }
    }

}
