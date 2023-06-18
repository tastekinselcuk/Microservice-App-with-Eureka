package com.microservice.userTerminalManagementservice.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.userTerminalManagementservice.business.abstracts.UserService;
import com.microservice.userTerminalManagementservice.domain.users.User;
import com.microservice.userTerminalManagementservice.dto.UserDTO;

/**
 * Rest API for managing users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
    private UserService userService;

    /**
     * Returns a list of all users.
     * 
     * @return a ResponseEntity containing a list of all users
     */
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
    	return this.userService.getAllUsers();
    }

    /**
     * Returns a list of all users as UserDTOs.
     * 
     * @return a ResponseEntity containing a list of all users as UserDTOs
     */
    @GetMapping("/getAllUserDtos")
    public List<UserDTO> getAllUserDtos() {
    	return this.userService.getAllUserDtos();
    }
    
    /**
     * Returns a specific user by ID.
     * 
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing the user with the given ID.
     */
    @GetMapping("/getUserDtoById/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.checkUserId(authentication,#id)")
    public ResponseEntity<?> getUserDtoById(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.getUserDtoById(id), HttpStatus.OK);
    }


}

