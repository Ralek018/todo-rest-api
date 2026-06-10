package com.ralek.todo_rest_api.repository;

import com.ralek.todo_rest_api.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo,Long> {

}
