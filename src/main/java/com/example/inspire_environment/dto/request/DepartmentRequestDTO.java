package com.example.inspire_environment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class DepartmentRequestDTO {

    @NotBlank(message = "Name is required")
    @UniqueElements(message = "Department name must be unique")
    private String name;

    private String description;
}
