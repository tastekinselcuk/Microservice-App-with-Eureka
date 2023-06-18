package com.microservice.carDefectservice.controller.restController;

import java.io.IOException;
import java.util.Optional;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.carDefectservice.domain.Defect;
import com.microservice.carDefectservice.repository.DefectRepository;

@RestController
@RequestMapping("/api/carDefectImage")
public class CarDefectImageController {
	
    @Autowired
    private DefectRepository defectRepository;
	
	@GetMapping("/createImage/{carId}")
    @PreAuthorize("hasRole('TEAMLEAD')")
	public void createImage () {
		
		
	}
	


    @GetMapping("/car-defects/{carId}")
    @PreAuthorize("hasRole('TEAMLEAD')")
    public ResponseEntity<byte[]> getCarDefectById(@PathVariable Integer carId) throws IOException {
        // Load the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Read the contents of the image
        String file = "images/fe428443-ce52-4f30-8a69-120975b96a54.jpg";
        Mat src = Imgcodecs.imread(file);
        
	    Optional<Defect> optionalDefect = defectRepository.findByDefectIdAndDeletedFalse(carId);
    	Defect defect = optionalDefect.get();


        // Prepare color and position of the marker
        Scalar color = new Scalar(0, 0, 125);
        Point point1 = new Point(defect.getLocation().getLatitude(), defect.getLocation().getLongitude());
        Point point2 = new Point(150, 240);
        Point point3 = new Point(300, 150);
        Point point4 = new Point(600, 60);

        // Draw marker
        Imgproc.drawMarker(src, point1, color, Imgproc.MARKER_SQUARE, 150, 8, Imgproc.LINE_8);
        Imgproc.drawMarker(src, point2, color, Imgproc.MARKER_SQUARE, 150, 8, Imgproc.LINE_8);
        Imgproc.drawMarker(src, point3, color, Imgproc.MARKER_SQUARE, 150, 8, Imgproc.LINE_8);
        Imgproc.drawMarker(src, point4, color, Imgproc.MARKER_SQUARE, 150, 8, Imgproc.LINE_8);

        // Convert the image to a byte array
        byte[] imageBytes = new byte[(int)(src.width() * src.height() * src.elemSize())];
        src.get(0, 0, imageBytes);

        // Return the image bytes as a response
        return ResponseEntity.ok(imageBytes);
    }

}
