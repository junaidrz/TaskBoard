package com.taskboard.controller;

import com.taskboard.model.Task;
import com.taskboard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task", description = "APIs for managing tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/{listId}")
    public ResponseEntity<Task> addTask(@PathVariable Long listId, @RequestBody Task task) {
        return new ResponseEntity<>(taskService.addTask(listId, task), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return new ResponseEntity<>(taskService.updateTask(id, taskDetails),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/{taskId}/move/{newListId}")
    public void moveTask(@PathVariable Long taskId, @PathVariable Long newListId) {
        taskService.moveTask(taskId, newListId);
    }
}