package com.microservice.carDefectservice.business.abstracts;

import java.util.List;

import com.microservice.carDefectservice.domain.user.User;
import com.microservice.carDefectservice.dto.UserDTO;



public interface UserService {

    List<User> getAllUsers();
    
    List<UserDTO> getAllUserDtos();
    
    UserDTO getUserDtoById(Integer id);
    
    User saveUser(User user);
    
    User updateUser(Integer id, User user);
    
    User changeUserPassword(Integer id, String password);
    
    void softDeleteUser(Integer id);
}
