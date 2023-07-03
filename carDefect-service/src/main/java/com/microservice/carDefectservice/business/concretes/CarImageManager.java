package com.microservice.carDefectservice.business.concretes;

import java.awt.image.BufferedImage;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.microservice.carDefectservice.business.abstracts.CarImageService;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.microservice.carDefectservice.domain.CarImage;
import com.microservice.carDefectservice.repository.CarImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarImageManager implements CarImageService {
	
    private final CarImageRepository carImageRepository;


	@Override
	public CarImage saveCar(CarImage carImage) {
		return carImageRepository.save(carImage);
	}
	
	@Override
	public Optional<CarImage> getCarImageById(Long carId) {
		return carImageRepository.findById(carId);
	}


	@Override
	public BufferedImage markCarImage(BufferedImage carImage, int x, int y, int size, Color color) {
		return markImage(carImage, x, y, size, color);
	}
	 
	 public static BufferedImage markImage(BufferedImage image, int x, int y, int size, Color color) {
	    BufferedImage markedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D graphics = markedImage.createGraphics();
	    graphics.drawImage(image, 0, 0, null);
	    graphics.setColor(color);
	    graphics.setStroke(new BasicStroke(2));
	    graphics.drawRect(x, y, size, size);
	    graphics.dispose();
	    return markedImage;
	 }

	

}
