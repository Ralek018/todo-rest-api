package com.ralek.todo_rest_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeRoleRequest(
        @NotBlank(message = "Role cannot be blank")
        String role
) {}
