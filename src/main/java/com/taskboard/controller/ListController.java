package com.taskboard.controller;

import com.taskboard.model.TaskList;
import com.taskboard.service.TaskListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/lists")
@Tag(name = "Task List", description = "APIs for managing task lists")
@Slf4j
public class ListController {
    @Autowired
    private TaskListService taskListService;

    @GetMapping
    public ResponseEntity<List<TaskList>> getAllLists() {
        return new ResponseEntity<>(taskListService.getAllLists(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskList> createList(@RequestBody TaskList taskList) {
        TaskList savedTaskList = taskListService.createList(taskList);
        log.info("TaskList Saved with Details : "+savedTaskList.getId()+" : "+savedTaskList.getName());
        return new ResponseEntity<>(savedTaskList,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteList(@PathVariable Long id) {
        log.info("Deleteing TakList with Id :"+id);
        taskListService.deleteList(id);
    }
}