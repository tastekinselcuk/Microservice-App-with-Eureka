package com.microservice.authservice.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.authservice.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
	
	boolean existsByEmailAndDeletedFalse(String email);

	List<User> findByDeletedFalse();

	Optional<User> findByIdAndDeletedFalse(Integer id);
}
