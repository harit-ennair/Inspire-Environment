package com.example.inspire_environment.dto.request;

import com.example.inspire_environment.entity.Staff;
import com.example.inspire_environment.enums.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityRequestDTO {
    private String title;
    private ActivityType type; // SESSION, VISIT, WORKSHOP
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long managedBy;
}
