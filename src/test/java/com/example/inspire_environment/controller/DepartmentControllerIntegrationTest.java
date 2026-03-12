package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.DepartmentRequestDTO;
import com.example.inspire_environment.dto.response.DepartmentResponseDTO;
import com.example.inspire_environment.entity.Department;
import com.example.inspire_environment.repository.DepartmentRepository;
import com.example.inspire_environment.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DepartmentControllerIntegrationTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void shouldCreateDepartment() {
        // Given
        DepartmentRequestDTO requestDTO = new DepartmentRequestDTO();
        requestDTO.setName("Computer Science");
        requestDTO.setDescription("Department of Computer Science");

        // When
        DepartmentResponseDTO response = departmentService.createDepartment(requestDTO);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("Computer Science");
        assertThat(response.getDescription()).isEqualTo("Department of Computer Science");
    }

    @Test
    void shouldGetAllDepartments() {
        // Given
        DepartmentRequestDTO dept1 = new DepartmentRequestDTO();
        dept1.setName("Mathematics Test");
        dept1.setDescription("Department of Mathematics");

        DepartmentRequestDTO dept2 = new DepartmentRequestDTO();
        dept2.setName("Physics Test");
        dept2.setDescription("Department of Physics");

        departmentService.createDepartment(dept1);
        departmentService.createDepartment(dept2);

        // When
        List<DepartmentResponseDTO> departments = departmentService.getAllDepartments();

        // Then
        assertThat(departments).isNotNull();
        assertThat(departments.size()).isGreaterThanOrEqualTo(2);
        assertThat(departments).anyMatch(d -> d.getName().equals("Mathematics Test"));
        assertThat(departments).anyMatch(d -> d.getName().equals("Physics Test"));
    }

    @Test
    void shouldGetDepartmentById() {
        // Given
        DepartmentRequestDTO requestDTO = new DepartmentRequestDTO();
        requestDTO.setName("Biology");
        requestDTO.setDescription("Department of Biology");

        DepartmentResponseDTO createdDept = departmentService.createDepartment(requestDTO);

        // When
        DepartmentResponseDTO response = departmentService.getDepartmentById(createdDept.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(createdDept.getId());
        assertThat(response.getName()).isEqualTo("Biology");
        assertThat(response.getDescription()).isEqualTo("Department of Biology");
    }

    @Test
    void shouldUpdateDepartment() {
        // Given
        DepartmentRequestDTO createDTO = new DepartmentRequestDTO();
        createDTO.setName("Chemistry");
        createDTO.setDescription("Old description");

        DepartmentResponseDTO createdDept = departmentService.createDepartment(createDTO);

        DepartmentRequestDTO updateDTO = new DepartmentRequestDTO();
        updateDTO.setName("Chemistry Updated");
        updateDTO.setDescription("New description");

        // When
        DepartmentResponseDTO response = departmentService.updateDepartment(createdDept.getId(), updateDTO);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(createdDept.getId());
        assertThat(response.getName()).isEqualTo("Chemistry Updated");
        assertThat(response.getDescription()).isEqualTo("New description");
    }

    @Test
    void shouldDeleteDepartment() {
        // Given
        DepartmentRequestDTO requestDTO = new DepartmentRequestDTO();
        requestDTO.setName("History");
        requestDTO.setDescription("Department of History");

        DepartmentResponseDTO createdDept = departmentService.createDepartment(requestDTO);

        // When
        departmentService.deleteDepartment(createdDept.getId());

        // Then
        Optional<Department> deletedDept = departmentRepository.findById(createdDept.getId());
        assertThat(deletedDept).isEmpty();
    }
}
