package com.example.inspire_environment.dto.request;

import com.example.inspire_environment.enums.PresenceStatus;
import lombok.Data;

@Data
public class AttendanceRequestDTO {
    private Long studentId;
    private Long activityId;
    private PresenceStatus status; // PRESENT, ABSENT, LATE
}
