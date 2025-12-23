package com.example.inspire_environment.dto.request;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long roleId;
    private Long departmentId;
}
