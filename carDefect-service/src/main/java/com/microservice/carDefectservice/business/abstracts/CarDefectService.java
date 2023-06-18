package com.microservice.carDefectservice.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microservice.carDefectservice.domain.Defect;
import com.microservice.carDefectservice.dto.CarDefectServiceDTO;
import jakarta.transaction.Transactional;

@Transactional
public interface CarDefectService {

    List<CarDefectServiceDTO> getCarDefects();
    
    void getCarDefectById(Integer carId);

    
    public void saveCarDefect(int carId, String defectPartCategory, String defectPartName, 
    		String reportedBy, double latitude, double longitude, String terminalName);
    
    Page<CarDefectServiceDTO> findAll(Pageable pageable);

    CarDefectServiceDTO convertToDto(Defect defect);
}
