
package com.taskboard.controller;

import com.taskboard.model.TaskList;
import com.taskboard.repository.TaskListRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ListControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskListRepository taskListRepository;

    @BeforeEach
    public void setUp() {
        taskListRepository.deleteAll();
    }

    @Test
    public void testGetAllLists() throws Exception {
        TaskList list = new TaskList();
        list.setName("Test List");
        taskListRepository.save(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/lists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test List"));
    }

    @Test
    public void testCreateList() throws Exception {
        String listJson = "{\"name\": \"New List\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(listJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New List"));
    }

    @Test
    public void testDeleteList() throws Exception {
        TaskList list = new TaskList();
        list.setName("Test List");
        TaskList savedList = taskListRepository.save(list);
        log.info("Task Saved with Id : "+savedList.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete("/lists/" + savedList.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
