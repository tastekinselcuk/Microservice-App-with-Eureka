package com.microservice.userTerminalManagementservice.dto;

import com.microservice.userTerminalManagementservice.enums.TerminalStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerminalDTO {
	
	private int terminalId;
	private String terminalName;
	private TerminalStatus status;

}
