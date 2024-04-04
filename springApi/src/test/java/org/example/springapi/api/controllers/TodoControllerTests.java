package org.example.springapi.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springapi.api.models.TodoItem;
import org.example.springapi.api.services.TodoService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TodoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;
    private List<TodoItem> todoItems;
    private TodoItem todoItem;

    @BeforeEach
    public void setUp() {
        todoItems = List.of(
            new TodoItem(1L, "Do the dishes", "Do the dishes before bedtime", "IN_PROGRESS", LocalDateTime.now(), LocalDateTime.now()),
            new TodoItem(2L, "Walk the dog", "Take the dog for a walk in the park", "IN_PROGRESS", LocalDateTime.now(), LocalDateTime.now())
        );
        todoItem = new TodoItem(1L, "Do the dishes", "Do the dishes before bedtime", "IN_PROGRESS", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void TodoController_GetTodos_ReturnsListOfTodoItems() throws Exception {
        when(todoService.getTodos()).thenReturn(todoItems);

        ResultActions resultActions = mockMvc.perform(get("/todos"));


            resultActions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(todoItems)));

    }

    @Test
    public void TodoController_GetTodoById_ReturnsTodoItem() throws Exception {
        Long id = 1L;
        when(todoService.getTodoById(id)).thenReturn(todoItem);

        ResultActions resultActions = mockMvc.perform(get("/todos/{id}", id));

            resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(todoItem.getTitle())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(todoItem.getStatus())));
    }

    @Test
    public void TodoController_CreateTodo_ReturnsCreatedTodoItem() throws Exception {
        given(todoService.createTodo(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions resultActions = mockMvc.perform(post("/todos")
            .content(objectMapper.writeValueAsString(todoItem))
            .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(todoItem.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(todoItem.getStatus())));
    }

    @Test
    public void TodoController_UpdateTodo_ReturnsTodoItem() throws Exception {
        when(todoService.updateTodo(todoItem.getId(), todoItem)).thenReturn(todoItem);

        ResultActions resultActions = mockMvc.perform(put("/todos/{id}", todoItem.getId())
            .content(objectMapper.writeValueAsString(todoItem))
            .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void TodoController_DeleteTodoById_ReturnsNoContent() throws Exception {
        Long id = 1L;
        doNothing().when(todoService).deleteTodoById(id);
        ResultActions resultActions = mockMvc.perform(delete("/todos/{id}", id));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
