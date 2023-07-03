package com.microservice.carDefectservice.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microservice.carDefectservice.domain.Defect;
import com.microservice.carDefectservice.dto.DefectDTO;


public interface DefectService {

	List<Defect> getAllDefect();
	
	List<DefectDTO> getAllDefectDto();
	
	DefectDTO getDefectDtoById(int id);
	
    void softDeleteDefect(int DefectId);
    
    Page<DefectDTO> getPageableDefect(Pageable pageable);



}
