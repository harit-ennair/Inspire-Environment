package com.example.inspire_environment.dto.request;

import lombok.Data;

@Data
public class AttendanceRequestDTO {
    private Long studentId;
    private Long activityId;
    private String status; // PRESENT, ABSENT, LATE
}
