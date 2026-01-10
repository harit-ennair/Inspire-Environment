package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.StaffRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.StaffResponseDTO;

import java.util.List;

public interface StaffService {

    StaffRequestDTO createStaff(StaffResponseDTO staffDto);

    StaffRequestDTO updateStaff(Long id, StaffRequestDTO staffDto);

    void deleteStaff(Long id);

    StaffResponseDTO getStaffById(Long id);

    List<ActivityResponseDTO> getManagedActivities(Long staffId);

    List<StaffResponseDTO> getAllStaffs();

    List<StaffResponseDTO> getAllStaffsByDepartment(Long departmentId);

//    DashboardStaffDTO getStaffDashboard(Long staffId);
}
