package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.StaffRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.StaffResponseDTO;
import com.example.inspire_environment.entity.Department;
import com.example.inspire_environment.entity.Role;
import com.example.inspire_environment.entity.Staff;
import com.example.inspire_environment.mapper.ActivityMapper;
import com.example.inspire_environment.mapper.StaffMapper;
import com.example.inspire_environment.repository.ActivityRepository;
import com.example.inspire_environment.repository.DepartmentRepository;
import com.example.inspire_environment.repository.RoleRepository;
import com.example.inspire_environment.repository.StaffRepository;
import com.example.inspire_environment.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final ActivityRepository activityRepository;
    private final StaffMapper staffMapper;
    private final ActivityMapper activityMapper;

    @Override
    public StaffRequestDTO createStaff(StaffResponseDTO staffDto) {
        // Check if email already exists
        if (staffDto.getUser() != null && staffDto.getUser().getEmail() != null) {
            staffRepository.findByEmail(staffDto.getUser().getEmail())
                    .ifPresent(existingStaff -> {
                        throw new IllegalArgumentException("Staff with email '" + staffDto.getUser().getEmail() + "' already exists");
                    });
        }

        Staff staff = new Staff();
        staff.setPosition(staffDto.getPosition());

        if (staffDto.getUser() != null) {
            staff.setFirstName(staffDto.getUser().getFirstName());
            staff.setLastName(staffDto.getUser().getLastName());
            staff.setEmail(staffDto.getUser().getEmail());

            // Set role (default to STAFF if not specified)
            if (staffDto.getUser().getRoleName() != null) {
                Role role = roleRepository.findByName(staffDto.getUser().getRoleName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + staffDto.getUser().getRoleName()));
                staff.setRole(role);
            } else {
                Role defaultRole = roleRepository.findByName("STAFF")
                        .orElseThrow(() -> new RuntimeException("Default STAFF role not found"));
                staff.setRole(defaultRole);
            }

            // Set department if specified
            if (staffDto.getUser().getDepartmentName() != null) {
                Department department = departmentRepository.findByName(staffDto.getUser().getDepartmentName())
                        .orElseThrow(() -> new RuntimeException("Department not found: " + staffDto.getUser().getDepartmentName()));
                staff.setDepartment(department);
            }
        }

        Staff savedStaff = staffRepository.save(staff);

        // Convert back to RequestDTO for return
        StaffRequestDTO requestDTO = new StaffRequestDTO();
        requestDTO.setPosition(savedStaff.getPosition());
        return requestDTO;
    }

    @Override
    public StaffRequestDTO updateStaff(Long id, StaffRequestDTO staffDto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));

        // Check if email already exists (excluding current staff)
        if (staffDto.getUser() != null && staffDto.getUser().getEmail() != null) {
            staffRepository.findByEmail(staffDto.getUser().getEmail())
                    .ifPresent(existingStaff -> {
                        if (!existingStaff.getId().equals(id)) {
                            throw new IllegalArgumentException("Staff with email '" + staffDto.getUser().getEmail() + "' already exists");
                        }
                    });
        }

        // Update staff fields
        staffMapper.updateEntityFromDTO(staffDto, staff);

        // Update role if specified
        if (staffDto.getUser() != null && staffDto.getUser().getRoleId() != null) {
            Role role = roleRepository.findById(staffDto.getUser().getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + staffDto.getUser().getRoleId()));
            staff.setRole(role);
        }

        // Update department if specified
        if (staffDto.getUser() != null && staffDto.getUser().getDepartmentId() != null) {
            Department department = departmentRepository.findById(staffDto.getUser().getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + staffDto.getUser().getDepartmentId()));
            staff.setDepartment(department);
        }

        staffRepository.save(staff);
        return staffDto;
    }

    @Override
    public void deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new RuntimeException("Staff not found with id: " + id);
        }
        staffRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponseDTO getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        return staffMapper.toResponseDTO(staff);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityResponseDTO> getManagedActivities(Long staffId) {
        // Verify staff exists
        if (!staffRepository.existsById(staffId)) {
            throw new RuntimeException("Staff not found with id: " + staffId);
        }

        return activityRepository.findByManagedById(staffId)
                .stream()
                .map(activityMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponseDTO> getAllStaffs() {
        return staffRepository.findAll()
                .stream()
                .map(staffMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponseDTO> getAllStaffsByDepartment(Long departmentId) {
        // Verify department exists
        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found with id: " + departmentId);
        }

        return staffRepository.findByDepartmentId(departmentId)
                .stream()
                .map(staffMapper::toResponseDTO)
                .toList();
    }
}
