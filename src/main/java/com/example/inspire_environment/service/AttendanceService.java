package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.response.AttendanceResponseDTO;

import java.util.List;

public interface AttendanceService {

    AttendanceResponseDTO checkIn(Long activityId, Long studentId);

    AttendanceResponseDTO updateStatus(Long attendanceId, String status);

    List<AttendanceResponseDTO> getAttendancesByActivity(Long activityId);

    List<AttendanceResponseDTO> getAttendancesByStudent(Long studentId);
}
