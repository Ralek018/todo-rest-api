package com.ralek.todo_rest_api.model;



import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private TodoUser todoUser;

    private String description;
    private LocalDate targetDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


    public Todo() {
    }

    public Todo(TodoUser todoUser, String description,
                LocalDate targetDate, Status status) {
        this.todoUser = todoUser;
        this.description = description;
        this.targetDate = targetDate;
        this.status = status;
    }

    public Todo(Long id,TodoUser todoUser, String description,
                LocalDate targetDate, Status status) {
        this.id = id;
        this.todoUser = todoUser;
        this.description = description;
        this.targetDate = targetDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public TodoUser getUser() {
        return todoUser;
    }

    public String getUsername(){
        return todoUser.getUsername();
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public Status getStatus() {
        return status;
    }

}
