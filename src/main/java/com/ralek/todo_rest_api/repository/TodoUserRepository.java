package com.ralek.todo_rest_api.repository;

import com.ralek.todo_rest_api.model.TodoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoUserRepository extends JpaRepository<TodoUser,Long> {
    Optional<TodoUser> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
