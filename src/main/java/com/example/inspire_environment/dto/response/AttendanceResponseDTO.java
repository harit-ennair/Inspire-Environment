package com.example.inspire_environment.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceResponseDTO {
    private Long id;
    private LocalDateTime checkInTime;
    private String status;
    private StudentResponseDTO student;
    private ActivityResponseDTO activity;
}
