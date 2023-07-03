package com.microservice.userTerminalManagementservice.controller.restController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservice.userTerminalManagementservice.business.abstracts.TerminalService;
import com.microservice.userTerminalManagementservice.dto.TerminalDTO;

import lombok.RequiredArgsConstructor;



/**
 * Rest API for managing terminals.
 */
@RestController
@RequestMapping("/api/terminalPage")
@RequiredArgsConstructor
public class TerminalListController {
	
	private final TerminalService terminalService;

	/**
	 * Returns a paginated list of all terminals
	 * 
	 * @return a paginated List of all terminals
	 */
	@GetMapping("/getPageableTerminal")
    @PreAuthorize("hasAuthority('admin:read')")
	public Page<TerminalDTO> getTerminals(@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "terminalName", required = false) String terminalName,
			@PageableDefault(size = 20) @SortDefault(sort = "terminalId", direction = Sort.Direction.ASC) Pageable pageable) {
		return terminalService.getTerminals(status, terminalName, pageable);
	}
    
    
}
