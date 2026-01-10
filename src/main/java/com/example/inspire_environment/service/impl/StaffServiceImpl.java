package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.StaffRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.StaffResponseDTO;
import com.example.inspire_environment.service.StaffService;

import java.util.List;

public class StaffServiceImpl implements StaffService {
    @Override
    public StaffRequestDTO createStaff(StaffResponseDTO staffDto) {
        return null;
    }

    @Override
    public StaffRequestDTO updateStaff(Long id, StaffRequestDTO staffDto) {
        return null;
    }

    @Override
    public void deleteStaff(Long id) {

    }

    @Override
    public StaffResponseDTO getStaffById(Long id) {
        return null;
    }

    @Override
    public List<ActivityResponseDTO> getManagedActivities(Long staffId) {
        return List.of();
    }

    @Override
    public List<StaffResponseDTO> getAllStaffs() {
        return List.of();
    }

    @Override
    public List<StaffResponseDTO> getAllStaffsByDepartment(Long departmentId) {
        return List.of();
    }
}
