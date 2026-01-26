package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.StudentRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.AttendanceResponseDTO;
import com.example.inspire_environment.dto.response.StudentResponseDTO;
import com.example.inspire_environment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO response = studentService.getStudentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudentsByDepartment(@PathVariable Long departmentId) {
        List<StudentResponseDTO> students = studentService.getAllStudentsByDepartment(departmentId);
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<StudentRequestDTO> createStudent(@RequestBody StudentRequestDTO studentDto) {
        StudentRequestDTO response = studentService.createStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentRequestDTO> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentRequestDTO studentDto) {
        StudentRequestDTO response = studentService.updateStudent(id, studentDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}/activities")
    public ResponseEntity<List<ActivityResponseDTO>> getMyActivities(@PathVariable Long studentId) {
        List<ActivityResponseDTO> activities = studentService.getMyActivities(studentId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{studentId}/attendances")
    public ResponseEntity<List<AttendanceResponseDTO>> getMyAttendances(@PathVariable Long studentId) {
        List<AttendanceResponseDTO> attendances = studentService.getMyAttendances(studentId);
        return ResponseEntity.ok(attendances);
    }
}

