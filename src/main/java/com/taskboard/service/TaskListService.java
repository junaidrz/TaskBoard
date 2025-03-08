package com.taskboard.service;

import com.taskboard.model.TaskList;
import com.taskboard.repository.TaskListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskListService {
    @Autowired
    private TaskListRepository taskListRepository;

    public List<TaskList> getAllLists() {
        return taskListRepository.findAll();
    }

    public TaskList createList(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    public void deleteList(Long id) {
        taskListRepository.deleteById(id);
    }
}