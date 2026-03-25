package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.StaffRequestDTO;
import com.example.inspire_environment.dto.response.StaffResponseDTO;

import java.util.List;

public interface StaffService {

    StaffRequestDTO createStaff(StaffRequestDTO staffDto);

    StaffRequestDTO updateStaff(Long id, StaffRequestDTO staffDto);

    void deleteStaff(Long id);

    StaffResponseDTO getStaffById(Long id);

    List<StaffResponseDTO> getAllStaffs();

}
