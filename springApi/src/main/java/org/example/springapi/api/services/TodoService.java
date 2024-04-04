package org.example.springapi.api.services;

import org.example.springapi.api.models.TodoItem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private final List<TodoItem> todoItems = new ArrayList<>();

    public List<TodoItem> getTodos() {
        return todoItems;
    }

    public TodoItem getTodoById(Long id) {
        return todoItems.stream()
                .filter(todoItem -> todoItem.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo item not found"));
    }

    public TodoItem createTodo(TodoItem todoItem) {
        todoItem.setId((long) (todoItems.size() + 1));
        todoItem.setCreatedAt(LocalDateTime.now());
        todoItem.setUpdatedAt(LocalDateTime.now());
        todoItems.add(todoItem);
        return todoItem;
    }

    public TodoItem updateTodo(Long id, TodoItem todoItem) {
        TodoItem existingTodoItem = getTodoById(id);
        existingTodoItem.setTitle(todoItem.getTitle());
        existingTodoItem.setDescription(todoItem.getDescription());
        existingTodoItem.setStatus(todoItem.getStatus());
        existingTodoItem.setUpdatedAt(LocalDateTime.now());
        return existingTodoItem;
    }

    public void deleteTodoById(Long id) {
        TodoItem todoItem = getTodoById(id);
        todoItems.remove(todoItem);
    }

    public TodoService() {
        todoItems.add(new TodoItem(1L, "Create Spring API", "Create a simple Spring API", "IN_PROGRESS", LocalDateTime.now(), LocalDateTime.now()));
        todoItems.add(new TodoItem(2L, "Deploy Spring API", "Deploy the Spring API to a cloud platform", "IN_PROGRESS", LocalDateTime.now(), LocalDateTime.now()));
        todoItems.add(new TodoItem(3L, "Celebrate", "Celebrate the successful deployment of the Spring API", "IN_PROGRESS", LocalDateTime.now(), LocalDateTime.now()));
    }

}