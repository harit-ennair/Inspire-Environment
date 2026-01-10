package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.DepartmentRequestDTO;
import com.example.inspire_environment.dto.response.DepartmentResponseDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto);

    DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO dto);

    List<DepartmentResponseDTO> getAllDepartments();

    DepartmentResponseDTO getDepartmentById(Long id);

    DepartmentResponseDTO getDepartmentByName(String name);

    void deleteDepartment(Long id);
}
