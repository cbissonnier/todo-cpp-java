package org.example.springapi.api.controller;

import org.example.springapi.api.model.TodoItem;
import org.example.springapi.api.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public List<TodoItem> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/todos/{id}")
    public TodoItem getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @PostMapping("/todos")
    public TodoItem createTodo(@RequestBody TodoItem todoItem) {
        return todoService.createTodo(todoItem);
    }

    @PutMapping("/todos/{id}")
    public TodoItem updateTodo(@PathVariable Long id, @RequestBody TodoItem todoItem) {
        return todoService.updateTodo(id, todoItem);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodoById(@PathVariable Long id) {
        todoService.deleteTodoById(id);
    }
}