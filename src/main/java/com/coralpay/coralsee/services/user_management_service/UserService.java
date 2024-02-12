package com.coralpay.coralsee.services.user_management_service;

import com.coralpay.coralsee.dtos.responses.UserResponse;

public interface UserService {
    UserResponse getUserBy(Long id);
    UserResponse getUserBy(String username);
}
