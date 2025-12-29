package com.example.inspire_environment.dto.request;

import com.example.inspire_environment.enums.AttendanceStatus;
import lombok.Data;

@Data
public class AttendanceRequestDTO {
    private Long studentId;
    private Long activityId;
    private AttendanceStatus status; // PRESENT, ABSENT, LATE
}
