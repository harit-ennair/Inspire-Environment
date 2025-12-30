package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.StaffResponseDTO;

import java.util.List;

public interface StaffService {

    StaffResponseDTO getStaffById(Long id);

    List<ActivityResponseDTO> getManagedActivities(Long staffId);

//    DashboardStaffDTO getStaffDashboard(Long staffId);
}
