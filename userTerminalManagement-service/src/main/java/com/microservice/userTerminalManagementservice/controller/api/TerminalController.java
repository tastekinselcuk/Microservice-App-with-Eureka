//package com.userTerminalManagementService.userTerminalManagementService.controller.api;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.userTerminalManagementService.userTerminalManagementService.business.abstracts.TerminalService;
//import com.userTerminalManagementService.userTerminalManagementService.domain.Terminal;
//import com.userTerminalManagementService.userTerminalManagementService.dto.TerminalDTO;
//import com.userTerminalManagementService.userTerminalManagementService.repository.TerminalRepository;
//
///**
// * Rest API for managing terminals.
// */
//@RestController
//@RequestMapping("/api/terminals")
//public class TerminalController {
//	
//	@Autowired
//	private TerminalService terminalService;
//	@Autowired
//	private TerminalRepository terminalRepository;
//
//	/**
//	 * Returns a list of all terminals.
//	 * 
//	 * @return a List of all terminals
//	 */
//    @GetMapping("/getAllTerminals")
//    public List<Terminal> getAllTerminals() {
//    	return this.terminalService.getAllTerminals();
//    }
//    
//	/**
//	 * Returns a list of all terminals as TerminalDTOs.
//	 * 
//	 * @return a List of all terminals as TerminalDTOs
//	 */
//    @GetMapping("/getAllTerminalDtos")
//    public List<TerminalDTO> getAllTerminalDtos() {
//    	return this.terminalService.getAllTerminalDtos();
//    }
//    
//	/**
//	 * Returns a paginated list of all terminals
//	 * 
//	 * @return a paginated List of all terminals
//	 */
//    @GetMapping("/getPageableTerminals")
//    public Page<Terminal> getTerminals(@PageableDefault(size = 20) Pageable pageable) {
//        return terminalRepository.findAll(pageable);
//    }
//    
//	/**
//	 * Adds a new terminal.
//	 * 
//	 * @param terminal the terminal to add
//	 * @return a ResponseEntity containing a success message.
//	 */
//    @PostMapping("/save")
//    public ResponseEntity<String> saveTerminal(@RequestBody Terminal terminal){
//    	
//		terminalService.saveTerminal(terminal);
//        String message = String.format("Terminal '%s' saved successfully.", terminal.getTerminalName());
//        return new ResponseEntity<>(message, HttpStatus.CREATED);
//    }
//    
//    /**
//     * Changes the status of a terminal with the given id.
//     * 
//     * @param id the ID of the terminal whose status will be changed.
//     * @return A ResponseEntity containing a success message.
//    */
//    @PutMapping("/changeTerminalStatus/{id}")
//    public ResponseEntity<String> changeTerminalStatus(@PathVariable Integer id) {
//    	
//        terminalService.changeTerminalStatus(id);
//        String message = String.format("Terminal's status with id '%s' changed successfully", id);
//        return new ResponseEntity<>(message, HttpStatus.OK); 
//        
//    }
//    
//    
//    
//    
//}
