package com.microservice.userTerminalManagementservice.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.userTerminalManagementservice.business.abstracts.UserService;
import com.microservice.userTerminalManagementservice.domain.User;
import com.microservice.userTerminalManagementservice.dto.UserDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Rest API for managing users.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Returns a list of all users.
     * 
     * @return a ResponseEntity containing a list of all users
     */
    @GetMapping("/getAllUser")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<User> getAllUsers() {
    	return this.userService.getAllUsers();
    }

    /**
     * Returns a list of all users as UserDTOs.
     * 
     * @return a ResponseEntity containing a list of all users as UserDTOs
     */
    @GetMapping("/getAllUserDto")
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
	 * Returns a list of all users.
	 * 
	 * @return a List of all users
	 */
    @GetMapping("/getPageableUser")
    @PreAuthorize("hasAuthority('teamlead:read')")
    public ResponseEntity<Page<UserDTO>> getPageableUser(@PageableDefault(size = 20) Pageable pageable) {
        Page<UserDTO> defectDTOs = userService.getPageableUser(pageable);
        return ResponseEntity.ok(defectDTOs);
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
    public ResponseEntity<String> changeUserPassword(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        try {
            userService.changeUserPassword(id, request.get("password"));
            return ResponseEntity.ok("Password changed successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to change password.");
        }
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

