package com.coralpay.coralsee.services.user_management_service;

import com.coralpay.coralsee.dtos.requests.SignupRequest;
import com.coralpay.coralsee.dtos.responses.UserResponse;
import com.coralpay.coralsee.exceptions.RegistrationException.UserAlreadyExistsException;

public interface UserService {
    UserResponse getUserBy(Long id);
    UserResponse getUserBy(String emailAddress);
    UserResponse createUserAccount(SignupRequest signupRequest) throws UserAlreadyExistsException;
}
