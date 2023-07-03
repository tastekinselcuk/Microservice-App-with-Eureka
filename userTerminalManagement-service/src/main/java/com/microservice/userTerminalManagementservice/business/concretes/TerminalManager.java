package com.microservice.userTerminalManagementservice.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.userTerminalManagementservice.business.abstracts.TerminalService;
import com.microservice.userTerminalManagementservice.domain.Terminal;
import com.microservice.userTerminalManagementservice.dto.TerminalDTO;
import com.microservice.userTerminalManagementservice.enums.TerminalStatus;
import com.microservice.userTerminalManagementservice.exception.AppException;
import com.microservice.userTerminalManagementservice.repository.TerminalRepository;

import lombok.RequiredArgsConstructor;

/**
 * This class implements the TerminalService interface and provides functionality for managing terminals.
 */
@RequiredArgsConstructor
@Service
public class TerminalManager implements TerminalService {
	
	private final TerminalRepository terminalRepository;

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
	 * 
	 * Returns a paginated list of TerminalDTO objects based on the given filters and pageable information.
	 * 
	 * @param status a String representation of the TerminalStatus filter
	 * @param terminalName a String to filter Terminal objects by name
	 * @param pageable a Pageable object used for pagination
	 * @return a Page of TerminalDTO objects
	 */
	@Override
	public Page<TerminalDTO> getTerminals(String status, String terminalName, Pageable pageable) {
		List<Terminal> allTerminals = terminalRepository.findByStatus(TerminalStatus.ACTIVE);
		// Filters the list of terminals based on the given status parameter, which is expected to be a string representation of a TerminalStatus enum value.
		if (status != null) {
			TerminalStatus terminalStatus = TerminalStatus.valueOf(status.toUpperCase());
			allTerminals = allTerminals.stream().filter(terminal -> terminal.getStatus() == terminalStatus).collect(Collectors.toList());
		}
		
		if (terminalName != null) {
			allTerminals = allTerminals.stream().filter(terminal -> terminal.getTerminalName().contains(terminalName)).collect(Collectors.toList());
		}
		
		List<Terminal> activeTerminals = allTerminals.stream().filter(terminal -> terminal.getStatus() == TerminalStatus.ACTIVE).collect(Collectors.toList());

		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), activeTerminals.size());
		List<TerminalDTO> terminalDTOs;

		if (start <= end) {
			terminalDTOs = activeTerminals.subList(start, end).stream().map(terminal -> new TerminalDTO(terminal.getTerminalId(), terminal.getTerminalName(), terminal.getStatus())).collect(Collectors.toList());
		} else {
			terminalDTOs = List.of();
		}

		return new PageImpl<>(terminalDTOs, pageable, activeTerminals.size());
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
	        return;
	    } else if (terminal.getStatus() == TerminalStatus.INACTIVE) {
	        terminal.setStatus(TerminalStatus.ACTIVE);
	        terminalRepository.save(terminal);
	        return;
	    }
	}


	


}
