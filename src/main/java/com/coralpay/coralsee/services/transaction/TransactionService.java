package com.coralpay.coralsee.services.transaction;

import com.coralpay.coralsee.dtos.requests.CreateTransactionRequest;
import com.coralpay.coralsee.dtos.responses.CreateTransactionResponse;
import com.coralpay.coralsee.exceptions.TransactionException.InvalidTransactionDetailException;

import java.io.IOException;

public interface TransactionService {

    CreateTransactionResponse createTransaction(CreateTransactionRequest createTransactionRequest) throws InvalidTransactionDetailException, IOException;
}
