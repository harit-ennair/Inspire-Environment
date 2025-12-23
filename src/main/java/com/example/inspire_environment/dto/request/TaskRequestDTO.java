package com.example.inspire_environment.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDTO {
    private String title;
    private String description;
    private LocalDateTime deadline;
    private Long activityId;
}

