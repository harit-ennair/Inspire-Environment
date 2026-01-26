package com.example.inspire_environment.controller;

import com.example.inspire_environment.dto.request.TaskRequestDTO;
import com.example.inspire_environment.dto.response.TaskResponseDTO;
import com.example.inspire_environment.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO dto) {
        TaskResponseDTO response = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDTO dto) {
        TaskResponseDTO response = taskService.updateTask(taskId, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByActivity(@PathVariable Long activityId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByActivity(activityId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByStudent(@PathVariable Long studentId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByStudent(studentId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
}

