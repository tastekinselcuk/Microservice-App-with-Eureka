package com.microservice.carDefectservice.business.concretes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.carDefectservice.business.abstracts.CarDefectService;
import com.microservice.carDefectservice.domain.Car;
import com.microservice.carDefectservice.domain.Defect;
import com.microservice.carDefectservice.domain.Location;
import com.microservice.carDefectservice.domain.Terminal;
import com.microservice.carDefectservice.dto.CarDefectServiceDTO;
import com.microservice.carDefectservice.enums.TerminalStatus;
import com.microservice.carDefectservice.exception.AppException;
import com.microservice.carDefectservice.repository.CarRepository;
import com.microservice.carDefectservice.repository.DefectRepository;
import com.microservice.carDefectservice.repository.LocationRepository;
import com.microservice.carDefectservice.repository.TerminalRepository;

import lombok.RequiredArgsConstructor;

/**
 * The CarDefectManager class is responsible for managing car defects.
 * It implements the CarDefectService interface and provides methods for
 * retrieving and saving car defects using the repositories for the Defect, Car,
 * Location, and Terminal entities.
 * 
 * @see CarDefectService
 * @see DefectRepository
 * @see CarRepository
 * @see LocationRepository
 * @see TerminalRepository
*/
@RequiredArgsConstructor
@Service
public class CarDefectManager implements CarDefectService {
	
    private final DefectRepository defectRepository;
    private final CarRepository carRepository;
    private final LocationRepository locationRepository;
    private final TerminalRepository terminalRepository;

    
    /**
     * Returns a list of all car defects.
     * 
     * @return a list of car defect data transfer objects
     */
	@Override
	public List<CarDefectServiceDTO> getCarDefects() {
        List<CarDefectServiceDTO> result = new ArrayList<>();
        List<Defect> faults = defectRepository.findAll();
        for (Defect fault : faults) {
        	CarDefectServiceDTO dto = new CarDefectServiceDTO();
            dto.setCarId(fault.getCar().getCarId());
            dto.setDefectPartCategory(fault.getDefectPartCategory());
            dto.setDefectPartName(fault.getDefectPartName());
            dto.setReportedBy(fault.getReportedBy());
            dto.setReportedDate(fault.getReportedDate());
            dto.setLatitude(fault.getLocation().getLatitude());
            dto.setLongitude(fault.getLocation().getLongitude());
            dto.setTerminalName(fault.getTerminal().getTerminalName());
            result.add(dto);
        }
        return result;
	}
	
	/**
	 * Saves a car defect using the given parameters.
	 * 
	 * @param carId The ID of the car.
	 * @param defectPartCategory The category of the defect part.
	 * @param defectPartName The name of the defect part.
	 * @param reportedBy The person who reported the defect.
	 * @param latitude The latitude of the location where the defect will be captured in a photo.
	 * @param longitude The longitude of the location where the defect will be captured in a photo.
	 * @param terminalName The name or ID of the terminal where the defect was reported from.
	 */
	@Override
    public void saveCarDefect(int carId, String defectPartCategory, String defectPartName, 
    		String reportedBy, double latitude, double longitude, String terminalName) {
        
        //Car
        Optional<Car> optionalCar = carRepository.findByCarIdAndDeletedFalse(carId);
	    if (optionalCar.isEmpty()) {
	        throw new AppException(
	                HttpStatus.BAD_REQUEST,
	                "No Id Provided",
	                "Please provide id of the record you want to update.",
	                "No id provided for the record to be updated.");
	    } 
        Car car = optionalCar.get();
        
        //Location
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        locationRepository.save(location);
        
        //Terminal
        Optional<Terminal> optionalTerminal= terminalRepository.findByTerminalNameAndStatus(terminalName, TerminalStatus.ACTIVE);
	    if (optionalTerminal.isEmpty()) {
	        throw new AppException(
	                HttpStatus.BAD_REQUEST,
	                "No Id Provided",
	                "Please provide id of the record you want to update.",
	                "No id provided for the record to be updated.");
	    } 
        Terminal terminal = optionalTerminal.get();
        
        //Defect
        Defect defect = new Defect(); 
        defect.setDefectPartCategory(defectPartCategory);
        defect.setDefectPartName(defectPartName);
        defect.setReportedBy(reportedBy);
        defect.setReportedDate(new Date());

        defect.setLocation(location);
        defect.setCar(car);
        defect.setTerminal(terminal);
        location.getDefects().add(defect);
        
        defectRepository.save(defect);
   }

	@Override
	public void getCarDefectById(Integer carId) {
	    Optional<Defect> optionalDefect = defectRepository.findByDefectIdAndDeletedFalse(carId);

	    	Defect defect = optionalDefect.get();
	        
		    //Loading the OpenCV core library
		    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		    //Reading the contents of the image
		    String file ="images/fe428443-ce52-4f30-8a69-120975b96a54.jpg";
		    Mat src = Imgcodecs.imread(file);
		    //Preparing color and position of the marker
		    Scalar color = new Scalar(0, 0, 125);
		    Point point1 = new Point(defect.getLocation().getLatitude(), defect.getLocation().getLongitude());
		    Point point2 = new Point(150, 240);
		    Point point3 = new Point(300, 150);
		    Point point4 = new Point(600, 60);

		    //Drawing marker
		    Imgproc.drawMarker(src, point1, color, Imgproc.MARKER_SQUARE, 150, 8, Imgproc.LINE_8);
		    Imgproc.drawMarker(src, point2, color, Imgproc.MARKER_SQUARE, 150, 8, Imgproc.LINE_8);
		    Imgproc.drawMarker(src, point3, color, Imgproc.MARKER_SQUARE, 150, 8, Imgproc.LINE_8);
		    Imgproc.drawMarker(src, point4, color, Imgproc.MARKER_SQUARE, 150, 8, Imgproc.LINE_8);

		    HighGui.imshow("Drawing Markers", src);
		    HighGui.waitKey(); 
	}
	
	public Page<CarDefectServiceDTO> findAll(Pageable pageable) {
	    Page<Defect> defects = defectRepository.findAll(pageable);
	    return defects.map(this::convertToDto);
	}

	public CarDefectServiceDTO convertToDto(Defect defect) {
	    return CarDefectServiceDTO.builder()
	        .carId(defect.getCar().getCarId())
	        .defectPartCategory(defect.getDefectPartCategory())
	        .defectPartName(defect.getDefectPartName())
	        .reportedBy(defect.getReportedBy())
	        .reportedDate(defect.getReportedDate())
	        .latitude(defect.getLocation().getLatitude())
	        .longitude(defect.getLocation().getLongitude())
	        .terminalName(defect.getTerminal().getTerminalName())
	        .build();
	}




}
