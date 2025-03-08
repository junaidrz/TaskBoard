package com.taskboard.controller;

import com.taskboard.model.TaskList;
import com.taskboard.service.TaskListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ListControllerTest {

    @Mock
    private TaskListService taskListService;

    @InjectMocks
    private ListController listController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllLists() {
        TaskList list1 = new TaskList();
        list1.setName("List 1");
        TaskList list2 = new TaskList();
        list2.setName("List 2");

        when(taskListService.getAllLists()).thenReturn(Arrays.asList(list1, list2));

        ResponseEntity<List<TaskList>> response = listController.getAllLists();
        assertEquals(2, response.getBody().size());
        verify(taskListService, times(1)).getAllLists();
    }

    @Test
    public void testCreateList() {
        TaskList list = new TaskList();
        list.setName("New List");

        when(taskListService.createList(list)).thenReturn(list);

        ResponseEntity<TaskList> response = listController.createList(list);
        assertEquals("New List", response.getBody().getName());
        verify(taskListService, times(1)).createList(list);
    }

    @Test
    public void testDeleteList() {
        Long listId = 1L;
        doNothing().when(taskListService).deleteList(listId);

        listController.deleteList(listId);
        verify(taskListService, times(1)).deleteList(listId);
    }
}