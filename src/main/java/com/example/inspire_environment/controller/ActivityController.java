package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.ActivityRequestDTO;
import com.example.inspire_environment.dto.response.ActivityResponseDTO;
import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.mapper.ActivityMapper;
import com.example.inspire_environment.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper;

    @GetMapping
    public ResponseEntity<List<ActivityResponseDTO>> getAllActivities() {
        List<Activity> activities = activityRepository.findAll();
        List<ActivityResponseDTO> activityDTOs = activities.stream()
                .map(activityMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activityDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> getActivityById(@PathVariable Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isPresent()) {
            ActivityResponseDTO activityDTO = activityMapper.toResponseDTO(activity.get());
            return ResponseEntity.ok(activityDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public RequestEntity<ActivityRequestDTO> createActivity(@RequestBody ActivityRequestDTO activityRequestDTO) {
        Activity activity = activityMapper.toEntity(activityRequestDTO);
        Activity savedActivity = activityRepository.save(activity);
        ActivityRequestDTO activityDTO = activityMapper.toRequestDTO(savedActivity);
        return RequestEntity.post("/api/activities").body(activityDTO);
    }

    @PutMapping("/{id}")
    public RequestEntity<ActivityRequestDTO> updateActivity(@PathVariable Long id, @RequestBody ActivityRequestDTO activityRequestDTO) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            activityMapper.updateEntityFromDTO(activityRequestDTO, activity);
            Activity updatedActivity = activityRepository.save(activity);
            ActivityRequestDTO activityDTO = activityMapper.toRequestDTO(updatedActivity);
            return RequestEntity.ok(activityDTO);
        } else {
            return RequestEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
