package com.microservice.carDefectservice.controller.restController;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.carDefectservice.dto.CarDefectServiceDTO;
import com.microservice.carDefectservice.dto.DefectDTO;


import lombok.RequiredArgsConstructor;

import com.microservice.carDefectservice.business.abstracts.CarDefectService;
import com.microservice.carDefectservice.business.abstracts.DefectService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carDefect")
public class CarDefectListController {
  
    private final DefectService defectService;
    private final CarDefectService carDefectService;

	/**
	 * Returns a pageable list of all carDefects.
	 * 
	 * @return a pageable list of all carDefects.
	 */
    @GetMapping("/getPageableCarDefect")
    @PreAuthorize("hasAuthority('teamlead:read')")
    public ResponseEntity<Page<CarDefectServiceDTO>> findAllCarDefects(Pageable pageable) {
        Page<CarDefectServiceDTO> carDefects = carDefectService.getPageableCarDefect(pageable);
        return new ResponseEntity<>(carDefects, HttpStatus.OK);
    }
    
	/**
	 * Returns a list of all carDefects.
	 * 
	 * @return a List of all carDefects
	 */
    @GetMapping("/getCarDefect")
    @PreAuthorize("hasAuthority('teamlead:read')")
    public ResponseEntity<List<CarDefectServiceDTO>> getCarDefects() {
        List<CarDefectServiceDTO> carDefects = carDefectService.getCarDefects();
        return new ResponseEntity<>(carDefects, HttpStatus.OK);
    }


	/**
	 * Returns a list of all defects.
	 * 
	 * @return a List of all defects
	 */
    @GetMapping("/getPageableDefect")
    @PreAuthorize("hasAuthority('teamlead:read')")
    public ResponseEntity<Page<DefectDTO>> getPageableDefect(@PageableDefault(size = 20) Pageable pageable) {
        Page<DefectDTO> defectDTOs = defectService.getPageableDefect(pageable);
        return ResponseEntity.ok(defectDTOs);
    }
    

}
