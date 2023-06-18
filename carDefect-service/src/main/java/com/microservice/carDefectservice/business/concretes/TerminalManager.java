package com.microservice.carDefectservice.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.carDefectservice.domain.Terminal;
import com.microservice.carDefectservice.dto.TerminalDTO;
import com.microservice.carDefectservice.enums.TerminalStatus;
import com.microservice.carDefectservice.exception.AppException;
import com.microservice.carDefectservice.repository.TerminalRepository;

import com.microservice.carDefectservice.business.abstracts.TerminalService;

/**
 * This class implements the TerminalService interface and provides functionality for managing terminals.
 */
@Service
public class TerminalManager implements TerminalService {
	
	@Autowired
	TerminalRepository terminalRepository;

    /**
     * Returns a list of all terminals in the system.
     *
     * @return List of Terminal objects
     */
	@Override
	public List<Terminal> getAllTerminals() {
	    return terminalRepository.findByStatus(TerminalStatus.ACTIVE);
	}
	
    /**
     * Returns a list of all terminalDtos in the system.
     *
     * @return List of Terminal objects
     */
	@Override
	public List<TerminalDTO> getAllTerminalDtos() {
    	List<Terminal> terminalList = terminalRepository.findByStatus(TerminalStatus.ACTIVE);
    	List<TerminalDTO> terminalDTOList = new ArrayList<>();
    	for(Terminal terminal : terminalList) {
    		terminalDTOList.add(new TerminalDTO(terminal.getTerminalId(), terminal.getTerminalName(), terminal.getStatus()));
    	}
		return terminalDTOList;
	}

    /**
     * Saves a new terminal to the system.
     *
     * @param terminal Terminal object to be saved
     * @return Terminal object that was saved
     */
	@Override
	public Terminal saveTerminal(Terminal terminal) {
		return terminalRepository.save(terminal);
	}
	
    /**
     * Changes the status of a terminal based on the provided id.
     *
     * @param id ID of the terminal to be updated
     */
	@Override
	public void changeTerminalStatus(int id) {
	    Optional<Terminal> optionalTerminal = terminalRepository.findById(id);
	    if (optionalTerminal.isEmpty()) {
	        throw new AppException(
	                HttpStatus.BAD_REQUEST,
	                "No Id Provided",
	                "Please provide id of the record you want to update.",
	                "No id provided for the record to be updated.");
	    } 
	    
	    Terminal terminal = optionalTerminal.get();
	    
	    if (terminal.getStatus() == TerminalStatus.ACTIVE) {
	        terminal.setStatus(TerminalStatus.INACTIVE);
	        terminalRepository.save(terminal);
	        throw new AppException(
	                HttpStatus.OK,
	                "Terminal status changed to Inactive",
	                "The terminal record you want to update is Inactived.",
	                "The terminal record you want to update is Inactived.");
	    } else if (terminal.getStatus() == TerminalStatus.INACTIVE) {
	        terminal.setStatus(TerminalStatus.ACTIVE);
	        terminalRepository.save(terminal);
	        throw new AppException(
	                HttpStatus.OK,
	                "Terminal status changed to Active",
	                "The terminal record you want to update is activated.",
	                "The terminal record you want to update is activated.");
	    } else {
	        throw new AppException(
	                HttpStatus.INTERNAL_SERVER_ERROR,
	                "Invalid Terminal Status",
	                "The terminal record you want to update has an invalid status: " + terminal.getStatus(),
	                "The terminal record you want to update has an invalid status: " + terminal.getStatus());
	    }
	}

	


}
