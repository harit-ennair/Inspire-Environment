package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.TaskRequestDTO;
import com.example.inspire_environment.dto.response.TaskResponseDTO;
import com.example.inspire_environment.entity.Activity;
import com.example.inspire_environment.entity.Task;
import com.example.inspire_environment.exception.ResourceNotFoundException;
import com.example.inspire_environment.mapper.TaskMapper;
import com.example.inspire_environment.repository.ActivityRepository;
import com.example.inspire_environment.repository.TaskRepository;
import com.example.inspire_environment.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ActivityRepository activityRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        Task task = taskMapper.toEntity(dto);

        // Set the activity if activityId is provided
        if (dto.getActivityId() != null) {
            Activity activity = activityRepository.findById(dto.getActivityId())
                    .orElseThrow(() -> new ResourceNotFoundException("Activity", "id", dto.getActivityId()));
            task.setActivity(activity);
        }

        return taskMapper.toResponseDTO(taskRepository.save(task));
    }

    @Override
    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        taskMapper.updateEntityFromDTO(dto, task);

        // Update the activity if activityId is provided
        if (dto.getActivityId() != null) {
            Activity activity = activityRepository.findById(dto.getActivityId())
                    .orElseThrow(() -> new ResourceNotFoundException("Activity", "id", dto.getActivityId()));
            task.setActivity(activity);
        }

        return taskMapper.toResponseDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByActivity(Long activityId) {
        return taskRepository.findByActivityId(activityId)
                .stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByStudent(Long studentId) {
        // Find tasks through submissions for this student
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getSubmissions() != null &&
                        task.getSubmissions().stream()
                                .anyMatch(submission -> submission.getStudent() != null &&
                                        submission.getStudent().getId().equals(studentId)))
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }
}
