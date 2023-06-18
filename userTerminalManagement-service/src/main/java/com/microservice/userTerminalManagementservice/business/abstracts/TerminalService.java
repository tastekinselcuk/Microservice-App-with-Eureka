package com.microservice.userTerminalManagementservice.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microservice.userTerminalManagementservice.domain.Terminal;
import com.microservice.userTerminalManagementservice.dto.TerminalDTO;

public interface TerminalService {
	
	List<Terminal> getAllTerminals();
	
	List<TerminalDTO> getAllTerminalDtos();
	
	Terminal saveTerminal(Terminal terminal);
	    
    void changeTerminalStatus(int id);
    
	Page<TerminalDTO> getTerminals(String status, String terminalName, Pageable pageable);

	

}
