package com.ralek.todo_rest_api.dto.response;

public record JwtResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        String username,
        String role
) {}
