package com.example.inspire_environment.service.impl;

import com.example.inspire_environment.dto.request.TaskRequestDTO;
import com.example.inspire_environment.dto.response.TaskResponseDTO;
import com.example.inspire_environment.service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    @Override
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        return null;
    }

    @Override
    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO dto) {
        return null;
    }

    @Override
    public void deleteTask(Long taskId) {

    }

    @Override
    public List<TaskResponseDTO> getTasksByActivity(Long activityId) {
        return List.of();
    }

    @Override
    public List<TaskResponseDTO> getTasksByStudent(Long studentId) {
        return List.of();
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        return List.of();
    }
}
