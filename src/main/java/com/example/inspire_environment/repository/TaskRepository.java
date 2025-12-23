package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByActivityId(Long activityId);
}
