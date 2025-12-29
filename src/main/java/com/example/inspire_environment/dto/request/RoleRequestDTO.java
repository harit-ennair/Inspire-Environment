package com.example.inspire_environment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class RoleRequestDTO {

    @NotBlank(message = "Role name must not be blank")
    @UniqueElements(message = "Role name must be unique")
    private String name;

    private String description;
}
