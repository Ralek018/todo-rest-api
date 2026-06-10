package com.ralek.todo_rest_api.service;

import com.ralek.todo_rest_api.dto.response.TodoUserListResponse;
import com.ralek.todo_rest_api.dto.response.TodoUserResponse;
import com.ralek.todo_rest_api.exceptions.TodoUserAlreadyExistsException;
import com.ralek.todo_rest_api.exceptions.TodoUserNotFoundException;
import com.ralek.todo_rest_api.model.Role;
import com.ralek.todo_rest_api.model.TodoUser;
import com.ralek.todo_rest_api.repository.TodoUserRepository;
import com.ralek.todo_rest_api.security.TodoUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;

@Service
@Transactional(readOnly = true)
public class TodoUserService implements UserDetailsService {

    private final TodoUserRepository todoUserRepository;
    private final PasswordEncoder passwordEncoder;

    public TodoUserService(TodoUserRepository todoUserRepository, PasswordEncoder passwordEncoder) {
        this.todoUserRepository = todoUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var todoUser = todoUserRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(
                "User " + username + " not found"));
        return new TodoUserDetails(todoUser);
    }

    public TodoUserResponse findByUsername(String username){

        var todoUser = todoUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                        ("User with username: " + username + " not found"));
        return toResponse(todoUser);
    }

    @Transactional
    public TodoUserResponse register(String username, String email, String password){

        if(todoUserRepository.existsByUsername(username)){
            throw new TodoUserAlreadyExistsException("Username already taken");
        }

        if(todoUserRepository.existsByEmail(email)){
            throw new TodoUserAlreadyExistsException("Email already in use");
        }

        TodoUser todoUser = new TodoUser(username,email,passwordEncoder.encode(password),
                Role.ROLE_USER,true);

        todoUserRepository.save(todoUser);
        return toResponse(todoUser);
    }

    public TodoUserListResponse findAll(){

        var list = todoUserRepository.findAll().stream().map(this::toResponse).toList();
        return new TodoUserListResponse(list);
    }

    @Transactional
    public void deleteByUsername(String username){

        TodoUser todoUser = todoUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new TodoUserNotFoundException
                                ("User with username: " + username + " not found"));

        todoUserRepository.delete(todoUser);
    }

    @Transactional
    public TodoUserResponse toggleEnabled(String username){

        TodoUser todoUser = todoUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new TodoUserNotFoundException
                                ("User with username: " + username + " not found"));
        TodoUser updated = new TodoUser(todoUser, !todoUser.isEnabled());
        todoUserRepository.save(updated);
        return toResponse(updated);
    }

    @Transactional
    public TodoUserResponse changeRole(String username, String role){

        TodoUser todoUser = todoUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new TodoUserNotFoundException
                                ("User with username: " + username + " not found"));

        EnumSet<Role> roles = EnumSet.allOf(Role.class);
        if(roles.stream().anyMatch
                (matchingRole -> matchingRole.toString().equalsIgnoreCase(role))){
            TodoUser updated = new TodoUser(todoUser, Role.valueOf(role.toUpperCase()));
            todoUserRepository.save(updated);
            return toResponse(updated);
        }
        throw new IllegalArgumentException("Invalid role " + role);
    }

    private TodoUserResponse toResponse(TodoUser user){
        return new TodoUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled()
        );
    }
}
