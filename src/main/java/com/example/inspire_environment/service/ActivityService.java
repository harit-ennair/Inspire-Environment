package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.ActivityRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActivityService {

    ActivityResponseDTO createActivity(ActivityRequestDTO dto);

    ActivityRequestDTO updateActivity(Long id, ActivityRequestDTO dto);

    ActivityResponseDTO getActivityById(Long id);

    List<ActivityResponseDTO> getAllActivities(); // Admin / Staff

    Page<ActivityResponseDTO> searchActivities(String search, int page, int size);

    List<ActivityResponseDTO> getActivitiesByStudent(Long studentId);

    List<ActivityResponseDTO> getActivitiesManagedBy(long managedById);

    List<ActivityResponseDTO> getActivitiesByDepartment(Long departmentId);

    List<ActivityResponseDTO> getActivitiesThatWeekByStudent(Long studentId);

    List<ActivityResponseDTO> getActivitiesThatWeekByDepartment(Long departmentId);


    void assignStudentToActivity(Long activityId, Long studentId);

    void assignStaffToActivity(Long activityId, Long staffId);

    void assignAllStudentsByDepartmentToActivity(Long activityId, Long departmentId);

    void removeStudentFromActivity(Long activityId, Long studentId);

    void deleteActivity(Long id);

}
