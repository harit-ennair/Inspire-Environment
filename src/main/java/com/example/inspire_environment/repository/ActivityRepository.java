package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.enums.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByType(ActivityType type);
}
