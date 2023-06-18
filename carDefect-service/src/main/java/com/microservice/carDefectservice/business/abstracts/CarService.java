package com.microservice.carDefectservice.business.abstracts;

import java.util.List;

import com.microservice.carDefectservice.domain.Car;
import com.microservice.carDefectservice.dto.CarDTO;




public interface CarService {

	
	List<Car> getAllCar();
	
    List<CarDTO> getAllCarDto();
    
    CarDTO getCarDtoById(Integer id);
    
	public Car saveCar(Car car);
	
    Car updateCar(Integer id, Car car);
    
    void softDeleteCar(int id);

	
	
}

