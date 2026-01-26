package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.FileRequestDTO;
import com.example.inspire_environment.dto.response.SubmissionResponseDTO;
import com.example.inspire_environment.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/submit")
    public ResponseEntity<SubmissionResponseDTO> submitTask(
            @RequestParam Long taskId,
            @RequestParam Long studentId,
            @RequestBody FileRequestDTO fileDto) {
        SubmissionResponseDTO response = submissionService.submitTask(taskId, studentId, fileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignStudentToTask(
            @RequestParam Long taskId,
            @RequestParam Long studentId) {
        submissionService.assignStudentToTask(taskId, studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign-department")
    public ResponseEntity<Void> assignStudentsByDepartmentToTask(
            @RequestParam Long taskId,
            @RequestParam Long departmentId) {
        submissionService.assignStudentsByDepartmentToTask(taskId, departmentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<SubmissionResponseDTO>> getSubmissionsByTask(@PathVariable Long taskId) {
        List<SubmissionResponseDTO> submissions = submissionService.getSubmissionsByTask(taskId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/my-submissions/{studentId}")
    public ResponseEntity<List<SubmissionResponseDTO>> getMySubmissions(@PathVariable Long studentId) {
        List<SubmissionResponseDTO> submissions = submissionService.getMySubmissions(studentId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<SubmissionResponseDTO>> getSubmissionsByStudent(@PathVariable Long studentId) {
        List<SubmissionResponseDTO> submissions = submissionService.getSubmissionsByStudent(studentId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping
    public ResponseEntity<List<SubmissionResponseDTO>> getAllSubmissions() {
        List<SubmissionResponseDTO> submissions = submissionService.getAllSubmissions();
        return ResponseEntity.ok(submissions);
    }
}

