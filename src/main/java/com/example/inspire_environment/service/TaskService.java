package com.example.inspire_environment.service;

import com.example.inspire_environment.dto.request.TaskRequestDTO;
import com.example.inspire_environment.dto.response.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO dto);

    TaskResponseDTO updateTask(Long taskId, TaskRequestDTO dto);

    void deleteTask(Long taskId);

    List<TaskResponseDTO> getTasksByActivity(Long activityId);

    List<TaskResponseDTO> getTasksByStudent(Long studentId);

    List<TaskResponseDTO> getAllTasks();
}
