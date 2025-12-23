package com.example.inspire_environment.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionResponseDTO {
    private Long id;
    private String fileUrl;
    private LocalDateTime submittedAt;
    private StudentResponseDTO student;
    private TaskResponseDTO task;
}
