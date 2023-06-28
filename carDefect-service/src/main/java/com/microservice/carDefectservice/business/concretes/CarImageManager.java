//package com.microservice.carDefectservice.business.concretes;
//
//import org.springframework.stereotype.Service;
//
//import com.microservice.carDefectservice.business.abstracts.CarImageService;
//import com.microservice.carDefectservice.domain.CarImage;
//import com.microservice.carDefectservice.repository.CarImageRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Service
//public class CarImageManager implements CarImageService {
//	
//    private final CarImageRepository carImageRepository;
//
//
//    public CarImage getCarImageById(Long carId) {
//        return carImageRepository.findById(carId).orElse(null);
//    }
//
//}
