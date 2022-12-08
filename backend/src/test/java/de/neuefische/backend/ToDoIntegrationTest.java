package de.neuefische.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllToDos_shouldReturnAEmptyList() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        []
                        """));
    }

    @DirtiesContext
    @Test
    void postToDo_shouldAddANewToDo() throws Exception {
        String actual = mockMvc.perform(
                        post("http://localhost:8080/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {"description":"New ToDo","status":"OPEN"}
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "description": "New ToDo",
                          "status": "OPEN"
                        }
                        """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ToDo actualTodo = objectMapper.readValue(actual, ToDo.class);
        assertThat(actualTodo.id)
                .isNotBlank();
    }

    @DirtiesContext
    @Test
    void putToDo_shouldPutAToDoToTheNextList() throws Exception {
        String saveResult = mockMvc.perform(
                        post("http://localhost:8080/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {"description":"New ToDo","status":"OPEN"}
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "description": "New ToDo",
                          "status": "OPEN"
                        }
                        """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ToDo saveResultTodo = objectMapper.readValue(saveResult, ToDo.class);
        String id = saveResultTodo.id;

        mockMvc.perform(
                        put("http://localhost:8080/api/todo/" + id + "/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {"id":"<ID>","description":"Checked ToDo","status":"IN_PROGRESS"}
                                        """.replaceFirst("<ID>", id))
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "id": "<ID>",
                          "description": "Checked ToDo",
                          "status": "IN_PROGRESS"
                        }
                        """.replaceFirst("<ID>", id)));

    }

    @DirtiesContext
    @Test
    void deleteToDo_shouldDeleteAToDo() throws Exception {
        String saveResult = mockMvc.perform(
                        post("http://localhost:8080/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {"description":"New ToDo","status":"OPEN"}
                                        """)
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        ToDo saveResultTodo = objectMapper.readValue(saveResult, ToDo.class);
        String id = saveResultTodo.id;

        mockMvc.perform(delete("http://localhost:8080/api/todo/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(get("http://localhost:8080/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        []
                        """));
    }

    @DirtiesContext
    @Test
    void getToDoById_shouldReturnaSingleToDo() throws Exception {
        String actual = mockMvc.perform(
                        post("http://localhost:8080/api/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {"description":"New ToDo","status":"OPEN"}
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "description": "New ToDo",
                          "status": "OPEN"
                        }
                        """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ToDo actualTodo = objectMapper.readValue(actual, ToDo.class);
        String id = actualTodo.id;

        mockMvc.perform(get("http://localhost:8080/api/todo/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "id": "<ID>",
                          "description": "Next ToDo",
                          "status": "OPEN"
                        }
                        """.replaceFirst("<ID>", id)));
    }
}
