package com.taskboard.controller;

import com.taskboard.exception.TaskListNotFoundException;
import com.taskboard.model.TaskList;
import com.taskboard.service.TaskListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
@Tag(name = "Task List", description = "APIs for managing task lists")
@Slf4j
public class ListController {

    @Autowired
    private TaskListService taskListService;

    @GetMapping
    @Operation(summary = "Get all task lists")
    public ResponseEntity<List<TaskList>> getAllLists() {
        log.info("Fetching all task lists");
        return ResponseEntity.ok(taskListService.getAllLists());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a task list by ID")
    public ResponseEntity<TaskList> getListById(@PathVariable Long id) {
        log.info("Fetching task list with ID: {}", id);
        return ResponseEntity.ok(taskListService.getListById(id)
                .orElseThrow(() -> new TaskListNotFoundException(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new task list")
    public ResponseEntity<TaskList> createList(@RequestBody TaskList taskList) {
        log.info("Creating a new task list with name: {}", taskList.getName());
        return ResponseEntity.ok(taskListService.createList(taskList));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task list by ID")
    public ResponseEntity<Void> deleteList(@PathVariable Long id) {
        log.info("Deleting task list with ID: {}", id);
        taskListService.deleteList(id);
        return ResponseEntity.noContent().build();
    }
}