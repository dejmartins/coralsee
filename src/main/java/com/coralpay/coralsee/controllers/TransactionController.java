package com.coralpay.coralsee.controllers;

import com.coralpay.coralsee.dtos.requests.CreateTransactionRequest;
import com.coralpay.coralsee.dtos.requests.SignupRequest;
import com.coralpay.coralsee.dtos.responses.SuccessResponse;
import com.coralpay.coralsee.exceptions.RegistrationException.UserAlreadyExistsException;
import com.coralpay.coralsee.exceptions.TransactionException.InvalidTransactionDetailException;
import com.coralpay.coralsee.services.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@Valid @RequestBody CreateTransactionRequest request) throws UserAlreadyExistsException, IOException, InvalidTransactionDetailException {
        SuccessResponse response = SuccessResponse.builder()
                .data(transactionService.createTransaction(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWeekToDateSummary(@PathVariable("userId") String userId, @RequestParam int number, @RequestParam int size){
        SuccessResponse response = SuccessResponse.builder()
                .data(transactionService.getWeekToDateSummary(userId, number, size))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
