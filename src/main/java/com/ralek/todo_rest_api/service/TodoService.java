package com.ralek.todo_rest_api.service;

import com.ralek.todo_rest_api.dto.request.TodoRequest;
import com.ralek.todo_rest_api.dto.response.TodoListResponse;
import com.ralek.todo_rest_api.dto.response.TodoResponse;
import com.ralek.todo_rest_api.exceptions.TodoNotFoundException;
import com.ralek.todo_rest_api.exceptions.TodoUserNotFoundException;
import com.ralek.todo_rest_api.repository.TodoRepository;
import com.ralek.todo_rest_api.model.Todo;
import com.ralek.todo_rest_api.repository.TodoUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoUserRepository todoUserRepository;

    public TodoService(TodoRepository todoRepository, TodoUserRepository todoUserRepository) {

        this.todoRepository = todoRepository;
        this.todoUserRepository = todoUserRepository;
    }

    public TodoListResponse findByUserName(String username){

        var todoUser = todoUserRepository.findByUsername(username)
                .orElseThrow(() -> new TodoUserNotFoundException
                        ("User with username:[" + username + "] not found"));
        var list = todoUser.getTodos().stream()
                .map(todo->new TodoResponse(todo.getId(),
                        todo.getDescription(),todo.getTargetDate()
                        ,todo.getStatus())).toList();
        return new TodoListResponse(list);

    }

    @Transactional
    public TodoResponse addTodo(String username, TodoRequest request){

        var todoUser = todoUserRepository.findByUsername(username)
                .orElseThrow(() -> new TodoUserNotFoundException
                        ("User with username:[" + username + "] not found"));
        var todo = new Todo(todoUser,request.description(),request.targetDate(),request.status());

        var saved = todoRepository.save(todo);
        return new TodoResponse(saved.getId(), request.description(),request.targetDate(),request.status());
    }

    public TodoResponse findById(Long id){

        var todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException
                        ("Todo with ID:[" + id + "] not found"));
        return new TodoResponse(todo.getId(),todo.getDescription(),todo.getTargetDate(),todo.getStatus());
    }

    @Transactional
    public void deleteById(Long id){

        if(!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo with ID:[" + id + "] not found");
        }
        todoRepository.deleteById(id);
    }

    @Transactional
    public TodoResponse updateTodo(String username, Long todoId, TodoRequest request){

        var todoUser = todoUserRepository.findByUsername(username)
                .orElseThrow(() -> new TodoUserNotFoundException
                        ("User with username:[" + username + "] not found"));

        if(!todoRepository.existsById(todoId)){
            throw new TodoNotFoundException("Todo with ID:[" + todoId + "] not found");
        }

        Todo updated = new Todo(todoId,todoUser,request.description(),request.targetDate(),request.status());
        todoRepository.save(updated);
        return new TodoResponse(todoId,updated.getDescription(),updated.getTargetDate(),updated.getStatus());
    }
}
