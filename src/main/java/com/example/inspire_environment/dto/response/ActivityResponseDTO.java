package com.example.inspire_environment.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ActivityResponseDTO {
    private Long id;
    private String title;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String managedBy;
    private List<TaskResponseDTO> tasks;
}
