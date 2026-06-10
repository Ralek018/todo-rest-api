package com.ralek.todo_rest_api.controller;

import com.ralek.todo_rest_api.dto.request.TodoRequest;
import com.ralek.todo_rest_api.dto.response.TodoListResponse;
import com.ralek.todo_rest_api.dto.response.TodoResponse;
import com.ralek.todo_rest_api.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @GetMapping(path = "/users/{username}/todos")
    public TodoListResponse retrieveUserTodos(@PathVariable String username){

        return todoService.findByUserName(username);
    }

    @GetMapping(path = "/users/{username}/todos/{id}")
    public TodoResponse retrieveTodo(@PathVariable String username, @PathVariable Long id){

        return todoService.findById(id);
    }

    @DeleteMapping(path = "/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteUserTodoById(@PathVariable String username,
                                                   @PathVariable Long id){

        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/users/{username}/todos/{id}")
    public TodoResponse updateTodo(@PathVariable String username,
                                   @PathVariable Long id,
                                   @Valid @RequestBody TodoRequest request) {

        return todoService.updateTodo(username,id,request);
    }

    @PostMapping(path = "/users/{username}/todos")
    public ResponseEntity<TodoResponse> createTodo(@PathVariable String username,
                                                   @Valid @RequestBody TodoRequest request){

        var todoResp = todoService.addTodo(username, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoResp);
    }
}
