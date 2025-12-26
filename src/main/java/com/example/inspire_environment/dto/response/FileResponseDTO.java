package com.example.inspire_environment.dto.response;

import lombok.Data;

@Data
public class FileResponseDTO {
    private Long id;
    private String fileUrl;
    private SubmissionResponseDTO submission;
}
