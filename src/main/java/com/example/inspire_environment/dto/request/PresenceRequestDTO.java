package com.example.inspire_environment.dto.request;

import com.example.inspire_environment.enums.PresenceStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PresenceRequestDTO {
    private Long studentId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private PresenceStatus status; // PRESENT, ABSENT, LEFT
}

