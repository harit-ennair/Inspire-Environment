package com.example.inspire_environment.dto.request;

import lombok.Data;

@Data
public class StudentRequestDTO {
    private String studentCode;
    private UserRequestDTO user;
}
