package com.taskboard.service;

import com.taskboard.model.Task;
import com.taskboard.model.TaskList;
import com.taskboard.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskListService taskListService;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTask() {
        TaskList list = new TaskList();
        list.setId(1L);
        Task task = new Task();
        task.setName("New Task");
        task.setDescription("Task Description");

        when(taskListService.getAllLists()).thenReturn(Arrays.asList(list));
        when(taskRepository.save(task)).thenReturn(task);

        Task addedTask = taskService.addTask(1L, task);
        assertEquals("New Task", addedTask.getName());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Old Task");
        task.setDescription("Old Description");

        Task taskDetails = new Task();
        taskDetails.setName("Updated Task");
        taskDetails.setDescription("Updated Description");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task updatedTask = taskService.updateTask(1L, taskDetails);
        assertEquals("Updated Task", updatedTask.getName());
        assertEquals("Updated Description", updatedTask.getDescription());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;
        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void testMoveTask() {
        TaskList oldList = new TaskList();
        oldList.setId(1L);
        TaskList newList = new TaskList();
        newList.setId(2L);
        Task task = new Task();
        task.setId(1L);
        task.setTaskList(oldList);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskListService.getAllLists()).thenReturn(Arrays.asList(newList));
        when(taskRepository.save(task)).thenReturn(task);

        taskService.moveTask(1L, 2L);
        assertEquals(2L, task.getTaskList().getId());
        verify(taskRepository, times(1)).save(task);
    }
}