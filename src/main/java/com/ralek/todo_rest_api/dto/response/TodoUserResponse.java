package com.ralek.todo_rest_api.dto.response;

public record TodoUserResponse(
        Long id,
        String username,
        String email,
        String role,
        boolean enabled
) {}
