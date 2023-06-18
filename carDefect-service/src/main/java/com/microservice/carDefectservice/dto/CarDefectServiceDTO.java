package com.microservice.carDefectservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDefectServiceDTO {
	//Car
    private int carId;
    //Defect
    private String defectPartCategory;
    private String defectPartName;
    private String reportedBy;
    private Date reportedDate;
    //Location
    private double latitude;
    private double longitude;
    //Terminal
    private String terminalName;
    
}
