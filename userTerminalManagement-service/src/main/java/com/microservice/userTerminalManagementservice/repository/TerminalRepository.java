package com.microservice.userTerminalManagementservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.userTerminalManagementservice.domain.Terminal;
import com.microservice.userTerminalManagementservice.enums.TerminalStatus;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer> {

	List<Terminal> findByStatus(TerminalStatus status);
	
	Optional<Terminal> findByTerminalNameAndStatus(String terminalName, TerminalStatus status);


    Optional<Terminal> findByTerminalIdAndStatus(Integer id, TerminalStatus status);
	

}