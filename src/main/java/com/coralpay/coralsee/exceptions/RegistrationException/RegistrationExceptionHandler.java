package com.coralpay.coralsee.exceptions.RegistrationException;

import com.coralpay.coralsee.dtos.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistrationExceptionHandler {

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex){
//        ErrorResponse response = ErrorResponse.builder()
//                .error("Invalid User Detail")
//                .message(ex.getMessage())
//                .detail("Ensure user has an account with Coralsee")
//                .build();
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex){
        ErrorResponse response = ErrorResponse.builder()
                .error("Reg 0001")
                .message(ex.getMessage())
                .detail("Ensure that the email sent on request has not been registered with already")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
