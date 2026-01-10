package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.FileRequestDTO;
import com.example.inspire_environment.dto.response.SubmissionResponseDTO;

import java.util.List;

public interface SubmissionService {

    SubmissionResponseDTO submitTask(
            Long taskId,
            Long studentId,
            FileRequestDTO fileDto
    );

    void assignStudentToTask(
            Long taskId,
            Long studentId
    );

    void assignStudentsByDepartmentToTask(
            Long taskId,
            Long departmentId
    );

    List<SubmissionResponseDTO> getSubmissionsByTask(Long taskId);

    List<SubmissionResponseDTO> getMySubmissions(Long studentId);

    List<SubmissionResponseDTO> getSubmissionsByStudent(Long studentId);

    List<SubmissionResponseDTO> getAllSubmissions();
}
