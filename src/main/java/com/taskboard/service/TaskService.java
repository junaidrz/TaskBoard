package com.taskboard.service;

import com.taskboard.model.Task;
import com.taskboard.model.TaskList;
import com.taskboard.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListService taskListService;

    public Task addTask(Long listId, Task task) {
        TaskList taskList = taskListService.getAllLists().stream()
                .filter(list -> list.getId().equals(listId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("List not found"));
        task.setTaskList(taskList);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setName(taskDetails.getName());
        task.setDescription(taskDetails.getDescription());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void moveTask(Long taskId, Long newListId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        TaskList newList = taskListService.getAllLists().stream()
                .filter(list -> list.getId().equals(newListId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("List not found"));
        task.setTaskList(newList);
        taskRepository.save(task);
    }
}