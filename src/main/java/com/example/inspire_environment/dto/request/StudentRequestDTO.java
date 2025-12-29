package com.example.inspire_environment.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class StudentRequestDTO {

    @UniqueElements(message = "Student code must be unique")
    private String studentCode;
    private UserRequestDTO user;
}
