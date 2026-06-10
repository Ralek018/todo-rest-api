package com.ralek.todo_rest_api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "app_user")
public class TodoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "todoUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private boolean enabled;

    public TodoUser(){

    }
    public TodoUser(String username,
                    String email, String password, Role role, boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public TodoUser(Long id, List<Todo> todos, String username, String email,
                    String password, Role role, boolean enabled) {
        this.id = id;
        this.todos = (todos == null) ? new ArrayList<>() : todos;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public TodoUser(TodoUser oldUser, Role role) {
        this(oldUser.id, oldUser.getTodos(), oldUser.username, oldUser.getEmail(),
                oldUser.password, role, oldUser.enabled);
    }

    public TodoUser(TodoUser oldUser, Boolean enabled) {
        this(oldUser.id, oldUser.getTodos(), oldUser.username, oldUser.getEmail(),
                oldUser.password, oldUser.role, enabled);
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public String getRole() {
        return role.name();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void addTodo(Todo todo){
        this.todos.add(todo);
    }

    @Override
    public String toString() {
        return "userID: %d:, username: %s, role: %s, email: %s".formatted(id, username, role, email);
    }
}
