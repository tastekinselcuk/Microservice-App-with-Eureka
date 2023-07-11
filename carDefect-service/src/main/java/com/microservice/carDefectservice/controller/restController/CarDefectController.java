package com.microservice.carDefectservice.controller.restController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.carDefectservice.business.abstracts.CarDefectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * The REST controller class that handles HTTP requests related to car defects.
 */
@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/carDefect")
public class CarDefectController {
	
	private final CarDefectService carDefectService;


    /**
     * HTTP POST request that saves a car defect report.
     *
     * @param carId the ID of the car that the defect is related to
     * @param defectPartCategory the category of the defective part
     * @param defectPartName the name of the defective part
     * @param reportedBy the name of the person who reported the defect (default: "selcuk")
     * @param latitude the latitude coordinate of the location where the defect was reported
     * @param longitude the longitude coordinate of the location where the defect was reported
     * @param terminalName the name of the terminal where the defect was reported
     *
     * @return a ResponseEntity object with HTTP status 200 (OK) if the request is successful, or with
     * HTTP status 500 (INTERNAL_SERVER_ERROR) if there is an error
     */
    @PostMapping("/saveCarDefect")
    @PreAuthorize("hasAuthority('operator:create')")
    public ResponseEntity<?> saveCarDefect(@RequestParam(required = true) int carId,
			   @RequestParam(required = true) String defectPartCategory,
               @RequestParam(required = true) String defectPartName,
               @RequestParam(required = false, defaultValue = "selcuk") String reportedBy,
               @RequestParam(required = true) double latitude,
               @RequestParam(required = true) double longitude,
               @RequestParam(required = true) String terminalName) {
    	
    	carDefectService.saveCarDefect(carId, defectPartCategory, defectPartName, reportedBy, latitude, longitude, terminalName);
        log.info("Car Defect saved successfully.");
        String message = String.format("Car defect saved succesfully for car id with: '%s'", carId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

}
