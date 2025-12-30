package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.response.AttendanceResponseDTO;

import java.util.List;

public interface AttendanceService {

    AttendanceResponseDTO checkIn(Long activityId, Long studentId);

    List<AttendanceResponseDTO> getAttendancesByActivity(Long activityId);

    List<AttendanceResponseDTO> getAttendancesByStudent(Long studentId);
}
