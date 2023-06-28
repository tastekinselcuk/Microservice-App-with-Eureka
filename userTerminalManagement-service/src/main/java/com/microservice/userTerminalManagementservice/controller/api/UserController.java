package com.microservice.userTerminalManagementservice.controller.api;

import java.util.List;

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


import com.microservice.userTerminalManagementservice.business.abstracts.UserService;
import com.microservice.userTerminalManagementservice.domain.user.User;
import com.microservice.userTerminalManagementservice.dto.UserDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Rest API for managing users.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Returns a list of all users.
     * 
     * @return a ResponseEntity containing a list of all users
     */
    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<User> getAllUsers() {
    	return this.userService.getAllUsers();
    }

    /**
     * Returns a list of all users as UserDTOs.
     * 
     * @return a ResponseEntity containing a list of all users as UserDTOs
     */
    @GetMapping("/getAllUserDtos")
    @PreAuthorize("hasAnyAuthority('admin:read', 'teamlead:read')")
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
    @PreAuthorize("hasAnyAuthority('admin:read', 'teamlead:read')")
    public ResponseEntity<?> getUserDtoById(@PathVariable Integer id) {

        return new ResponseEntity<>(userService.getUserDtoById(id), HttpStatus.OK);



    }

    /**
     * Adds a new user
     * 
     * @param user the user to add
     * @return a ResponseEntity containing a success message.
     */
    @PostMapping("/saveUser")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> saveUser(@Valid @RequestBody User user) {
        userService.saveUser(user);
        String message = String.format("User '%s' saved successfully.", user.getEmail());
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    /**
     * Updates an existing user with the given ID
     * 
     * @param id the ID of the user to update
     * @param user the updated user information
     * @return a ResponseEntity containing a success message.
     */
    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('admin:update') or @userSecurity.checkUserId(authentication,#id)")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        String message = String.format("User with id '%s' updated successfully.", updatedUser.getId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    
    /**
     * Changes the password for the user with the given ID.
     *
     * @param id the ID of the user whose password will be changed.
     * @param password the new password to be set for the user.
     * @return a ResponseEntity containing a success message.
     */
    @PutMapping("/changeUserPassword/{id}")
    @PreAuthorize("hasAuthority('admin:update') or @userSecurity.checkUserId(authentication,#id)")
    public ResponseEntity<String> changeUserPassword(@PathVariable Integer id, @RequestBody String password) {
        userService.changeUserPassword(id, password);
        String message = String.format("Password changed successfully for user with id '%s'.", id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }



    /**
     * Soft delete a user by ID.
     *
     * @param id the ID of the user to delete
     * @return a ResponseEntity containing a success message
     */
    @PutMapping("/softDeleteUser/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> softDeleteUser(@PathVariable Integer id) {

    	userService.softDeleteUser(id);
        String message = String.format("Soft delet completed successfully for user with id '%s'.", id);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
}

