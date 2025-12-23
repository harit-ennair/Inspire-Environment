package com.example.inspire_environment.dto.request;

import lombok.Data;

@Data
public class SubmissionRequestDTO {
    private String fileUrl;
    private Long studentId;
    private Long taskId;
}
