package com.microservice.carDefectservice.business.abstracts;

import java.util.List;

import com.microservice.carDefectservice.domain.Terminal;
import com.microservice.carDefectservice.dto.TerminalDTO;


public interface TerminalService {
	
	List<Terminal> getAllTerminals();
	
	List<TerminalDTO> getAllTerminalDtos();
	
	public Terminal saveTerminal(Terminal terminal);
	    
    void changeTerminalStatus(int id);

	
	

}
