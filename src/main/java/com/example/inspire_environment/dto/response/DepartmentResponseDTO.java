package com.example.inspire_environment.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentResponseDTO {
    private Long id;
    private String name;
    private String description;
    private List<UserResponseDTO> users;
}
