package com.example.inspire_environment.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;
    private String departmentName;
}
