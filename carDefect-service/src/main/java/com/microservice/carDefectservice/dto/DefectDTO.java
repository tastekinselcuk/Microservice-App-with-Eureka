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
public class DefectDTO {

	private int defectId;
	private String defectPartCategory;
	private String defectPartName;
	private String reportedBy;
	private Date reportedDate;
	private Double latitude;
	private Double longitude;
	private String terminalName;

}
