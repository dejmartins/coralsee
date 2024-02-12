package com.coralpay.coralsee.exceptions.TransactionException;

import com.coralpay.coralsee.dtos.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionHandler {
    @ExceptionHandler(InvalidTransactionDetailException.class)
    public ResponseEntity<?> handleInvalidPostDetail(InvalidTransactionDetailException ex){
        ErrorResponse response = ErrorResponse.builder()
                .error("Invalid Transaction Detail")
                .message(ex.getMessage())
                .detail("Ensure that all transaction request details are available and correct")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
