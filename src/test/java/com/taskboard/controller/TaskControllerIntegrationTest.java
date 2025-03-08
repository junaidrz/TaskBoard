
package com.taskboard.controller;

import com.taskboard.model.Task;
import com.taskboard.model.TaskList;
import com.taskboard.repository.TaskListRepository;
import com.taskboard.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        taskRepository.deleteAll();
        taskListRepository.deleteAll();
    }

    @Test
    public void testAddTask() throws Exception {
        TaskList list = new TaskList();
        list.setName("Test List");
        TaskList savedList = taskListRepository.save(list);

        String taskJson = "{\"name\": \"New Task\", \"description\": \"Task Description\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/" + savedList.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Task"))
                .andExpect(jsonPath("$.description").value("Task Description"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskList list = new TaskList();
        list.setName("Test List");
        TaskList savedList = taskListRepository.save(list);

        Task task = new Task();
        task.setName("Old Task");
        task.setDescription("Old Description");
        task.setTaskList(savedList);
        Task savedTask = taskRepository.save(task);

        String taskJson = "{\"name\": \"Updated Task\", \"description\": \"Updated Description\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/" + savedTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        TaskList list = new TaskList();
        list.setName("Test List");
        TaskList savedList = taskListRepository.save(list);

        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setTaskList(savedList);
        Task savedTask = taskRepository.save(task);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/" + savedTask.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testMoveTask() throws Exception {
        TaskList list1 = new TaskList();
        list1.setName("List 1");
        TaskList savedList1 = taskListRepository.save(list1);

        TaskList list2 = new TaskList();
        list2.setName("List 2");
        TaskList savedList2 = taskListRepository.save(list2);

        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setTaskList(savedList1);
        Task savedTask = taskRepository.save(task);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/" + savedTask.getId() + "/move/" + savedList2.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
