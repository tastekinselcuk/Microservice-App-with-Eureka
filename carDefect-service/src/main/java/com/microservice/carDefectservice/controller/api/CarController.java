package com.microservice.carDefectservice.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.carDefectservice.business.abstracts.CarService;
import com.microservice.carDefectservice.domain.Car;
import com.microservice.carDefectservice.dto.CarDTO;

import lombok.RequiredArgsConstructor;

/**
 * Rest API for managing cars.
*/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/car")
public class CarController {
	

	private final CarService carService;
	
	/**
	 * Returns a list of all cars.
	 * 
	 * @return a ResponseEntity containing a list of all cars
	*/
	@GetMapping("/getAllCar")
    @PreAuthorize("hasAuthority('admin:read')")
	public ResponseEntity<?> getAllCar(){
        return new ResponseEntity<>(carService.getAllCar(), HttpStatus.OK);
	}
    
	/**
	 * Returns a list of all cars as CarDTOs.
	 * 
	 * @return a ResponseEntity containing a list of all cars as CarDTOs
	*/
    @GetMapping("/getAllCarDto")
    @PreAuthorize("hasAnyAuthority('admin:read', 'teamlead:read')")
    public ResponseEntity<?> getAllCarDto() {
        return new ResponseEntity<>(carService.getAllCarDto(), HttpStatus.OK);
    }
    
	/**
	 * Returns a specific car by ID.
	 * 
	 * @param id the ID of the car to retrieve
	 * @return a ResponseEntity containing the car with the given ID.
	*/
    @GetMapping("/getCarDtoById/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'teamlead:read')")
    public ResponseEntity<?> getCarDtoById(@PathVariable Integer id) {
        return new ResponseEntity<>(carService.getCarDtoById(id), HttpStatus.OK);
    }
    
	/**
	 * Returns a list of all cars.
	 * 
	 * @return a List of all cars
	 */
    @GetMapping("/getPageableCar")
    @PreAuthorize("hasAuthority('teamlead:read')")
    public ResponseEntity<Page<CarDTO>> getPageableCar(@PageableDefault(size = 100) Pageable pageable) {
        Page<CarDTO> carDTOs = carService.getPageableCar(pageable);
        return ResponseEntity.ok(carDTOs);
    }
    
	/**
	 * Adds a new car
	 * 
	 * @param car the car to add
	 * @return a ResponseEntity containing a success message.
	*/
	@PostMapping("/saveCar") 
    @PreAuthorize("hasAuthority('admin:create')")
	public ResponseEntity<String> saveCar(@RequestBody Car car){

		carService.saveCar(car);
        String message = String.format("Car '%s' saved successfully.", car.getCarModel());
        return new ResponseEntity<>(message, HttpStatus.CREATED);

	}
	
    /**
     * Updates an existing car.
     *
     * @param id the ID of the car to update
     * @param car the updated car object
     * @return a ResponseEntity containing a success message.
     */
    @PutMapping("/updateCar/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> updateCar(@PathVariable Integer id, @RequestBody Car car) {

    	carService.updateCar(id, car);
        String message = String.format("Car with id '%s' updated successfully.", id);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    /**
     * Soft delete a car by ID.
     *
     * @param id the ID of the car to delete
     * @return a ResponseEntity containing a success message.
     */
	@PutMapping("/softDeleteCar/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
	public ResponseEntity<String> softDeleteCar(@PathVariable int id) {

		carService.softDeleteCar(id);
        String message = String.format("Soft delet completed successfully for user with id '%s'.", id);
        return new ResponseEntity<>(message, HttpStatus.OK);

	}
}


