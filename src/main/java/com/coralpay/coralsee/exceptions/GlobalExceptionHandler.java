package com.coralpay.coralsee.exceptions;

import com.coralpay.coralsee.dtos.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleRegistrationException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Validation Error")
                .message(errors)
                .detail("Ensure all request details are valid")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(IOException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Service Unavailable")
                .message(ex.getMessage())
                .detail("HTTP request was not successful")
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }
}
