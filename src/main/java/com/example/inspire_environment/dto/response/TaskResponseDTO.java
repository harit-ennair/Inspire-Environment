package com.example.inspire_environment.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private ActivitySummaryDTO activity;
    private List<SubmissionResponseDTO> submissions;
}
