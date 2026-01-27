package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.DepartmentRequestDTO;
import com.example.inspire_environment.dto.response.DepartmentResponseDTO;
import com.example.inspire_environment.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentByName(@PathVariable String name) {
        return ResponseEntity.ok(departmentService.getDepartmentByName(name));
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody DepartmentRequestDTO departmentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(departmentRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
