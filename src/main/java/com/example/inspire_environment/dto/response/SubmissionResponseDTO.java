package com.example.inspire_environment.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubmissionResponseDTO {
    private Long id;
    private LocalDateTime submittedAt;
    private StudentResponseDTO student;
    private TaskResponseDTO task;
    private List<FileResponseDTO> files;
}
