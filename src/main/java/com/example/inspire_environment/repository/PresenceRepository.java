package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Presence;
import com.example.inspire_environment.enums.PresenceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PresenceRepository extends JpaRepository<Presence, Long> {

    @Query("SELECT p FROM Presence p JOIN Student s ON p MEMBER OF s.presences WHERE s.id = :studentId")
    List<Presence> findByStudentId(Long studentId);

    List<Presence> findByStatus(PresenceStatus status);

    @Query("SELECT p FROM Presence p WHERE p.checkInTime BETWEEN :start AND :end")
    List<Presence> findByCheckInTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT p FROM Presence p WHERE p.checkOutTime BETWEEN :start AND :end")
    List<Presence> findByCheckOutTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT p FROM Presence p JOIN Student s ON p MEMBER OF s.presences WHERE s.id = :studentId AND p.status = :status")
    List<Presence> findByStudentIdAndStatus(Long studentId, PresenceStatus status);

    @Query("SELECT p FROM Presence p JOIN Student s ON p MEMBER OF s.presences WHERE s.id = :studentId AND p.checkInTime BETWEEN :start AND :end")
    List<Presence> findByStudentIdAndCheckInTimeBetween(Long studentId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT p FROM Presence p WHERE p.checkInTime IS NOT NULL AND p.checkOutTime IS NULL")
    List<Presence> findActivePresences();
}

