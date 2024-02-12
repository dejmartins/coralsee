package com.coralpay.coralsee.exceptions.TransactionException;

import com.coralpay.coralsee.exceptions.CoralseeException;

public class InvalidTransactionDetailException extends CoralseeException {
    public InvalidTransactionDetailException(String message) {
        super(message);
    }
}
