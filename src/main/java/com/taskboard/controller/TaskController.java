package com.taskboard.controller;

import com.taskboard.exception.TaskNotFoundException;
import com.taskboard.model.Task;
import com.taskboard.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task", description = "APIs for managing tasks")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/{listId}")
    @Operation(summary = "Add a new task to a list")
    public ResponseEntity<Task> addTask(@PathVariable Long listId, @RequestBody Task task) {
        log.info("Adding a new task to list with ID: {}", listId);
        return ResponseEntity.ok(taskService.addTask(listId, task));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task by ID")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        log.info("Updating task with ID: {}", id);
        return ResponseEntity.ok(taskService.updateTask(id, taskDetails));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by ID")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.info("Deleting task with ID: {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{taskId}/move/{newListId}")
    @Operation(summary = "Move a task to a different list")
    public ResponseEntity<Void> moveTask(@PathVariable Long taskId, @PathVariable Long newListId) {
        log.info("Moving task with ID: {} to list with ID: {}", taskId, newListId);
        taskService.moveTask(taskId, newListId);
        return ResponseEntity.noContent().build();
    }
}