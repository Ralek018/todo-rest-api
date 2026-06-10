package com.ralek.todo_rest_api.dto.response;

import com.ralek.todo_rest_api.model.Status;

import java.time.LocalDate;

public record TodoResponse(
        Long id,
        String description,
        LocalDate targetDate,
        Status status
) {}
