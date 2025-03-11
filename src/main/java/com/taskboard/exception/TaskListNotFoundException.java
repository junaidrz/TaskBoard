package com.taskboard.exception;

public class TaskListNotFoundException extends RuntimeException {
    public TaskListNotFoundException(Long id) {
        super("Task list not found with ID: " + id);
    }
}