package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByActivityId(Long activityId);

    List<Task> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT t FROM Task t WHERE t.deadline < :now")
    List<Task> findOverdueTasks(LocalDateTime now);

    @Query("SELECT t FROM Task t WHERE t.deadline BETWEEN :start AND :end")
    List<Task> findTasksDueBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM Task t JOIN t.activity a WHERE a.managedBy.id = :staffId")
    List<Task> findTasksByStaffId(Long staffId);
}
