package com.microservice.carDefectservice.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.microservice.carDefectservice.business.abstracts.DefectService;
import com.microservice.carDefectservice.domain.Defect;
import com.microservice.carDefectservice.dto.DefectDTO;

/**
 * Rest API for managing defects.
 */
@RestController
@RequestMapping("/api/defect")
public class DefectController {
	
	@Autowired
	private DefectService defectService;
	
    /**
     * Returns a list of all active defects.
     * 
     * @return a ResponseEntity containing a list of all active defects
     */
	@GetMapping("/getAllDefect") 
    @PreAuthorize("hasRole('ADMIN')")
	public List<Defect> getAllDefect(){
		return this.defectService.getAllDefect();
	}
    
    /**
     * Returns a list of all defects as DefectDTOs.
     * 
     * @return a ResponseEntity containing a list of all defects as DefectDTOs
     */
	@GetMapping("/getAllDefectDto") 
    @PreAuthorize("hasRole('ADMIN')")
	public List<DefectDTO> getAllDefectDto(){
		return this.defectService.getAllDefectDto();
	}
	
	/**
     * Returns the defect with the given ID as a DefectDTO.
     * 
     * @param id the ID of the defect to be returned
     * @return a ResponseEntity containing the defect with the given ID as a DefectDTO
     */
	@GetMapping("/getDefectDtoById/{id}") 
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getDefectDtoById(@PathVariable Integer id) {
		
        return new ResponseEntity<>(defectService.getDefectDtoById(id), HttpStatus.OK);

	}
    
	/**
     * Soft deletes the defect with the given ID.
     * 
     * @param DefectId the ID of the defect to be soft deleted
     * @return a ResponseEntity containing a succes message.
     */
    @PutMapping("/softDeleteCarDefectLocation/{DefectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> softDeleteCarDefectLocation(@PathVariable int DefectId) {

        defectService.softDeleteDefect(DefectId);
        String message = String.format("Soft delet completed successfully for defect with id '%s'.", DefectId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
