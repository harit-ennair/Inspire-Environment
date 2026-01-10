package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.response.AttendanceResponseDTO;
import com.example.inspire_environment.service.AttendanceService;

import java.util.List;

public class AttendanceServiceImpl implements AttendanceService {
    @Override
    public AttendanceResponseDTO checkIn(Long activityId, Long studentId) {
        return null;
    }

    @Override
    public List<AttendanceResponseDTO> getAttendancesByActivity(Long activityId) {
        return List.of();
    }

    @Override
    public List<AttendanceResponseDTO> getAttendancesByStudent(Long studentId) {
        return List.of();
    }
}
