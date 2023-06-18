package com.microservice.userTerminalManagementservice.business.abstracts;

import java.util.List;

import com.microservice.userTerminalManagementservice.domain.users.User;
import com.microservice.userTerminalManagementservice.dto.UserDTO;

public interface UserService {

    List<User> getAllUsers();
    
    List<UserDTO> getAllUserDtos();
    
    UserDTO getUserDtoById(Integer id);
    
    User saveUser(User user);
    
    User updateUser(Integer id, User user);
    
    User changeUserPassword(Integer id, String password);
    
    void softDeleteUser(Integer id);
}
