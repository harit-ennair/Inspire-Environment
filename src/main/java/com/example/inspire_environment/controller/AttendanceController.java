package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.response.AttendanceResponseDTO;
import com.example.inspire_environment.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponseDTO> checkIn(
            @RequestParam Long activityId,
            @RequestParam Long studentId) {
        AttendanceResponseDTO response = attendanceService.checkIn(activityId, studentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{attendanceId}/status")
    public ResponseEntity<AttendanceResponseDTO> updateStatus(
            @PathVariable Long attendanceId,
            @RequestParam String status) {
        AttendanceResponseDTO response = attendanceService.updateStatus(attendanceId, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendancesByActivity(
            @PathVariable Long activityId) {
        List<AttendanceResponseDTO> attendances = attendanceService.getAttendancesByActivity(activityId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendancesByStudent(
            @PathVariable Long studentId) {
        List<AttendanceResponseDTO> attendances = attendanceService.getAttendancesByStudent(studentId);
        return ResponseEntity.ok(attendances);
    }
}

