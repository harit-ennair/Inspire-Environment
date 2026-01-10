package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.StudentRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.AttendanceResponseDTO;
import com.example.inspire_environment.dto.response.StudentResponseDTO;
import com.example.inspire_environment.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    @Override
    public StudentResponseDTO getStudentById(Long id) {
        return null;
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        return List.of();
    }

    @Override
    public List<StudentResponseDTO> getAllStudentsByDepartment(Long departmentId) {
        return List.of();
    }

    @Override
    public StudentRequestDTO createStudent(StudentRequestDTO studentDto) {
        return null;
    }

    @Override
    public StudentRequestDTO updateStudent(Long id, StudentRequestDTO studentDto) {
        return null;
    }

    @Override
    public void deleteStudent(Long id) {

    }

    @Override
    public List<ActivityResponseDTO> getMyActivities(Long studentId) {
        return List.of();
    }

    @Override
    public List<AttendanceResponseDTO> getMyAttendances(Long studentId) {
        return List.of();
    }
}
