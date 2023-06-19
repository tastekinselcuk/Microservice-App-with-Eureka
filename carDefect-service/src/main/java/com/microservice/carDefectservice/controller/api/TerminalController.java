package com.microservice.carDefectservice.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.microservice.carDefectservice.business.abstracts.TerminalService;
import com.microservice.carDefectservice.domain.Terminal;
import com.microservice.carDefectservice.dto.TerminalDTO;

/**
 * Rest API for managing terminals.
 */
@RestController
@RequestMapping("/api/terminals")
public class TerminalController {
	
	@Autowired
	private TerminalService terminalService;

	/**
	 * Returns a list of all terminals.
	 * 
	 * @return a List of all terminals
	 */
    @GetMapping("/getAllTerminals")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Terminal> getAllTerminals() {
    	return this.terminalService.getAllTerminals();
    }
    
	/**
	 * Returns a list of all terminals as TerminalDTOs.
	 * 
	 * @return a List of all terminals as TerminalDTOs
	 */
    @GetMapping("/getAllTerminalDtos")
    @PreAuthorize("hasRole('ADMIN')")
    public List<TerminalDTO> getAllTerminalDtos() {
    	return this.terminalService.getAllTerminalDtos();
    }
    
	/**
	 * Adds a new terminal.
	 * 
	 * @param terminal the terminal to add
	 * @return a ResponseEntity containing a success message.
	 */
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> saveTerminal(@RequestBody Terminal terminal){
    	
		terminalService.saveTerminal(terminal);
        String message = String.format("Terminal '%s' saved successfully.", terminal.getTerminalName());
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    
    /**
     * Changes the status of a terminal with the given id.
     * 
     * @param id the ID of the terminal whose status will be changed.
     * @return A ResponseEntity containing a success message.
    */
    @PutMapping("/changeTerminalStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeTerminalStatus(@PathVariable Integer id) {
    	
        terminalService.changeTerminalStatus(id);
        String message = String.format("Terminal's status with id '%s' changed successfully", id);
        return new ResponseEntity<>(message, HttpStatus.OK); 
        
    }
    
    
    
    
}