package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.StaffRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.dto.response.StaffResponseDTO;
import com.example.inspire_environment.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping
    public ResponseEntity<StaffRequestDTO> createStaff(@RequestBody StaffResponseDTO staffDto) {
        StaffRequestDTO response = staffService.createStaff(staffDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffRequestDTO> updateStaff(
            @PathVariable Long id,
            @RequestBody StaffRequestDTO staffDto) {
        StaffRequestDTO response = staffService.updateStaff(id, staffDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponseDTO> getStaffById(@PathVariable Long id) {
        StaffResponseDTO response = staffService.getStaffById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StaffResponseDTO>> getAllStaffs() {
        List<StaffResponseDTO> staffs = staffService.getAllStaffs();
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<StaffResponseDTO>> getAllStaffsByDepartment(@PathVariable Long departmentId) {
        List<StaffResponseDTO> staffs = staffService.getAllStaffsByDepartment(departmentId);
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/{staffId}/activities")
    public ResponseEntity<List<ActivityResponseDTO>> getManagedActivities(@PathVariable Long staffId) {
        List<ActivityResponseDTO> activities = staffService.getManagedActivities(staffId);
        return ResponseEntity.ok(activities);
    }
}

