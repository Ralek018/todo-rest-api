package com.ralek.todo_rest_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({TodoUserNotFoundException.class, TodoNotFoundException.class})
    public final ResponseEntity<ErrorDetails> handleNotFound(RuntimeException exception,
                                                                     WebRequest request){

        var error = new ErrorDetails(exception.getMessage(),404, LocalDate.now(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidation(MethodArgumentNotValidException exception,
                                                         WebRequest request) {
        String message = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        var error = new ErrorDetails(message, 400, LocalDate.now(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TodoUserAlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleConflict(RuntimeException exception,
                                                                          WebRequest request){

        var error = new ErrorDetails(exception.getMessage(),409,
                LocalDate.now(), request.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleGeneral(Exception exception,
                                                            WebRequest request){

        var error = new ErrorDetails(exception.getMessage(), 500,
                LocalDate.now(), request.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentials(BadCredentialsException ex,
                                                             WebRequest request) {
        var error = new ErrorDetails(ex.getMessage(), 401, LocalDate.now(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDenied(AccessDeniedException ex,
                                                           WebRequest request) {
        var error = new ErrorDetails("You do not have permission to access this resource",
                403, LocalDate.now(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

}
