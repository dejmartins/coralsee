package com.coralpay.coralsee.validators;

import com.coralpay.coralsee.dtos.requests.CreateTransactionRequest;
import com.coralpay.coralsee.exceptions.TransactionException.InvalidTransactionDetailException;

public class TransactionValidator {

    private static void validateTransactionRequest(CreateTransactionRequest transactionRequest) throws InvalidTransactionDetailException {
        if (transactionRequest == null) {
            throw new InvalidTransactionDetailException("Invalid Payload");
        }
    }

    public static void validateCreateTransactionRequest(CreateTransactionRequest transactionRequest) throws InvalidTransactionDetailException {
        validateTransactionRequest(transactionRequest);

        if (transactionRequest.getAmountPaid() == null || transactionRequest.getAmountPaid().isEmpty()) {
            throw new InvalidTransactionDetailException("Invalid Amount");
        }

        if (transactionRequest.getEmailAddress() == null || transactionRequest.getEmailAddress().isEmpty()){
            throw new InvalidTransactionDetailException(("Invalid Email Address"));
        }

        if (transactionRequest.getPaymentReference() == null || transactionRequest.getPaymentReference().isEmpty()){
            throw new InvalidTransactionDetailException("Invalid Payment Reference");
        }
    }
}
