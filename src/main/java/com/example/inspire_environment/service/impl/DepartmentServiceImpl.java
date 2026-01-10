package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.DepartmentRequestDTO;
import com.example.inspire_environment.dto.response.DepartmentResponseDTO;
import com.example.inspire_environment.entity.Department;
import com.example.inspire_environment.mapper.DepartmentMapper;
import com.example.inspire_environment.repository.DepartmentRepository;
import com.example.inspire_environment.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {
        // Check if department with the same name already exists
        departmentRepository.findByName(dto.getName())
                .ifPresent(existingDept -> {
                    throw new IllegalArgumentException("Department with name '" + dto.getName() + "' already exists");
                });

        Department department = departmentMapper.toEntity(dto);
        return departmentMapper.toResponseDTO(departmentRepository.save(department));
    }

    @Override
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        // Check if department with the same name already exists (excluding current department)
        departmentRepository.findByName(dto.getName())
                .ifPresent(existingDept -> {
                    if (!existingDept.getId().equals(id)) {
                        throw new IllegalArgumentException("Department with name '" + dto.getName() + "' already exists");
                    }
                });

        departmentMapper.updateEntityFromDTO(dto, department);
        return departmentMapper.toResponseDTO(departmentRepository.save(department));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponseDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        return departmentMapper.toResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO getDepartmentByName(String name) {
        Department department = departmentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Department not found with name: " + name));
        return departmentMapper.toResponseDTO(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
