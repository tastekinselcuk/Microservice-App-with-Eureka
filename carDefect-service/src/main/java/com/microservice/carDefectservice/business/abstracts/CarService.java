package com.microservice.carDefectservice.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microservice.carDefectservice.domain.Car;
import com.microservice.carDefectservice.dto.CarDTO;

public interface CarService {

	
	List<Car> getAllCar();
	
    List<CarDTO> getAllCarDto();
    
    CarDTO getCarDtoById(Integer id);
    
	public Car saveCar(Car car);
	
    Car updateCar(Integer id, Car car);
    
    void softDeleteCar(int id);
    
    Page<CarDTO> getPageableCar(Pageable pageable);

	
}

