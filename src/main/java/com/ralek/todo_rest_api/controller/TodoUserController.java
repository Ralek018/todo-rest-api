package com.ralek.todo_rest_api.controller;

import com.ralek.todo_rest_api.dto.request.ChangeRoleRequest;
import com.ralek.todo_rest_api.dto.response.TodoUserListResponse;
import com.ralek.todo_rest_api.dto.response.TodoUserResponse;
import com.ralek.todo_rest_api.service.TodoUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoUserController {

    private final TodoUserService todoUserService;

    public TodoUserController(TodoUserService todoUserService) {
        this.todoUserService = todoUserService;
    }

    @GetMapping("/admin/users")
    public TodoUserListResponse getAllUsers() {
        return todoUserService.findAll();
    }

    @DeleteMapping("/admin/users/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        todoUserService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/users/{username}/toggle")
    public TodoUserResponse toggleEnabled(@PathVariable String username) {
        return todoUserService.toggleEnabled(username);
    }

    @PutMapping("/admin/users/{username}/role")
    public TodoUserResponse changeRole(@PathVariable String username,
                                       @Valid @RequestBody ChangeRoleRequest request) {
        return todoUserService.changeRole(username, request.role());
    }
}
