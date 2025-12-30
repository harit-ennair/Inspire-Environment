package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.enums.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByType(ActivityType type);

    List<Activity> findByManagedById(Long staffId);

    List<Activity> findByManagedBy(String managedBy);

    @Query("SELECT DISTINCT a FROM Activity a JOIN a.attendances att WHERE att.student.id = :studentId")
    List<Activity> findByStudents_Id(Long studentId);

    @Query("SELECT a FROM Activity a WHERE a.startDate >= :startDate AND a.endDate <= :endDate")
    List<Activity> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT a FROM Activity a WHERE a.startDate <= :now AND a.endDate >= :now")
    List<Activity> findActiveActivities(LocalDateTime now);

    List<Activity> findByTitleContainingIgnoreCase(String title);
}
