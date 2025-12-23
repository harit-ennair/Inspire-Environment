package com.example.inspire_environment.dto.response;

import lombok.Data;

@Data
public class StaffResponseDTO {
    private Long id;
    private String position;
    private UserResponseDTO user;
}
