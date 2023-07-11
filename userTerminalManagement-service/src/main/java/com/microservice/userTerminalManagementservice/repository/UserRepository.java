package com.microservice.userTerminalManagementservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.userTerminalManagementservice.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmailAndDeletedFalse(String email);

	List<User> findByDeletedFalse();

	Optional<User> findByIdAndDeletedFalse(Integer id);
	
	Page<User> findByDeletedFalse(Pageable pageable);


}