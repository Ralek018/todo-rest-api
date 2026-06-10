package com.ralek.todo_rest_api.exceptions;

public class TodoUserAlreadyExistsException extends RuntimeException {

    public TodoUserAlreadyExistsException(String message) {
        super(message);
    }
}
