package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Attendance;
import com.example.inspire_environment.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a JOIN Student s ON a MEMBER OF s.attendances WHERE s.id = :studentId")
    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByActivityId(Long activityId);

    List<Attendance> findByStatus(AttendanceStatus status);

    @Query("SELECT a FROM Attendance a JOIN Student s ON a MEMBER OF s.attendances WHERE s.id = :studentId AND a.activity.id = :activityId")
    Optional<Attendance> findByStudentIdAndActivityId(Long studentId, Long activityId);

    @Query("SELECT a FROM Attendance a WHERE a.checkInTime BETWEEN :start AND :end")
    List<Attendance> findByCheckInTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Attendance a JOIN Student s ON a MEMBER OF s.attendances WHERE s.id = :studentId AND a.status = :status")
    List<Attendance> findByStudentIdAndStatus(Long studentId, AttendanceStatus status);
}

