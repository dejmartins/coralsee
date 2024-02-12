package com.coralpay.coralsee.exceptions.RegistrationException;

import com.coralpay.coralsee.exceptions.CoralseeException;

public class UserAlreadyExistsException extends CoralseeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
