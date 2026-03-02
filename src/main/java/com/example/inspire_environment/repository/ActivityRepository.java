package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.enums.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {


    List<Activity> findByManagedById(Long staffId);


    @Query("SELECT DISTINCT a FROM Activity a JOIN a.attendances att WHERE att.student.id = :studentId")
    List<Activity> findByStudents_Id(Long studentId);

}
