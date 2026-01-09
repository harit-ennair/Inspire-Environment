package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.ActivityRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.entity.Activity;

import java.util.List;

public interface ActivityService {

    ActivityResponseDTO createActivity(ActivityRequestDTO dto);

    ActivityResponseDTO updateActivity(Long id, ActivityRequestDTO dto);

    List<ActivityResponseDTO> getAllActivities(); // Admin / Staff

    List<ActivityResponseDTO> getActivitiesByStudent(Long studentId);

    List<ActivityResponseDTO> getActivitiesManagedBy(String managedBy);

    List<ActivityResponseDTO> getActivitiesByDepartment(Long departmentId);

    List<ActivityResponseDTO> getActivitiesThatWeekByStudent(Long studentId);

    List<ActivityResponseDTO> getActivitiesThatWeekByDepartment(Long departmentId);

    List<Activity> findByTitleContainingIgnoreCase(String title);

    void assignStudentToActivity(Long activityId, Long studentId);

    void assignAllStudentsByDepartmentToActivity(Long activityId, Long departmentId);

    void removeStudentFromActivity(Long activityId, Long studentId);

}
