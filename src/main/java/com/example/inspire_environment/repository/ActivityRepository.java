package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {


    List<Activity> findByManagedById(Long staffId);


    @Query("SELECT DISTINCT a FROM Activity a JOIN a.attendances att WHERE att.student.id = :studentId")
    List<Activity> findByStudents_Id(Long studentId);

    @Query("SELECT a FROM Activity a WHERE " +
           "LOWER(a.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Activity> searchActivities(@Param("search") String search, Pageable pageable);


}
