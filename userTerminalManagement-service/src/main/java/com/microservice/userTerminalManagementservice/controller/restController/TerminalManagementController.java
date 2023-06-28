package com.microservice.userTerminalManagementservice.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.userTerminalManagementservice.business.abstracts.TerminalService;
import com.microservice.userTerminalManagementservice.domain.Terminal;

import lombok.RequiredArgsConstructor;
/**
 * Rest API for managing terminals.
 */
@RestController
@RequestMapping("/api/terminal/manager")
@RequiredArgsConstructor
public class TerminalManagementController {
	
	@Autowired
	private final TerminalService terminalService;
    
	/**
	 * Adds a new terminal.
	 * 
	 * @param terminal the terminal to add
	 * @return a ResponseEntity containing a success message.
	 */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('admin:create')")
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
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> changeTerminalStatus(@PathVariable Integer id) {
        terminalService.changeTerminalStatus(id);
        String message = String.format("Terminal's status with id '%s' changed successfully", id);
        return new ResponseEntity<>(message, HttpStatus.OK); 
        
    }
	
}
