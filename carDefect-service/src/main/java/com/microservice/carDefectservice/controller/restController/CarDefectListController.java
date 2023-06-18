package com.microservice.carDefectservice.controller.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.carDefectservice.domain.Defect;
import com.microservice.carDefectservice.dto.CarDefectServiceDTO;
import com.microservice.carDefectservice.repository.DefectRepository;
import com.microservice.carDefectservice.business.abstracts.CarDefectService;

@RestController
@RequestMapping("/api/page")
public class CarDefectListController {
  
    @Autowired
    private DefectRepository defectRepository;


    @Autowired
    private CarDefectService carDefectService;

	/**
	 * Returns a pageable list of all carDefects.
	 * 
	 * @return a pageable list of all carDefects.
	 */
    @GetMapping("/PageableCarDefects")
    @PreAuthorize("hasRole('TEAMLEAD')")
    public ResponseEntity<Page<CarDefectServiceDTO>> findAllCarDefects(Pageable pageable) {
        Page<CarDefectServiceDTO> carDefects = carDefectService.findAll(pageable);
        return ResponseEntity.ok(carDefects);
    }
    
	/**
	 * Returns a list of all carDefects.
	 * 
	 * @return a List of all carDefects
	 */
    @GetMapping("/carDefects")
    @PreAuthorize("hasRole('TEAMLEAD')")
    public ResponseEntity<List<CarDefectServiceDTO>> getCarDefects() {
        List<CarDefectServiceDTO> carDefects = carDefectService.getCarDefects();
        return new ResponseEntity<>(carDefects, HttpStatus.OK);
    }


	/**
	 * Returns a list of all defects.
	 * 
	 * @return a List of all defects
	 */
    @GetMapping("/defects")
    @PreAuthorize("hasRole('TEAMLEAD')")
    public Page<Defect> getDefects(@PageableDefault(size = 20) Pageable pageable) {
        return defectRepository.findAll(pageable);
    }
    
    

}
