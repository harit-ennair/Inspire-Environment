package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.response.AttendanceResponseDTO;
import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.entity.Attendance;
import com.example.inspire_environment.entity.Student;
import com.example.inspire_environment.enums.PresenceStatus;
import com.example.inspire_environment.mapper.AttendanceMapper;
import com.example.inspire_environment.repository.ActivityRepository;
import com.example.inspire_environment.repository.AttendanceRepository;
import com.example.inspire_environment.repository.StudentRepository;
import com.example.inspire_environment.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final ActivityRepository activityRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    @Transactional
    public AttendanceResponseDTO checkIn(Long activityId, Long studentId) {
        // Validate student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        // Validate activity exists
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + activityId));

        // Check if student already checked in for this activity
        if (attendanceRepository.findByStudentIdAndActivityId(studentId, activityId).isPresent()) {
            throw new RuntimeException("Student already checked in for this activity");
        }

        LocalDateTime activityStartTimePlus15Minites = activity.getStartDate().plusMinutes(15);

        // Create new attendance record
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setActivity(activity);
        attendance.setCheckInTime(LocalDateTime.now());

        if (attendance.getCheckInTime().isAfter(activityStartTimePlus15Minites)) {
            attendance.setStatus(PresenceStatus.LATE);
        } else {
            attendance.setStatus(PresenceStatus.PRESENT);
        }
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toResponseDTO(savedAttendance);
    }

    @Override
    public AttendanceResponseDTO updateStatus(Long attendanceId, String status) {
        // Validate attendance exists
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + attendanceId));

        // Update status
        try {
            PresenceStatus presenceStatus = PresenceStatus.valueOf(status.toUpperCase());
            attendance.setStatus(presenceStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + status);
        }

        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toResponseDTO(updatedAttendance);
    }

    @Override
    public List<AttendanceResponseDTO> getAttendancesByActivity(Long activityId) {
        // Validate activity exists
        if (!activityRepository.existsById(activityId)) {
            throw new RuntimeException("Activity not found with id: " + activityId);
        }

        List<Attendance> attendances = attendanceRepository.findByActivityId(activityId);
        return attendances.stream()
                .map(attendanceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponseDTO> getAttendancesByStudent(Long studentId) {
        // Validate student exists
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }

        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        return attendances.stream()
                .map(attendanceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
