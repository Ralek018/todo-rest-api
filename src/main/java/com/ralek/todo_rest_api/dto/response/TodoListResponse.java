package com.ralek.todo_rest_api.dto.response;

import java.util.List;

public record TodoListResponse(List<TodoResponse> todos) {
}
