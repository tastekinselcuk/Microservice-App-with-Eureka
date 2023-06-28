package com.microservice.carDefectservice.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



import com.microservice.carDefectservice.business.abstracts.CarService;
import com.microservice.carDefectservice.domain.Car;
import com.microservice.carDefectservice.dto.CarDTO;
import com.microservice.carDefectservice.exception.AppException;
import com.microservice.carDefectservice.repository.CarRepository;

import lombok.RequiredArgsConstructor;

/**
 * This class implements the CarService interface and provides methods for managing cars in the system.
 */
@RequiredArgsConstructor
@Service
public class CarManager implements CarService{
	
	private final CarRepository carRepository;


    /**
     * Returns a list of all cars in the system.
     *
     * @return List of Car objects
     */
	@Override
	public List<Car> getAllCar() {
		return carRepository.findByDeletedFalse() ;
	}
	
    /**
     * Returns a list of all car DTOs in the system.
     *
     * @return List of CarDTO objects
     */
    public List<CarDTO> getAllCarDto() {
        List<Car> carList = carRepository.findByDeletedFalse();
        List<CarDTO> carDTOList = new ArrayList<>();
        for (Car car : carList) {
        	carDTOList.add(new CarDTO(car.getCarId(), car.getCarModel()));
        }
        return carDTOList;
    }
    
    /**
     * Returns a car DTO with the specified ID.
     *
     * @param id ID of the car to be returned
     * @return CarDTO object
     */
    @Override
    public CarDTO getCarDtoById(Integer id) {
        if (carRepository.findByCarIdAndDeletedFalse(id) == null) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "No Id Provided",
                    "Please provide id of the record you want to see.",
                    "No id provided for the record to be seen.");
        }    
        Optional<Car> optionalCar = carRepository.findByCarIdAndDeletedFalse(id);
        Car car = optionalCar.get();
        return new CarDTO(car.getCarId(), car.getCarModel());
    }
	
    /**
     * Saves a new car to the system.
     *
     * @param car Car object to be saved
     * @return Saved Car object
     */
	@Override
	public Car saveCar(Car car) {		 
         return carRepository.save(car);
	}
	
    /**
     * Updates an existing car in the system.
     *
     * @param carId ID of the car to be updated
     * @param car Updated Car object
     * @return Updated Car object
     */
	@Override
	public Car updateCar(Integer carId, Car car) {
        if (carRepository.findByCarIdAndDeletedFalse(carId) == null) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "No Id Provided",
                    "Please provide id of the record you want to update.",
                    "No id provided for the record to be updated.");
        } 
        Optional<Car> optionalCar = carRepository.findByCarIdAndDeletedFalse(carId);
        Car existingCar = optionalCar.get();
        existingCar.setCarId(car.getCarId());
        existingCar.setCarModel(car.getCarModel());
		return carRepository.save(existingCar);
	}

    /**
     * Soft deletes a car in the system by setting the "deleted" flag to true.
     *
     * @param carId ID of the car to be deleted
     */
    public void softDeleteCar(int carId) {
        if (carRepository.findByCarIdAndDeletedFalse(carId) == null) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "No Id Provided",
                    "Please provide id of the record you want to delete.",
                    "No id provided for the record to be deleted.");
        } 
        Optional<Car> optionalCar = carRepository.findByCarIdAndDeletedFalse(carId);
        Car existingCar = optionalCar.get();
        existingCar.setDeleted(true);
        carRepository.save(existingCar);
    }


}
