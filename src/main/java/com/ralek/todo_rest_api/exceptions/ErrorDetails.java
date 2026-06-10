package com.ralek.todo_rest_api.exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ErrorDetails {

    private String message;
    private int status;
    private LocalDate timestamp;
    private String path;

    public ErrorDetails(String message, int status, LocalDate timestamp, String path) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "[%s (%s) -> %s]: %s".formatted(message,status,path,
                timestamp.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    }
}
