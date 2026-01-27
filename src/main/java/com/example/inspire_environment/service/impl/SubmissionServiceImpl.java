package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.FileRequestDTO;
import com.example.inspire_environment.dto.response.SubmissionResponseDTO;
import com.example.inspire_environment.entity.File;
import com.example.inspire_environment.entity.Student;
import com.example.inspire_environment.entity.Submission;
import com.example.inspire_environment.entity.Task;
import com.example.inspire_environment.exception.ResourceNotFoundException;
import com.example.inspire_environment.mapper.SubmissionMapper;
import com.example.inspire_environment.repository.StudentRepository;
import com.example.inspire_environment.repository.SubmissionRepository;
import com.example.inspire_environment.repository.TaskRepository;
import com.example.inspire_environment.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final TaskRepository taskRepository;
    private final StudentRepository studentRepository;
    private final SubmissionMapper submissionMapper;

    @Override
    public SubmissionResponseDTO submitTask(Long taskId, Long studentId, FileRequestDTO fileDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        // Check if submission already exists
        Optional<Submission> existingSubmission = submissionRepository.findByStudentIdAndTaskId(studentId, taskId);

        Submission submission;
        if (existingSubmission.isPresent()) {
            submission = existingSubmission.get();
            // Add new file to existing submission
            if (fileDto != null && fileDto.getFileUrl() != null) {
                File file = new File();
                file.setFileUrl(fileDto.getFileUrl());
                file.setSubmission(submission);
                if (submission.getFiles() == null) {
                    submission.setFiles(new ArrayList<>());
                }
                submission.getFiles().add(file);
            }
        } else {
            // Create new submission
            submission = new Submission();
            submission.setTask(task);
            submission.setStudent(student);
            submission.setSubmittedAt(LocalDateTime.now());

            if (fileDto != null && fileDto.getFileUrl() != null) {
                File file = new File();
                file.setFileUrl(fileDto.getFileUrl());
                file.setSubmission(submission);
                List<File> files = new ArrayList<>();
                files.add(file);
                submission.setFiles(files);
            }
        }

        Submission savedSubmission = submissionRepository.save(submission);
        return submissionMapper.toResponseDTO(savedSubmission);
    }

    @Override
    public void assignStudentToTask(Long taskId, Long studentId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        // Check if submission already exists
        Optional<Submission> existingSubmission = submissionRepository.findByStudentIdAndTaskId(studentId, taskId);

        if (existingSubmission.isEmpty()) {
            // Create a new submission record (not submitted yet, just assigned)
            Submission submission = new Submission();
            submission.setTask(task);
            submission.setStudent(student);
            submission.setSubmittedAt(null); // Not yet submitted
            submissionRepository.save(submission);
        }
    }

    @Override
    public void assignStudentsByDepartmentToTask(Long taskId, Long departmentId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        // Get all students from the department
        List<Student> students = studentRepository.findByDepartmentId(departmentId);

        for (Student student : students) {
            // Check if submission already exists
            Optional<Submission> existingSubmission = submissionRepository.findByStudentIdAndTaskId(student.getId(), taskId);

            if (existingSubmission.isEmpty()) {
                // Create a new submission record (not submitted yet, just assigned)
                Submission submission = new Submission();
                submission.setTask(task);
                submission.setStudent(student);
                submission.setSubmittedAt(null); // Not yet submitted
                submissionRepository.save(submission);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getSubmissionsByTask(Long taskId) {
        return submissionRepository.findByTaskId(taskId)
                .stream()
                .map(submissionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getMySubmissions(Long studentId) {
        return submissionRepository.findByStudentId(studentId)
                .stream()
                .map(submissionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getSubmissionsByStudent(Long studentId) {
        return submissionRepository.findByStudentId(studentId)
                .stream()
                .map(submissionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getAllSubmissions() {
        return submissionRepository.findAll()
                .stream()
                .map(submissionMapper::toResponseDTO)
                .toList();
    }
}
