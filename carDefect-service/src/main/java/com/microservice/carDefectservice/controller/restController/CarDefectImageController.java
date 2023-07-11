package com.microservice.carDefectservice.controller.restController;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.carDefectservice.business.abstracts.CarImageService;
import com.microservice.carDefectservice.domain.CarImage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carDefect")
public class CarDefectImageController {
	
    private final CarImageService carImageService;
	

    @PostMapping("/saveCarImage")
    @PreAuthorize("hasAuthority('teamlead:create')")
    public ResponseEntity<String> saveCarImage(@RequestParam("file") MultipartFile file) {
      try {
        byte[] blobImage = file.getBytes();
        CarImage carImage = new CarImage();
        carImage.setBlobImage(blobImage);

        CarImage savedCarImage = carImageService.saveCar(carImage);
        return ResponseEntity.ok("Car image saved with ID: " + savedCarImage.getCarId());
      } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save car image.");
      }
    }
	
    @GetMapping("/getCarImage/{carId}")
    @PreAuthorize("hasAuthority('teamlead:read')")
    public ResponseEntity<byte[]> getCarImage(@PathVariable Long carId,
                                              @RequestParam(value = "markX", required = false, defaultValue = "200") int markX,
                                              @RequestParam(value = "markY", required = false, defaultValue = "100") int markY) {
      Optional<CarImage> carImageOptional = carImageService.getCarImageById(carId);
      if (carImageOptional.isPresent()) {
        CarImage carImage = carImageOptional.get();
        byte[] imageBytes = carImage.getBlobImage();
        BufferedImage bufferedImage;
        try {
          bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        int markSize = 50;
        Color markColor = Color.RED;
        BufferedImage markedImage = carImageService.markCarImage(bufferedImage, markX, markY, markSize, markColor);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
          ImageIO.write(markedImage, "png", baos);
        } catch (IOException e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        byte[] markedImageBytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(markedImageBytes.length);
        return new ResponseEntity<>(markedImageBytes, headers, HttpStatus.OK);
      } else {
        return ResponseEntity.notFound().build();
      }
    }

}


