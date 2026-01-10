package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.FileRequestDTO;
import com.example.inspire_environment.dto.response.SubmissionResponseDTO;
import com.example.inspire_environment.service.SubmissionService;

import java.util.List;

public class SubmissionServiceImpl implements SubmissionService {
    @Override
    public SubmissionResponseDTO submitTask(Long taskId, Long studentId, FileRequestDTO fileDto) {
        return null;
    }

    @Override
    public void assignStudentToTask(Long taskId, Long studentId) {

    }

    @Override
    public void assignStudentsByDepartmentToTask(Long taskId, Long departmentId) {

    }

    @Override
    public List<SubmissionResponseDTO> getSubmissionsByTask(Long taskId) {
        return List.of();
    }

    @Override
    public List<SubmissionResponseDTO> getMySubmissions(Long studentId) {
        return List.of();
    }

    @Override
    public List<SubmissionResponseDTO> getSubmissionsByStudent(Long studentId) {
        return List.of();
    }

    @Override
    public List<SubmissionResponseDTO> getAllSubmissions() {
        return List.of();
    }
}
