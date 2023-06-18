package com.microservice.carDefectservice.dto;

import java.util.List;

import com.microservice.carDefectservice.enums.Role;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private String firstname;
    private String lastname;
    private String email;
    private List<Role> roles;



}