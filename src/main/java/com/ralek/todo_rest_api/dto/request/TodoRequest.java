package com.ralek.todo_rest_api.dto.request;

import com.ralek.todo_rest_api.model.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TodoRequest(
        @NotBlank (message = "Description cannot be blank")
        String description,

        @NotNull(message = "Target date cannot be null")
        LocalDate targetDate,

        @NotNull(message = "Status cannot be null")
        Status status
) {}
