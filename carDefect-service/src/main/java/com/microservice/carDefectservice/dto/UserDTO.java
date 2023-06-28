package com.microservice.carDefectservice.dto;

import com.microservice.carDefectservice.domain.user.Role;
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