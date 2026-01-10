package com.example.inspire_environment.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PresenceResponseDTO {
    private Long id;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String status;
    private StudentResponseDTO student;
}

