package com.coralpay.coralsee.controllers;

import com.coralpay.coralsee.dtos.requests.SignupRequest;
import com.coralpay.coralsee.dtos.responses.SuccessResponse;
import com.coralpay.coralsee.exceptions.RegistrationException.UserAlreadyExistsException;
import com.coralpay.coralsee.services.user_management_service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> createAccount(@Valid @RequestBody SignupRequest request) throws UserAlreadyExistsException {
        SuccessResponse response = SuccessResponse.builder()
                .data(userService.createUserAccount(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
