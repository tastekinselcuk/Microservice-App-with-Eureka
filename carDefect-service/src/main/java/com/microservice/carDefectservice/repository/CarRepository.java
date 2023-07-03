package com.microservice.carDefectservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.carDefectservice.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

	List<Car> findByDeletedFalse();

	Optional<Car> findByCarIdAndDeletedFalse(Integer id);
	
	Page<Car> findByDeletedFalse(Pageable pageable);
	
	
	
}

