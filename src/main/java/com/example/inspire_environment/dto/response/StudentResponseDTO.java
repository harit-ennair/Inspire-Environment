package com.example.inspire_environment.dto.response;

import lombok.Data;

@Data
public class StudentResponseDTO {
    private Long id;
    private String studentCode;
    private UserResponseDTO user;
}
