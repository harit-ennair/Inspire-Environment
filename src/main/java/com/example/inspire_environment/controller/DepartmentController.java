package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.DepartmentRequestDTO;
import com.example.inspire_environment.dto.response.DepartmentResponseDTO;
import com.example.inspire_environment.entity.Department;
import com.example.inspire_environment.mapper.DepartmentMapper;
import com.example.inspire_environment.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDTO> departmentDTOs = departments.stream()
                .map(departmentMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(departmentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            DepartmentResponseDTO departmentDTO = departmentMapper.toResponseDTO(department.get());
            return ResponseEntity.ok(departmentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody DepartmentRequestDTO departmentRequestDTO) {
        Department department = departmentMapper.toEntity(departmentRequestDTO);
        Department savedDepartment = departmentRepository.save(department);
        DepartmentResponseDTO departmentDTO = departmentMapper.toResponseDTO(savedDepartment);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            departmentMapper.updateEntityFromDTO(departmentRequestDTO, department);
            Department updatedDepartment = departmentRepository.save(department);
            DepartmentResponseDTO departmentDTO = departmentMapper.toResponseDTO(updatedDepartment);
            return ResponseEntity.ok(departmentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
