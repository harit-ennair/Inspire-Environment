package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.ActivityRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<ActivityResponseDTO>> getAllActivities() {
        List<ActivityResponseDTO> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ActivityResponseDTO>> searchActivities(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ActivityResponseDTO> result = activityService.searchActivities(search, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> getActivityById(@PathVariable("id") Long id) {
        ActivityResponseDTO activity = activityService.getActivityById(id);
        return ResponseEntity.ok(activity);
    }

    @PostMapping
    public ResponseEntity<ActivityResponseDTO> createActivity(@RequestBody ActivityRequestDTO activityRequestDTO) {
        ActivityResponseDTO activity = activityService.createActivity(activityRequestDTO);
        return ResponseEntity.ok(activity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityRequestDTO> updateActivity(@PathVariable Long id, @RequestBody ActivityRequestDTO activityRequestDTO) {
        ActivityRequestDTO activity = activityService.updateActivity(id, activityRequestDTO);
        return ResponseEntity.ok(activity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ActivityResponseDTO>> getActivitiesByStudent(@PathVariable Long studentId) {
        List<ActivityResponseDTO> activities = activityService.getActivitiesByStudent(studentId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/managed-by/{managedById}")
    public ResponseEntity<List<ActivityResponseDTO>> getActivitiesManagedBy(@PathVariable Long managedById) {
        List<ActivityResponseDTO> activities = activityService.getActivitiesManagedBy(managedById);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<ActivityResponseDTO>> getActivitiesByDepartment(@PathVariable Long departmentId) {
        List<ActivityResponseDTO> activities = activityService.getActivitiesByDepartment(departmentId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/this-week/student/{studentId}")
    public ResponseEntity<List<ActivityResponseDTO>> getActivitiesThatWeekByStudent(@PathVariable Long studentId) {
        List<ActivityResponseDTO> activities = activityService.getActivitiesThatWeekByStudent(studentId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/this-week/department/{departmentId}")
    public ResponseEntity<List<ActivityResponseDTO>> getActivitiesThatWeekByDepartment(@PathVariable Long departmentId) {
        List<ActivityResponseDTO> activities = activityService.getActivitiesThatWeekByDepartment(departmentId);
        return ResponseEntity.ok(activities);
    }


    @PostMapping("/{activityId}/assign-student/{studentId}")
    public ResponseEntity<Void> assignStudentToActivity(@PathVariable Long activityId, @PathVariable Long studentId) {
        activityService.assignStudentToActivity(activityId, studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{activityId}/assign-staff/{staffId}")
    public ResponseEntity<Void> assignStaffToActivity(@PathVariable Long activityId, @PathVariable Long staffId) {
        activityService.assignStaffToActivity(activityId, staffId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{activityId}/assign-department/{departmentId}")
    public ResponseEntity<Void> assignAllStudentsByDepartmentToActivity(@PathVariable Long activityId, @PathVariable Long departmentId) {
        activityService.assignAllStudentsByDepartmentToActivity(activityId, departmentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{activityId}/remove-student/{studentId}")
    public ResponseEntity<Void> removeStudentFromActivity(@PathVariable Long activityId, @PathVariable Long studentId) {
        activityService.removeStudentFromActivity(activityId, studentId);
        return ResponseEntity.noContent().build();
    }
}
