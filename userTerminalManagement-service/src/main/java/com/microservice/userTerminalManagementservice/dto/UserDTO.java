package com.microservice.userTerminalManagementservice.dto;

import com.microservice.userTerminalManagementservice.domain.user.Role;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private String firstname;
    private String lastname;
    private String email;
    private Role role;

}