package com.ralek.todo_rest_api.dto.response;

import java.util.List;

public record TodoUserListResponse(List<TodoUserResponse> todoUsers) {}
