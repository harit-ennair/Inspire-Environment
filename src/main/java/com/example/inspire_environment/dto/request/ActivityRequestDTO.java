package com.example.inspire_environment.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityRequestDTO {
    private String title;
    private String type; // SESSION, VISIT, WORKSHOP
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String managedBy;
}
