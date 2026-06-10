package com.ralek.todo_rest_api.exceptions;

public class TodoUserNotFoundException extends RuntimeException{

    public TodoUserNotFoundException(String message) {
        super(message);
    }
}
