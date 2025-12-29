package com.example.inspire_environment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;
    private LocalDateTime deadline;
    private Long activityId;
}

