package com.coralpay.coralsee.exceptions;

import com.coralpay.coralsee.exceptions.CoralseeException;

public class UserNotFoundException extends CoralseeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
