package org.example.springapi.api.controllers;

import org.example.springapi.api.models.TodoItem;
import org.example.springapi.api.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoItem>> getTodos() {

        return new ResponseEntity<>(todoService.getTodos(), HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem todoItem) {

        return new ResponseEntity<>(todoService.createTodo(todoItem), HttpStatus.CREATED);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoItem> updateTodo(@RequestBody TodoItem todoItem, @PathVariable("id") Long id) {
        TodoItem response =  todoService.updateTodo(id, todoItem);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable("id") Long id) {
        todoService.deleteTodoById(id);
        return new ResponseEntity<>("Item deleted", HttpStatus.OK);
    }
}