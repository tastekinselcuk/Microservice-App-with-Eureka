package com.microservice.userTerminalManagementservice.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microservice.userTerminalManagementservice.domain.User;
import com.microservice.userTerminalManagementservice.dto.UserDTO;

public interface UserService {

    List<User> getAllUsers();
    
    List<UserDTO> getAllUserDtos();
    
    UserDTO getUserDtoById(Integer id);
    
    Page<UserDTO> getPageableUser(Pageable pageable);
    
    User saveUser(User user);
    
    User updateUser(Integer id, User user);
    
    User changeUserPassword(Integer id, String password);
    
    void softDeleteUser(Integer id);
}
