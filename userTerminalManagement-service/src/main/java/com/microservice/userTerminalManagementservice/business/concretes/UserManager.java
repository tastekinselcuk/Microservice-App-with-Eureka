package com.microservice.userTerminalManagementservice.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservice.userTerminalManagementservice.business.abstracts.UserService;
import com.microservice.userTerminalManagementservice.domain.user.User;
import com.microservice.userTerminalManagementservice.dto.UserDTO;
import com.microservice.userTerminalManagementservice.exception.AppException;
import com.microservice.userTerminalManagementservice.repository.UserRepository;
import com.microservice.userTerminalManagementservice.security.config.JwtService;

import lombok.RequiredArgsConstructor;

/**
 * This class implements the UserService interface and provides functionality for managing users.
 */
@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

	  private final PasswordEncoder passwordEncoder;
	  private final JwtService jwtService;
	  private final UserRepository userRepository;

    /**
     * Returns a list of all users in the system
     * 
     * @return List of User objects
     */
    public List<User> getAllUsers() {
        return userRepository.findByDeletedFalse();
    }
    
    /**
     * Returns a list of all user DTOs in the system
     * 
     * @return List of UserDTO objects
     */
    @Override
    public List<UserDTO> getAllUserDtos() {
        List<User> userList = userRepository.findByDeletedFalse();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            userDTOList.add(new UserDTO(user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole()));
        }
        return userDTOList;
    }

    /**
     * Returns a user DTO with the specified ID
     * 
     * @param id ID of the user to be returned
     * @return UserDTO object
     */
    @Override
    public UserDTO getUserDtoById(Integer id) {
        if (userRepository.findByIdAndDeletedFalse(id) == null) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "No Id Provided",
                    "Please provide id of the record you want to see.",
                    "No id provided for the record to be seen.");
        }
        Optional<User> optionalUser = userRepository.findByIdAndDeletedFalse(id);
        User existingUser = optionalUser.get();
        return new UserDTO(existingUser.getFirstname(), existingUser.getLastname(), existingUser.getEmail(), existingUser.getRole());
    }
    
    @Override
    public Page<UserDTO> getPageableUser(Pageable pageable) {
        return userRepository.findByDeletedFalse(pageable).map(this::convertToDto);
    }

    /**
     * Saves a new user to the system
     * 
     * @param user User object to be saved
     * @return User object
     */
    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmailAndDeletedFalse(user.getEmail())) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "Email Already Exist",
                    "Provided email already exist!"
            );
        }
        if (user.getPassword().length() < 8 || !user.getPassword().matches(".*[a-z].*") || !user.getPassword().matches(".*[A-Z].*")) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid Password",
                    "Password should contain at least one lowercase letter, one uppercase letter, and be at least 8 characters long!"
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        userRepository.save(user);
        jwtService.generateToken(user);
        return user;

    }

    /**
     * Updates an existing user in the system
     * 
     * @param id ID of the user to be updated
     * @param user User object with updated information
     * @return Updated User object
     */
    @Override
    public User updateUser(Integer id, User user) {
        if (userRepository.findByIdAndDeletedFalse(id) == null) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "No Id Provided",
                    "Please provide id of the record you want to update.",
                    "No id provided for the record to be updated.");
        }
        Optional<User> optionalUser = userRepository.findByIdAndDeletedFalse(id);
        User existingUser = optionalUser.get();        
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);

    }

    /**
     * Changes the password of a user.
     * 
     * @param id The ID of the user whose password will be changed
     * @param password The new password
     * @return The user with the updated password
    */
    @Override
	public User changeUserPassword(Integer id, String password) {
        if (userRepository.findByIdAndDeletedFalse(id) == null) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "No Id Provided",
                    "Please provide id of the record you want to change password.",
                    "No id provided for the record to be changed password.");
        }
	    if (password.length() < 8 || !password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid Password",
                    "Password should contain at least one lowercase letter, one uppercase letter, and be at least 8 characters long!"
            );
        }
        Optional<User> optionalUser = userRepository.findByIdAndDeletedFalse(id);
        User existingUser = optionalUser.get();
        existingUser.setPassword(passwordEncoder.encode(password));
	    userRepository.save(existingUser);
	    return existingUser;

	}

    /**
     * Soft deletes a user in the system by setting the "deleted" flag to true.
     * 
     * @param id The ID of the user to be soft deleted.
    */
	@Override
    public void softDeleteUser(Integer id) {
        if (userRepository.findByIdAndDeletedFalse(id) == null) {
            throw new AppException(
                    HttpStatus.BAD_REQUEST,
                    "No Id Provided",
                    "Please provide id of the record you want to delete.",
                    "No id provided for the record to be deleted.");
        }
        Optional<User> optionalUser = userRepository.findByIdAndDeletedFalse(id);
        User existingUser = optionalUser.get();
        existingUser.setDeleted(true);
        userRepository.save(existingUser);
    }
	
    private UserDTO convertToDto(User user) {
        return UserDTO.builder()
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
    }
	
}

