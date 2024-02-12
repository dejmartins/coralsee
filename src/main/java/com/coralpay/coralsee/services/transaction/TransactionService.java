package com.coralpay.coralsee.services.transaction;

import com.coralpay.coralsee.dtos.requests.CreateTransactionRequest;
import com.coralpay.coralsee.dtos.responses.TransactionResponse.TransactionResponse;
import com.coralpay.coralsee.dtos.responses.TransactionResponse.TransactionSummaryResponse;
import com.coralpay.coralsee.exceptions.TransactionException.InvalidTransactionDetailException;

import java.io.IOException;

public interface TransactionService {

    TransactionResponse createTransaction(CreateTransactionRequest createTransactionRequest) throws InvalidTransactionDetailException, IOException;

    TransactionSummaryResponse getWeekToDateSummary(String userId, int number, int size);
}
