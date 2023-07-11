package com.microservice.userTerminalManagementservice.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.microservice.userTerminalManagementservice.business.abstracts.TerminalService;
import com.microservice.userTerminalManagementservice.domain.Terminal;
import com.microservice.userTerminalManagementservice.dto.TerminalDTO;

import lombok.RequiredArgsConstructor;

/**
 * Rest API for managing terminals.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/terminal")
public class TerminalController {
	
	private final TerminalService terminalService;

	/**
	 * Returns a list of all terminals.
	 * 
	 * @return a List of all terminals
	 */
    @GetMapping("/getAllTerminal")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<Terminal> getAllTerminals() {
    	return this.terminalService.getAllTerminals();
    }
    
	/**
	 * Returns a list of all terminals as TerminalDTOs.
	 * 
	 * @return a List of all terminals as TerminalDTOs
	 */
    @GetMapping("/getAllTerminalDto")
    @PreAuthorize("hasAnyAuthority('admin:read', 'teamlead:read')")
    public List<TerminalDTO> getAllTerminalDtos() {
    	return this.terminalService.getAllTerminalDtos();
    }
    
	/**
	 * Returns a paginated list of all terminals
	 * 
	 * @return a paginated List of all terminals
	 */
    @GetMapping("/getPageableTerminal")
    @PreAuthorize("hasAnyAuthority('admin:read', 'teamlead:read')")
    public Page<TerminalDTO> getTerminals(@PageableDefault(size = 20) String status, String terminalName, Pageable pageable) {
        return terminalService.getTerminals(status, terminalName, pageable);
    }
    
	/**
	 * Adds a new terminal.
	 * 
	 * @param terminal the terminal to add
	 * @return a ResponseEntity containing a success message.
	 */
    @PostMapping("/saveTerminal")
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
