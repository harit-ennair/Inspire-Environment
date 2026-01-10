package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.StudentRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.AttendanceResponseDTO;
import com.example.inspire_environment.dto.response.StudentResponseDTO;

import java.util.List;

public interface StudentService {

    StudentResponseDTO getStudentById(Long id);

    List<StudentResponseDTO> getAllStudents();

    List<StudentResponseDTO> getAllStudentsByDepartment(Long departmentId);


    StudentRequestDTO createStudent(StudentRequestDTO studentDto);

    StudentRequestDTO updateStudent(Long id, StudentRequestDTO studentDto);

    void deleteStudent(Long id);

    List<ActivityResponseDTO> getMyActivities(Long studentId);

    List<AttendanceResponseDTO> getMyAttendances(Long studentId);

//    DashboardStudentDTO getMyDashboard(Long studentId);
}
