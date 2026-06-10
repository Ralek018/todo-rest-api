package com.ralek.todo_rest_api.security;

import com.ralek.todo_rest_api.model.TodoUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class TodoUserDetails implements UserDetails {

    private final TodoUser todoUser;

    public TodoUserDetails(TodoUser todoUser) {
        this.todoUser = todoUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(todoUser.getRole()));
    }

    @Override
    public String getPassword() {
        return todoUser.getPassword();
    }

    @Override
    public String getUsername() {
        return todoUser.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return todoUser.isEnabled();
    }


}
