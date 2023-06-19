package com.microservice.carDefectservice.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Rest API for managing cars.
*/
@RestController
@RequestMapping("/api/car")
public class CarController {
	

	@Autowired
	private CarService carService;
	
	/**
	 * Returns a list of all cars.
	 * 
	 * @return a ResponseEntity containing a list of all cars
	*/
	@GetMapping("/getAllCar")
    @PreAuthorize("hasRole('ADMIN')")
	public List<Car> getAllCar(){
		return this.carService.getAllCar();
	}
    
	/**
	 * Returns a list of all cars as CarDTOs.
	 * 
	 * @return a ResponseEntity containing a list of all cars as CarDTOs
	*/
    @GetMapping("/getAllCarDto")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CarDTO> getAllCarDto() {
        return this.carService.getAllCarDto();
    }
    
	/**
	 * Returns a specific car by ID.
	 * 
	 * @param id the ID of the car to retrieve
	 * @return a ResponseEntity containing the car with the given ID.
	*/
    @GetMapping("/getCarDtoById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCarDtoById(@PathVariable Integer id) {

        return new ResponseEntity<>(carService.getCarDtoById(id), HttpStatus.OK);

    }
    
	/**
	 * Adds a new car
	 * 
	 * @param car the car to add
	 * @return a ResponseEntity containing a success message.
	*/
	@PostMapping("/saveCar") 
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
	@PutMapping("/softDelete/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> softDeleteCar(@PathVariable int carId) {

		carService.softDeleteCar(carId);
        String message = String.format("Soft delet completed successfully for user with id '%s'.", carId);
        return new ResponseEntity<>(message, HttpStatus.OK);

	}
}

