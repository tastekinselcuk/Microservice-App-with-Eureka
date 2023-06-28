//package com.microservice.carDefectservice.controller.restController;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//
//import com.microservice.carDefectservice.business.concretes.CarImageManager;
//import com.microservice.carDefectservice.domain.CarImage;
//
//@RestController
//public class CarImageController {
//
//    private final CarImageManager carImageManager;
//
//    public CarImageController(CarImageManager carImageManager) {
//        this.carImageManager = carImageManager;
//    }
//
//    @GetMapping("/car/{carId}/image")
//    @PreAuthorize("hasAuthority('teamlead:read')")
//    public ResponseEntity<byte[]> getCarImage(@PathVariable Long carId) {
//        CarImage carImage = carImageManager.getCarImageById(carId);
//
//        if (carImage == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        byte[] imageBytes = carImage.getBlobImage();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//
//        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//    }
//}
