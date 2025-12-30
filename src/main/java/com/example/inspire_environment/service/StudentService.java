package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.AttendanceResponseDTO;
import com.example.inspire_environment.dto.response.StudentResponseDTO;

import java.util.List;

public interface StudentService {

    StudentResponseDTO getStudentById(Long id);

    List<ActivityResponseDTO> getMyActivities(Long studentId);

    List<AttendanceResponseDTO> getMyAttendances(Long studentId);

//    DashboardStudentDTO getMyDashboard(Long studentId);
}
